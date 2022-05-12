package de.thb.jee.authexample.security;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import de.thb.jee.authexample.repository.UserRepository;
import de.thb.jee.authexample.security.authority.UserAuthority;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ExampleUserDetailsService implements UserDetailsService {
	
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    	
    	ExampleUserDetails exampleUserDetails = userRepository.findByEmail(username)
    			.map(user -> {
    				return ExampleUserDetails.builder()
    						.username(user.getEmail())
    	                    .password(user.getPassword())
    	                    .authorities(List.of(new UserAuthority()))
    	                    .enabled(user.isEnabled())
    	                    .accountNonExpired(true)
    	                    .accountNonLocked(true)
    	                    .credentialsNonExpired(true)
    						.build();
    			})
    			.orElseThrow(() -> new UsernameNotFoundException("User not found!"));
    	
    	return exampleUserDetails;
    }
}
