package com.kasakaid.security.repository;

import com.kasakaid.security.domain.KasakaidUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Repository;

import java.util.LinkedList;
import java.util.List;

@Repository
public class UserRepository {
    public KasakaidUser findByUsernameAndPassword(String username, String password) {
        List<GrantedAuthority> authorityList = new LinkedList<GrantedAuthority>() {
            {
                add(new SimpleGrantedAuthority("USER"));
            }
        };
        return new KasakaidUser(username, password, authorityList);
    }
}
