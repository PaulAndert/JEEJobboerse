package de.thb.jee.authexample.security;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import de.thb.jee.authexample.entity.UserEntity;
import org.springframework.security.core.userdetails.User;
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

		return userRepository.findByEmail(username)
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

	public List<UserDetails> loadUserWithSearch(String str) throws UsernameNotFoundException {
		List<UserEntity> uebec = userRepository.findUserEntitiesByEmailContains(str);
		List<UserDetails> ret = new ArrayList<UserDetails>();
		if(!uebec.isEmpty()) {
			for (UserEntity rot : uebec) {
				ret.add(ExampleUserDetails.builder()
						.username(rot.getEmail())
						.password(rot.getPassword())
						.authorities(List.of(new UserAuthority()))
						.enabled(rot.isEnabled())
						.roleId(rot.getRoleId())
						.accountNonExpired(true)
						.accountNonLocked(true)
						.credentialsNonExpired(true)
						.build());
			}
		}
		return ret;
	}
}
