package de.thb.jee.authexample.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Builder;
import lombok.ToString;
import lombok.Value;

@Value
@Builder(toBuilder = true)
@ToString
public class ExampleUserDetails implements UserDetails {
	private final String username;
	@ToString.Exclude
	private final String password;
	private boolean accountNonExpired;
	private boolean accountNonLocked;
	private boolean credentialsNonExpired;
	private boolean enabled;
	private int roleId;

	private final Collection<? extends GrantedAuthority> authorities;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<SimpleGrantedAuthority> authorities = new ArrayList<>();
		if (roleId == 1) authorities.add(new SimpleGrantedAuthority("ROLE_FIRMA"));
		if (roleId == 2) authorities.add(new SimpleGrantedAuthority("ROLE_JOBSUCHENDER"));
		return authorities;
	}
}

