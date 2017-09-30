package com.kasakaid.security.infrastructure.filter;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.UUID;

@Component
@Slf4j
public class LoggingFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("Application Filter is initialized");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        log.info("Application doFilter begins");
        HttpServletRequest httpRequest = ((HttpServletRequest) request);
        StringBuffer requestUrl = httpRequest.getRequestURL();
        MDC.put("requestUrl", requestUrl.toString());
        MDC.put("correlationId", UUID.randomUUID().toString());
        log.info("Application doFilter ends... do chain will begin");
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        log.info("Application Filter is destroyed");
    }
}
