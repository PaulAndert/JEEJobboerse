package de.thb.jee.authexample.security.authority;

import org.springframework.security.core.GrantedAuthority;

public class UserAuthority implements GrantedAuthority {

    public final static String ROLE_USER = "ROLE_USER";

    @Override
    public String getAuthority() {
        return ROLE_USER;
    }
}
