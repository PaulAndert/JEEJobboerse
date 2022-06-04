package de.thb.jee.authexample.configuration;

import de.thb.jee.authexample.repository.OffeneStellenRepository;
import de.thb.jee.authexample.service.OffeneStellenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import de.thb.jee.authexample.repository.UserRepository;
import de.thb.jee.authexample.security.ExampleUserDetailsService;

@Configuration
public class SecurityConfiguration {
	
	@Autowired
	private UserRepository userRepository;
	
    @Bean
    public UserDetailsService userDetailsService() {
        return new ExampleUserDetailsService(userRepository);
    }

    @Autowired
    private OffeneStellenRepository offeneStellenRepository;

    @Bean
    public OffeneStellenService offeneStellenService() {
        return new OffeneStellenService(offeneStellenRepository);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

}
