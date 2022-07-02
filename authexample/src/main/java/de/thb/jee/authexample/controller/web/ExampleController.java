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
import org.springframework.web.bind.annotation.*;
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

	public UserEntity getCurrentUser() { return userService.loadByEmail(((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername()); }

	@GetMapping("/")
	public String showHomepage() { return "home"; }

	@GetMapping("/secure")
	public String securedPage(Model model) {
		//System.out.println("Test");
		UserEntity currentUser = getCurrentUser();
		if(currentUser.getRoleId() == 1) model.addAttribute("offeneStellen", offeneStellenService.loadAllByUserId(currentUser.getId()));
		else{
			List<String> emailList = new ArrayList<>();
			for (OffeneStellenEntity offeneStellenEntity : currentUser.getOffenenStellenBookmarks()) {
				String emailString = userService.loadById(offeneStellenEntity.getUserId()).getEmail();
				if (!emailString.isBlank()) emailList.add(emailString);
				else emailList.add("");
			}
			model.addAttribute("emailList", emailList);
			model.addAttribute("kompetenzen", kompetenzService.loadAll());
			model.addAttribute("abschluesse", abschlussService.loadAll());
		}
		model.addAttribute("user", currentUser);
		BookmarkTransferEntity bte = new BookmarkTransferEntity();
		bte.setRoleId(currentUser.getRoleId());
		model.addAttribute("bookmarkTransfer", bte);
		return "secure";
	}

	@PostMapping("/secure")
	public RedirectView deleteBookmark(@ModelAttribute BookmarkTransferEntity bookmarkTransfer){
		UserEntity currentUser = getCurrentUser();
		if(bookmarkTransfer.getRoleId() == 1) currentUser.getJobsuchendeBookmarks().remove(userService.loadById(bookmarkTransfer.getSecondId()));
		else currentUser.getOffenenStellenBookmarks().remove(offeneStellenService.loadById(bookmarkTransfer.getSecondId()));
		return new RedirectView("/secure");
	}

	@RequestMapping(value = "offeneStellenDelete", method = RequestMethod.POST)
	public RedirectView offeneStellenDelete(@RequestParam("secondid") int secondid){
		offeneStellenService.deleteById(secondid);
		return new RedirectView("/secure");
	}

	@RequestMapping(value = "removeAbschlussOderKompetenz", method = RequestMethod.POST)
	public RedirectView removeAbschlussOderKompetenz(@RequestParam("secondid") int secondid, @RequestParam("mode") int mode){
		UserEntity currentUser = getCurrentUser();
		if(mode == 1) currentUser.getUserAbschluesse().remove(abschlussService.loadById(secondid));
		else currentUser.getUserKompetenzen().remove(kompetenzService.loadById(secondid));
		return new RedirectView("/secure");
	}

	@RequestMapping(value = "addAbschlussOderKompetenz", method = RequestMethod.POST)
	public RedirectView addAbschlussOderKompetenz(@RequestParam("secondid") int secondid, @RequestParam("mode") int mode){
		UserEntity currentUser = getCurrentUser();
		if(mode == 1){
			AbschlussEntity abschluss = abschlussService.loadById(secondid);
			if(!currentUser.getUserAbschluesse().contains(abschluss)) currentUser.getUserAbschluesse().add(abschluss);
		}else{
			KompetenzenEntity kompetenz = kompetenzService.loadById(secondid);
			if(!currentUser.getUserKompetenzen().contains(kompetenz)) currentUser.getUserKompetenzen().add(kompetenz);
		}
		return new RedirectView("/secure");
	}

	@RequestMapping(value = "prepareUserUpdate", method = RequestMethod.POST)
	public String prepareUserUpdate(Model model){
		model.addAttribute("user", getCurrentUser());
		return "userUpdate";
	}

	@RequestMapping(value = "userUpdate", method = RequestMethod.POST)
	public RedirectView userUpdate(@ModelAttribute UserEntity userEntityUpdate){
		UserEntity currentUser = getCurrentUser();
		currentUser.setEmail(userEntityUpdate.getEmail());
		currentUser.setVorname(userEntityUpdate.getVorname());
		currentUser.setNachname(userEntityUpdate.getNachname());
		currentUser.setAdresse(userEntityUpdate.getAdresse());
		currentUser.setUnternehmensname(userEntityUpdate.getUnternehmensname());
		currentUser.setUnternehmensregisternr(userEntityUpdate.getUnternehmensregisternr());
		currentUser.setBeschreibung(userEntityUpdate.getBeschreibung());
		return new RedirectView("/secure");
	}

	@GetMapping("/search")
	public String searchPage(Model model) {
		model.addAttribute("dataTransfer", new DataTransferEntity());
		model.addAttribute("kompetenzen", kompetenzService.loadAll());
		model.addAttribute("abschluesse", abschlussService.loadAll());
		return "search";
	}

	@PostMapping("/search")
	public String showResult(@ModelAttribute DataTransferEntity dataTransfer, Model model) {
		if(dataTransfer.getRoleId() == 1) {
			int abschlussEntityId = 0;
			if (!dataTransfer.getAbschluss().isBlank()) abschlussEntityId = (int) abschlussService.loadByName(dataTransfer.getAbschluss()).getId();
			int kompetenzenEntityId = 0;
			if (!dataTransfer.getKompetenz().isBlank()) kompetenzenEntityId = (int) kompetenzService.loadByName(dataTransfer.getKompetenz()).getId();
			List<UserEntity> userEntities = userService.loadSearchResults("%" + dataTransfer.getBeschreibung() + "%", abschlussEntityId, kompetenzenEntityId);
			model.addAttribute("outputList", userEntities);
		}else{
			int kompetenzenEntityId = 0;
			if (!dataTransfer.getKompetenz().isBlank()) kompetenzenEntityId = (int) kompetenzService.loadByName(dataTransfer.getKompetenz()).getId();
			List<OffeneStellenEntity> offeneStellenEntities = offeneStellenService.loadSearchResults((int) dataTransfer.getGehalt(), "%" + dataTransfer.getBeschreibung() + "%", kompetenzenEntityId);
			model.addAttribute("outputList", offeneStellenEntities);
			List<String> emailList = new ArrayList<>();
			for (OffeneStellenEntity offeneStellenEntity : offeneStellenEntities) {
				String emailString = userService.loadById(offeneStellenEntity.getUserId()).getEmail();
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

	@RequestMapping(value = "result", method = RequestMethod.POST)
	public void bookmark(@ModelAttribute("bookmarkTransfer") BookmarkTransferEntity bookmarkTransfer,
						 @RequestParam("beschreibung") String beschreibung,
						 @RequestParam("abschluss") String abschluss,
						 @RequestParam("kompetenz") String kompetenz,
						 @RequestParam("gehalt") float gehalt,
						 @RequestParam("roleid") int roleid,
						 Model model){
		UserEntity currentUser = getCurrentUser();
		if(bookmarkTransfer.getRoleId() == 1){
			UserEntity secondUserEntity = userService.loadById(bookmarkTransfer.getSecondId());
			if(!currentUser.getJobsuchendeBookmarks().contains(secondUserEntity)) currentUser.getJobsuchendeBookmarks().add(secondUserEntity);
		}else{
			OffeneStellenEntity secondOffeneStellenEntity = offeneStellenService.loadById(bookmarkTransfer.getSecondId());
			if(!currentUser.getOffenenStellenBookmarks().contains(secondOffeneStellenEntity)) currentUser.getOffenenStellenBookmarks().add(secondOffeneStellenEntity);
		}
		showResult(new DataTransferEntity(roleid, beschreibung, kompetenz, abschluss, gehalt), model);
	}

	@GetMapping("createOffeneStellen")
	public String createOffeneStellen(Model model){
		OffeneStellenEntity newOffStelle = new OffeneStellenEntity();
		newOffStelle.setOffeneStelleKompetenzen(new ArrayList<>());
		newOffStelle.setUserId(getCurrentUser().getId());
		offeneStellenService.insert(newOffStelle);
		model.addAttribute("DataTransfer", newOffStelle);
		model.addAttribute("kompetenzen", kompetenzService.loadAll());
		return "createOffeneStelen";
	}

	@PostMapping("addKompetenzToOS")
	public String addKompetenzToOS(@ModelAttribute("DataTransfer") OffeneStellenEntity ofe,
								   @RequestParam("kompetenzid") long kompetenzid,
								   @RequestParam("id") long id,
								   Model model){
		KompetenzenEntity kompetenz = kompetenzService.loadById(kompetenzid);
		OffeneStellenEntity offeneStelle = offeneStellenService.loadById(id);
		if (offeneStelle.getOffeneStelleKompetenzen() != null && !offeneStelle.getOffeneStelleKompetenzen().contains(kompetenz)) offeneStelle.getOffeneStelleKompetenzen().add(kompetenz);
		model.addAttribute("DataTransfer", offeneStelle);
		model.addAttribute("kompetenzen", kompetenzService.loadAll());
		return "createOffeneStelen";
	}

	@PostMapping("createOffeneStellen")
	public RedirectView createOffeneStellen2(@ModelAttribute("offeneStelle") OffeneStellenEntity offeneStellenEntity,
											 @RequestParam("id") long id){
		OffeneStellenEntity offeneStelle = offeneStellenService.loadById(id);
		offeneStelle.setGehalt(offeneStellenEntity.getGehalt());
		offeneStelle.setBeschreibung(offeneStellenEntity.getBeschreibung());
		offeneStelle.setEnabled(true);
		return new RedirectView("/secure");
	}
}
