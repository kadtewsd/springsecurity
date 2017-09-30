package com.kasakaid.security.infrastructure.handler;

import com.kasakaid.security.infrastructure.environment.EnvironmentHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
    @Slf4j
    public class SecurityApplicationReadyEventHandler implements ApplicationListener<ApplicationReadyEvent> {

        @Override
        public void onApplicationEvent(ApplicationReadyEvent event) {
            log.info("application is ready to start...");
            Environment environment = event.getApplicationContext().getEnvironment();
            EnvironmentHelper.setEnvironment(environment);
        }
    }
