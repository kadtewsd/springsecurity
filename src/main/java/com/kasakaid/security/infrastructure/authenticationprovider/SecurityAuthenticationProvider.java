package com.kasakaid.security.infrastructure.authenticationprovider;

import com.kasakaid.security.domain.KasakaidUser;
import com.kasakaid.security.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SecurityAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private UserRepository repository;

    @Override
    public Authentication authenticate(Authentication authentication) {
        if (authentication.getName().equals("")) return authentication;
        String userAccount = authentication.getName();
        String password = authentication.getCredentials().toString();
//        return this.administrativeUser.findByUserAccountAndPassword(userAccount, password).getAuthenticationToken();
        return this.repository.findByUsernameAndPassword(userAccount, password);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.isAssignableFrom(UsernamePasswordAuthenticationToken.class);
    }
}
