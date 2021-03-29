package com.com.munroes.service;

import com.com.munroes.data.MunroStore;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class BasicMunroService implements MunroService {

    private final MunroStore store;

    public BasicMunroService(MunroStore store) {
        Assert.notNull(store, "'store' must not be 'null'");

        this.store = store;
    }
}
