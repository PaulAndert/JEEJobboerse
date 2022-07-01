package de.thb.jee.authexample.security;

import java.util.List;

import de.thb.jee.authexample.entity.UserEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import de.thb.jee.authexample.repository.UserRepository;
import de.thb.jee.authexample.security.authority.UserAuthority;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ExampleUserDetailsService implements UserDetailsService {

	private final UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		return userRepository.findOptionalByEmail(username)
				.map(user -> ExampleUserDetails.builder()
						.username(user.getEmail())
						.password(user.getPassword())
						.authorities(List.of(new UserAuthority()))
						.enabled(user.isEnabled())
						.roleId(user.getRoleId())
						.accountNonExpired(true)
						.accountNonLocked(true)
						.credentialsNonExpired(true)
						.build())
				.orElseThrow(() -> new UsernameNotFoundException("User not found!"));
	}
}

