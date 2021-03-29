package com.com.munroes.controller;

import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@Log
class MunroControllerExceptionAdapter {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    void handleIllegalArgument(final IllegalArgumentException iaEx) {
        log.warning("Bad request: " + iaEx.getMessage());
    }
}
