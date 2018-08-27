package com.seha.TaskProject.service.exceptions;

import javax.ws.rs.ext.Provider;

@Provider
public class BadUserException extends RuntimeException {
    public BadUserException(String message) {
        super(message);
    }
}