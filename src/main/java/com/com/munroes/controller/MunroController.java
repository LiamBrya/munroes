package com.com.munroes.controller;

import com.com.munroes.service.MunroService;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MunroController {

    private final MunroService service;

    public MunroController(final MunroService service) {
        Assert.notNull(service, "'service' must not be 'null'");

        this.service = service;
    }
}
