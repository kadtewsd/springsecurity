package com.kasakaid.security.mock;

import com.kasakaid.security.domain.KasakaidUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import java.util.ArrayList;
import java.util.List;

public class ContextFactory implements WithSecurityContextFactory<KasakaidMockUser> {

    @Override
    public SecurityContext createSecurityContext(KasakaidMockUser customUser) {

        SecurityContext context = SecurityContextHolder.createEmptyContext();
        List<GrantedAuthority> authorityList = new ArrayList<GrantedAuthority>() {
            {
                add(new SimpleGrantedAuthority("USER"));
            }
        };
        KasakaidUser principal = new KasakaidUser(customUser.username(),
                customUser.password(), authorityList);
        context.setAuthentication(principal);
        return context;
    }
}

