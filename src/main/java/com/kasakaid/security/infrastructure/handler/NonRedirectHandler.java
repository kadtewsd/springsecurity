package com.kasakaid.security.infrastructure.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

@Component
public class NonRedirectHandler extends SavedRequestAwareAuthenticationSuccessHandler {
//public class NonRedirectHandler extends SimpleUrlAuthenticationSuccessHandler {

    private RequestCache requestCache = new HttpSessionRequestCache();

    private ObjectMapper objectMapper;

    public NonRedirectHandler() {
        this.objectMapper = new ObjectMapper();
    }

    @SneakyThrows
    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication) {

        SavedRequest savedRequest
                = requestCache.getRequest(request, response);

        HttpServletResponse res = (HttpServletResponse) response;
        // これらは write 前に書かないといけない
        res.setStatus(HttpServletResponse.SC_OK);
        String allowedOrigins = "http://wfe.kasakaid.com";
        res.setHeader("Access-Control-Allow-Origin", allowedOrigins);
        res.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        res.setHeader("Access-Control-Max-Age", "3600");
        res.setHeader("Access-Control-Allow-Headers", "x-requested-with");
        writeResult(res, authentication);
        if (savedRequest == null) {
            clearAuthenticationAttributes(request);
            return;
        }
        String targetUrlParam = getTargetUrlParameter();
        if (isAlwaysUseDefaultTargetUrl()
                || (targetUrlParam != null
                && StringUtils.hasText(request.getParameter(targetUrlParam)))) {
            requestCache.removeRequest(request, response);
            clearAuthenticationAttributes(request);
            return;
        }

        clearAuthenticationAttributes(request);
    }

    public void setRequestCache(RequestCache requestCache) {
        this.requestCache = requestCache;
    }

    @SneakyThrows
    private void writeResult(HttpServletResponse response, Authentication authentication) {
        PrintWriter writer = response.getWriter();
        objectMapper.writeValue(writer, authentication);
        writer.flush();
    }
}

