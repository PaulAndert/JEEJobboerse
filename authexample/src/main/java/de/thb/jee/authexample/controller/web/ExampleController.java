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

	@GetMapping("/")
	public String showHomepage() { return "home"; }

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
			List<KompetenzenEntity> kompetenzen = kompetenzService.getAllKompetenzen();
			model.addAttribute("kompetenzen", kompetenzen);
			List<AbschlussEntity> abschluesse = abschlussService.getAllAbschlüsse();
			model.addAttribute("abschluesse", abschluesse);
		}
		model.addAttribute("user", currentUser);
		BookmarkTransferEntity bte = new BookmarkTransferEntity();
		bte.setRoleId(currentUser.getRoleId());
		model.addAttribute("bookmarkTransfer", bte);
		return "secure";
	}

	@PostMapping("/secure")
	public RedirectView deleteBookmark(@ModelAttribute BookmarkTransferEntity bookmarkTransfer){
		UserEntity ue = userService.loadCurrentUser(((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());
		if(bookmarkTransfer.getRoleId() == 1) ue.getJobsuchendeBookmarks().remove(userService.userId(bookmarkTransfer.getSecondId()));
		else ue.getOffenenStellenBookmarks().remove(offeneStellenService.getOffeneStelleById(bookmarkTransfer.getSecondId()));
		return new RedirectView("/secure");
	}

	@RequestMapping(value = "offeneStellenDelete", method = RequestMethod.POST)
	public RedirectView offeneStellenDelete(@RequestParam("secondid") int secondid){
		offeneStellenService.deleteById(secondid);
		return new RedirectView("/secure");
	}

	@RequestMapping(value = "removeAbschlussOderKompetenz", method = RequestMethod.POST)
	public RedirectView removeAbschlussOderKompetenz(@RequestParam("secondid") int secondid, @RequestParam("mode") int mode){
		UserEntity ue = userService.loadCurrentUser(((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());
		if(mode == 1){
			ue.getUserAbschluesse().remove(abschlussService.getAbschlussById(secondid));
		}else{
			ue.getUserKompetenzen().remove(kompetenzService.getKompetenzById(secondid));
		}
		return new RedirectView("/secure");
	}

	@RequestMapping(value = "addAbschlussOderKompetenz", method = RequestMethod.POST)
	public RedirectView addAbschlussOderKompetenz(@RequestParam("secondid") int secondid, @RequestParam("mode") int mode){
		UserEntity ue = userService.loadCurrentUser(((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());
		if(mode == 1){
			AbschlussEntity absch = abschlussService.getAbschlussById(secondid);
			if(!ue.getUserAbschluesse().contains(absch)) ue.getUserAbschluesse().add(absch);
		}else{
			KompetenzenEntity komp = kompetenzService.getKompetenzById(secondid);
			if(!ue.getUserKompetenzen().contains(komp)) ue.getUserKompetenzen().add(komp);
		}
		return new RedirectView("/secure");
	}

	@RequestMapping(value = "profilUpdate", method = RequestMethod.POST)
	public String userUpdate(Model model){
		UserEntity ue = userService.loadCurrentUser(((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());
		model.addAttribute("user", ue);
		return "userUpdate";
	}

	@RequestMapping(value = "profilUpdate2", method = RequestMethod.POST)
	public RedirectView userUpdate2(@ModelAttribute UserEntity ueupdated){
		UserEntity ue = userService.loadCurrentUser(((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());
		ue.setEmail(ueupdated.getEmail());
		ue.setVorname(ueupdated.getVorname());
		ue.setNachname(ueupdated.getNachname());
		ue.setAdresse(ueupdated.getAdresse());
		ue.setUnternehmensname(ueupdated.getUnternehmensname());
		ue.setUnternehmensregisternr(ueupdated.getUnternehmensregisternr());
		ue.setBeschreibung(ueupdated.getBeschreibung());
		return new RedirectView("/secure");
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

	@RequestMapping(value = "result", method = RequestMethod.POST)
	public void bookmark(@ModelAttribute("bookmarkTransfer") BookmarkTransferEntity bookmarkTransfer,
						 @RequestParam("beschreibung") String beschreibung,
						 @RequestParam("abschluss") String abschluss,
						 @RequestParam("kompetenz") String kompetenz,
						 @RequestParam("gehalt") float gehalt,
						 @RequestParam("roleid") int roleid,
						 Model model){
		UserEntity ue = userService.loadCurrentUser(((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());
		if(bookmarkTransfer.getRoleId() == 1){
			UserEntity uesecond = userService.userId(bookmarkTransfer.getSecondId());
			if(!ue.getJobsuchendeBookmarks().contains(uesecond))ue.getJobsuchendeBookmarks().add(uesecond);
		}else{
			OffeneStellenEntity ossecond = offeneStellenService.getOffeneStelleById(bookmarkTransfer.getSecondId());
			if(!ue.getOffenenStellenBookmarks().contains(ossecond))ue.getOffenenStellenBookmarks().add(ossecond);
		}
		DataTransferEntity dte = new DataTransferEntity(roleid, beschreibung, kompetenz, abschluss, gehalt);
		showresult(dte, model);
	}
	/* // Getmapping für den Ofenen stellen erstellung
	@GetMapping("createOffeneStelen")
	public createOffeneStelen(){

	}
	*/
}
