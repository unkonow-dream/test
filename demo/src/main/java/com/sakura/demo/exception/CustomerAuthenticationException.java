package com.sakura.demo.exception;

import org.springframework.security.core.AuthenticationException;

public class CustomerAuthenticationException extends AuthenticationException {


    public CustomerAuthenticationException(String msg) {
        super(msg);
    }
}
