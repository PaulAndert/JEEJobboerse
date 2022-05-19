package de.thb.jee.authexample.controller.web;

import de.thb.jee.authexample.security.ExampleUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class ExampleController {

	private final ExampleUserDetailsService exampleUserDetailsService;

	@GetMapping("/")
	public String showNotebooks() {
		return "home";
	}

	@GetMapping("secure")
	public String securedPage() { return "secure"; }

}
