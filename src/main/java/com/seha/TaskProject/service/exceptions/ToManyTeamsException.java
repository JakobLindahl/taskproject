package com.seha.TaskProject.service.exceptions;

public class ToManyTeamsException extends RuntimeException {
    public ToManyTeamsException(String message) {
        super(message);
    }
}
