package com.com.munroes.controller;

import com.com.munroes.data.query.MunroQuerySpecification;
import com.com.munroes.service.MunroService;
import com.com.munroes.view.MunroView;
import lombok.extern.java.Log;
import org.springframework.http.MediaType;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/api/munroes",
                produces = MediaType.APPLICATION_JSON_VALUE)
@Log
public class MunroController {

    private final MunroService service;

    public MunroController(final MunroService service) {
        Assert.notNull(service, "'service' must not be 'null'");

        this.service = service;
    }


    @GetMapping
    public List<MunroView> queryMunroes(final @Valid @ModelAttribute
                                                MunroQuerySpecification query) {

        final List<MunroView> views =
                this.service.query(query).stream().map(MunroView::new).collect(Collectors.toList());

        log.info("Munroes queried, returning " + views.size() + " results");

        return views;
    }
}
