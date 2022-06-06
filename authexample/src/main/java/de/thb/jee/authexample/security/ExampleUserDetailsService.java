package de.thb.jee.authexample.security;

import java.util.ArrayList;
import java.util.List;

import de.thb.jee.authexample.entity.AbschlussEntity;
import de.thb.jee.authexample.entity.KompetenzenEntity;
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

	public List<UserDetails> loadUserWithSearch(String str) throws UsernameNotFoundException {
		List<UserEntity> uebec = userRepository.findUserEntitiesByEmailContains(str);
		List<UserDetails> ret = new ArrayList<>();
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

	public UserEntity leadCurrentUser(String str) throws UsernameNotFoundException {
		return userRepository.findUserEntitiesByEmail(str);
	}
	/*
	public List<UserEntity> loadAllUSersMatchingSeachParameters(String desc, long abschlussId, long kompetenzId){
		List<UserEntity> uel = userRepository.search
				(desc, abschlussId, kompetenzId);
		List<UserEntity> ret = new ArrayList<>();
		if(!uel.isEmpty()) return uel;
		else return ret;
	}
	*/

	public List<UserEntity> findAllByDescriptionAndRoleId(String str, int roleId){
		return userRepository.findUserEntitiesByBeschreibungContainsAndRoleId(str, roleId);
	}
	public List<UserEntity> loadByRoleId(int roleId){
		return userRepository.findUserEntitiesByRoleId(roleId);
	}

	public List<UserEntity> loadAll(){
		return userRepository.findAll();
	}
}
