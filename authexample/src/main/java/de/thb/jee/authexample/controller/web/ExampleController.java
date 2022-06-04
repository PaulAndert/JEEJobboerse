package de.thb.jee.authexample.controller.web;

import de.thb.jee.authexample.entity.DataTransfer;
import de.thb.jee.authexample.entity.OffeneStellenEntity;
import de.thb.jee.authexample.entity.UserEntity;
import de.thb.jee.authexample.security.ExampleUserDetailsService;
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

import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping
public class ExampleController {

	private final ExampleUserDetailsService exampleUserDetailsService;
	private final OffeneStellenService offeneStellenService;

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
		return "search";
	}

	@PostMapping("/search")
	public String showresult(@ModelAttribute DataTransfer dataTransfer, Model model) {
		List<UserDetails> test = exampleUserDetailsService.loadUserWithSearch(dataTransfer.getEingabe());
		model.addAttribute("dataTransfer", dataTransfer);
		model.addAttribute("data", test);
		return "result";
	}
}
