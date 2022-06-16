package de.thb.jee.authexample.security;

import java.util.List;

import de.thb.jee.authexample.entity.UserEntity;
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

	public UserEntity leadCurrentUser(String email) throws UsernameNotFoundException {
		return userRepository.findUserEntitiesByEmail(email);
	}

	public List<UserEntity> loadAllUsersMatchingSeachParameters(String desc, int abschlussId, int kompetenzId){
		return userRepository.search(desc, abschlussId, kompetenzId);
	}
	public UserEntity userId(long id) throws UsernameNotFoundException {
		return userRepository.findById(id);
	}
}
