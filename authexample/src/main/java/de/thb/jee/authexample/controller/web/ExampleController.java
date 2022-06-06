package de.thb.jee.authexample.controller.web;

import de.thb.jee.authexample.entity.*;
import de.thb.jee.authexample.security.ExampleUserDetailsService;
import de.thb.jee.authexample.service.AbschlussService;
import de.thb.jee.authexample.service.KompetenzService;
import de.thb.jee.authexample.service.OffeneStellenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping
public class ExampleController {

	private final ExampleUserDetailsService exampleUserDetailsService;
	private final OffeneStellenService offeneStellenService;
	private final KompetenzService kompetenzService;
	private final AbschlussService abschlussService;

	@GetMapping("/")
	public String showNotebooks() {
		return "home";
	}

	@GetMapping("/secure")
	public String securedPage(Model model) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserEntity currentUser = exampleUserDetailsService.leadCurrentUser(((UserDetails) principal).getUsername());
		if(currentUser.getRoleId() == 1){
			List<OffeneStellenEntity> allOffeneStellenOfUser = offeneStellenService.getAllOffeneStellenOfUser(currentUser.getId());
			model.addAttribute("offeneStellen", allOffeneStellenOfUser);
		}
		model.addAttribute("user", currentUser);
		return "secure";
	}

	@GetMapping("/search")
	public String searchPage(Model model) {
		model.addAttribute("dataTransfer", new DataTransfer());
		model.addAttribute("kompetenzen", kompetenzService.getAllKompetenzen());
		model.addAttribute("abschluesse", abschlussService.getAllAbschlüsse());
		return "search";
	}

	@PostMapping("/search")
	public String showresult(@ModelAttribute DataTransfer dataTransfer, Model model) {
		if(dataTransfer.getRoleId() == 1) {
			List<UserEntity> userEntities;
			if (!dataTransfer.getBeschreibung().isBlank())
				userEntities = exampleUserDetailsService.findAllByDescriptionAndRoleId(dataTransfer.getBeschreibung(), 2);
			else userEntities = exampleUserDetailsService.loadByRoleId(2);
			if (!dataTransfer.getAbschluss().isBlank()) {
				AbschlussEntity abschlussEntity = abschlussService.getByMatchingName(dataTransfer.getAbschluss());
				userEntities.removeIf(ue -> !ue.getUserAbschluesse().contains(abschlussEntity));
			}
			if (!dataTransfer.getKompetenz().isBlank()) {
				KompetenzenEntity kompetenzenEntity = kompetenzService.getByMatchingName(dataTransfer.getKompetenz());
				userEntities.removeIf(ue -> !ue.getUserKompetenzen().contains(kompetenzenEntity));
			}
			model.addAttribute("outputList", userEntities);
		}else{
			List<OffeneStellenEntity> offeneStellenEntities;
			if (!dataTransfer.getBeschreibung().isBlank()){
				if(dataTransfer.getGehalt() != 0){
					offeneStellenEntities = offeneStellenService.loadAllbyGehaltAndBeschreibung(dataTransfer.getGehalt(), dataTransfer.getBeschreibung());
				}else{
					offeneStellenEntities = offeneStellenService.loadAllbyBeschreibung(dataTransfer.getBeschreibung());
				}
			}else{
				if(dataTransfer.getGehalt() != 0){
					offeneStellenEntities = offeneStellenService.loadAllbyGehalt(dataTransfer.getGehalt());
				}else{
					offeneStellenEntities = offeneStellenService.loadAllOffeneStellen();
				}
			}
			if (!dataTransfer.getKompetenz().isBlank()) {
				KompetenzenEntity kompetenzenEntity = kompetenzService.getByMatchingName(dataTransfer.getKompetenz());
				offeneStellenEntities.removeIf(ofe -> !ofe.getOffeneStelleKompetenzen().contains(kompetenzenEntity));
			}
			model.addAttribute("outputList", offeneStellenEntities);
		}
		model.addAttribute("dataTransfer", dataTransfer);
		return "result";
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
