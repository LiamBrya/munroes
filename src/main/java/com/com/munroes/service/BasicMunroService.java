package com.com.munroes.service;

import com.com.munroes.data.MunroStore;
import com.com.munroes.data.query.MunroQuerySpecification;
import com.com.munroes.model.Munro;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

/**
 * Very basic implementation of {@link MunroService} which forwards directly to an underlying {@link
 * MunroStore}.
 */
@Service
public class BasicMunroService implements MunroService {

    private final MunroStore store;

    public BasicMunroService(final MunroStore store) {
        Assert.notNull(store, "'store' must not be 'null'");

        this.store = store;
    }


    @Override
    public List<Munro> query(final MunroQuerySpecification query) {
        return this.store.query(query);
    }
}
