package com.kasakaid.security.domain;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class KasakaidUser extends UsernamePasswordAuthenticationToken {
    public KasakaidUser(Object principal, Object credentials) {
        super(principal, credentials);
    }

    public KasakaidUser(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
        super(principal, credentials, authorities);
    }
}
