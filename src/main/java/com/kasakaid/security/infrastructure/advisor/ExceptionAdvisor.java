package com.kasakaid.security.infrastructure.advisor;

import com.kasakaid.security.exception.BoundaryException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ExceptionAdvisor {

    @ResponseBody
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception exception) {

        if (isRestController(handler)) {
            return null;
        }

        ModelAndView model = new ModelAndView();
        model.setViewName("my-error");
        return model;
    }

    @org.springframework.web.bind.annotation.ExceptionHandler({ BoundaryException.class })
    @ResponseBody
    public Map<String, Object> handleError(BoundaryException ex) {
        Map<String, Object> errorMap = new HashMap<>();
        errorMap.put("status", ex.getHttpStatus());
        errorMap.put("message", ex.getMessage());
        errorMap.put("destination", ex.getDestination());
        errorMap.put("restMethod", ex.getRestMethod());
        //errorMap.put("cause", ex.getStackTrace());
        return errorMap;
    }

    private boolean isRestController(Object handler) {
        if (handler instanceof HandlerMethod) {
            HandlerMethod method = (HandlerMethod)handler;
            return method.getMethod().getDeclaringClass().isAnnotationPresent(RestController.class);
        }
        return false;
    }

}
