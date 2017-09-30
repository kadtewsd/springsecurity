package com.kasakaid.security.mock;

import org.springframework.security.test.context.support.WithSecurityContext;

@WithSecurityContext(factory = ContextFactory.class)
public @interface KasakaidMockUser {

    String username() default "kasakaid";

    String password() default "hoge";
}
