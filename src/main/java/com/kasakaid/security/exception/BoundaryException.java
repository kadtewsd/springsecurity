package com.kasakaid.security.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.net.URL;

@Data
public class BoundaryException extends RuntimeException {
    private HttpStatus httpStatus;

    private URL destination;

    private String restMethod;
}
