package com.com.munroes.controller;

import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Simple {@link ControllerAdvice} which maps {@link IllegalArgumentException
 * IllegalArgumentExceptions} to {@link HttpStatus#BAD_REQUEST 400 BAD REQUEST}.
 */
@ControllerAdvice
@Log
class MunroControllerExceptionAdapter {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    void handleIllegalArgument(final IllegalArgumentException iaEx) {
        log.warning("Bad request: " + iaEx.getMessage());
    }
}
