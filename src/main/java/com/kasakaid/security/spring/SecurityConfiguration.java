package com.kasakaid.security.spring;

import com.kasakaid.security.infrastructure.filter.AuthenticationFilter;
import com.kasakaid.security.infrastructure.filter.LoggingFilter;
import com.kasakaid.security.infrastructure.handler.NonRedirectHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.servlet.DispatcherType;
import java.util.EnumSet;

import static com.kasakaid.security.spring.SecurityConfig.LOGIN_PROCESS_URL;

@Configuration
public class SecurityConfiguration {
    @Bean
    FilterRegistrationBean registration(LoggingFilter filter) {
        FilterRegistrationBean registration = new FilterRegistrationBean(filter);
        registration.setDispatcherTypes(EnumSet.allOf(DispatcherType.class));
        registration.addUrlPatterns("/*");
        return registration;
    }

    @Bean
    WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**");
            }
        };
    }

    @Autowired
    private AuthenticationManager authenticationManager;

    AuthenticationFilter authenticationFilter() {
        NonRedirectHandler successHandler = new NonRedirectHandler();
        AuthenticationFilter filter = new AuthenticationFilter();
        filter.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher(
                LOGIN_PROCESS_URL));
        filter.setAuthenticationManager(authenticationManager);
        filter.setAuthenticationSuccessHandler(successHandler);
        return filter;
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new Pbkdf2PasswordEncoder();
    }
}
