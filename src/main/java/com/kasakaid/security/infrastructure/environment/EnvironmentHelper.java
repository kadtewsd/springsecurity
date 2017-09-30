package com.kasakaid.security.infrastructure.environment;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;

@Slf4j
public final class EnvironmentHelper {

    private static Environment env;

    public static void setEnvironment(Environment env) {
        EnvironmentHelper.env = env;
    }

    public static String getPaaSEndpoint() {
        return env.getProperty("environment.paasEndpoint");
        //return properties()..getProperty("environment.paasEndpoint");
    }

    public static String getProperty(String key) {
        return env.getProperty(key);
    }
}
