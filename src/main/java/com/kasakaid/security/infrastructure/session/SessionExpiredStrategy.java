package com.kasakaid.security.infrastructure.session;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SessionExpiredStrategy implements SessionInformationExpiredStrategy {

    @SneakyThrows
    @Override
    public void onExpiredSessionDetected(SessionInformationExpiredEvent event)  {

        DefaultRedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
        redirectStrategy.sendRedirect(event.getRequest(), event.getResponse(), "/error/sessionTimeout");
    }
}
