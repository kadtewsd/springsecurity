package com.kasakaid.security.infrastructure.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.LinkedList;
import java.util.List;

@Slf4j
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private static final String REQUEST_ATTRNAME_USERNAME = "username";
    private static final String REQUEST_ATTRNAME_PASSWORD = "password";

    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    protected String obtainPassword(HttpServletRequest request) {
        return (String) request.getAttribute(REQUEST_ATTRNAME_PASSWORD);
    }

    @Override
    protected String obtainUsername(HttpServletRequest request) {
        return (String) request.getAttribute(REQUEST_ATTRNAME_USERNAME);
    }

    @SneakyThrows
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {

        // TODO 実装
        UsernamePasswordAuthenticationToken token = null;
        try {
            token = mapper.readValue(request.getReader(), UsernamePasswordAuthenticationToken.class);
        } catch (Exception e) {
            List<SimpleGrantedAuthority> list = new LinkedList();
            list.add(new SimpleGrantedAuthority("USER"));
            token = new UsernamePasswordAuthenticationToken(
                    request.getParameter(REQUEST_ATTRNAME_USERNAME),
                    request.getParameter(REQUEST_ATTRNAME_PASSWORD),
                    list);
            request.setAttribute(REQUEST_ATTRNAME_USERNAME, token.getName());
            request.setAttribute(REQUEST_ATTRNAME_PASSWORD, token.getPrincipal());

            // Allow subclasses to set the "details" property
            setDetails(request, token);

            // (1)
            // Obtain UserName, Password, CompanyId
            String username = super.obtainUsername(request);
            String password = super.obtainPassword(request);
        }
        return this.getAuthenticationManager().authenticate(token);
    }
}
