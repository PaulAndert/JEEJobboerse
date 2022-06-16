package de.thb.jee.authexample.controller.web;

import de.thb.jee.authexample.entity.*;
import de.thb.jee.authexample.service.AbschlussService;
import de.thb.jee.authexample.service.KompetenzService;
import de.thb.jee.authexample.service.OffeneStellenService;
import de.thb.jee.authexample.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping
public class ExampleController {

	private final UserService userService;
	private final OffeneStellenService offeneStellenService;
	private final KompetenzService kompetenzService;
	private final AbschlussService abschlussService;

	@GetMapping("/")
	public String showHomepage() {
		return "home";
	}

	@GetMapping("/secure")
	public String securedPage(Model model) {
		UserEntity currentUser = userService.loadCurrentUser(((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());
		if(currentUser.getRoleId() == 1){
			List<OffeneStellenEntity> allOffeneStellenOfUser = offeneStellenService.getAllOffeneStellenOfUser(currentUser.getId());
			model.addAttribute("offeneStellen", allOffeneStellenOfUser);
		}else{
			List<String> emailList = new ArrayList<>();
			for (OffeneStellenEntity offeneStellenEntity : currentUser.getOffenenStellenBookmarks()) {
				String emailString = userService.userId(offeneStellenEntity.getUserId()).getEmail();
				if (!emailString.isBlank()) emailList.add(emailString);
				else emailList.add("");
			}
			model.addAttribute("emailList", emailList);
		}
		model.addAttribute("user", currentUser);
		return "secure";
	}

	@GetMapping("/search")
	public String searchPage(Model model) {
		model.addAttribute("dataTransfer", new DataTransferEntity());
		model.addAttribute("kompetenzen", kompetenzService.getAllKompetenzen());
		model.addAttribute("abschluesse", abschlussService.getAllAbschlüsse());
		return "search";
	}

	@PostMapping("/search")
	public String showresult(@ModelAttribute DataTransferEntity dataTransfer, Model model) {
		if(dataTransfer.getRoleId() == 1) {
			int abschlussEntityId = 0;
			if (!dataTransfer.getAbschluss().isBlank()) abschlussEntityId = (int) abschlussService.getByMatchingName(dataTransfer.getAbschluss()).getId();
			int kompetenzenEntityId = 0;
			if (!dataTransfer.getKompetenz().isBlank()) kompetenzenEntityId = (int) kompetenzService.getByMatchingName(dataTransfer.getKompetenz()).getId();
			List<UserEntity> userEntities = userService.loadAllUsersMatchingSeachParameters("%" + dataTransfer.getBeschreibung() + "%", abschlussEntityId, kompetenzenEntityId);
			model.addAttribute("outputList", userEntities);
		}else{
			int kompetenzenEntityId = 0;
			if (!dataTransfer.getKompetenz().isBlank()) kompetenzenEntityId = (int) kompetenzService.getByMatchingName(dataTransfer.getKompetenz()).getId();
			List<OffeneStellenEntity> offeneStellenEntities = offeneStellenService.loadAllOffeneStellenMatchingSeachParameters((int) dataTransfer.getGehalt(), "%" + dataTransfer.getBeschreibung() + "%", kompetenzenEntityId);
			model.addAttribute("outputList", offeneStellenEntities);
			List<String> emailList = new ArrayList<>();
			for (OffeneStellenEntity offeneStellenEntity : offeneStellenEntities) {
				String emailString = userService.userId(offeneStellenEntity.getUserId()).getEmail();
				if (!emailString.isBlank()) emailList.add(emailString);
				else emailList.add("");
			}
			model.addAttribute("emailList", emailList);
		}
		model.addAttribute("dataTransfer", dataTransfer);
		BookmarkTransferEntity bte = new BookmarkTransferEntity();
		bte.setRoleId(dataTransfer.getRoleId());
		model.addAttribute("bookmarkTransfer", bte);
		return "result";
	}

	@PostMapping("/result")
	public RedirectView bookmark(@ModelAttribute BookmarkTransferEntity bookmarkTransfer){
		UserEntity ue = userService.loadCurrentUser(((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());
		if(bookmarkTransfer.getRoleId() == 1) ue.getJobsuchendeBookmarks().add(userService.userId(bookmarkTransfer.getSecondId()));
		else ue.getOffenenStellenBookmarks().add(offeneStellenService.getOffeneStelleById(bookmarkTransfer.getSecondId()));
		return new RedirectView("/secure");
	}

	/*
	@PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Book create(@RequestBody Book book) {
        return bookRepository.save(book);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        bookRepository.findById(id)
          .orElseThrow(BookNotFoundException::new);
        bookRepository.deleteById(id);
    }

    @PutMapping("/{id}")
    public Book updateBook(@RequestBody Book book, @PathVariable Long id) {
        if (book.getId() != id) {
          throw new BookIdMismatchException();
        }
        bookRepository.findById(id)
          .orElseThrow(BookNotFoundException::new);
        return bookRepository.save(book);
    }
	 */
}
