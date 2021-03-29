package com.com.munroes.data.memory;

import com.com.munroes.data.MunroStore;
import com.com.munroes.data.load.MunroDataLoader;
import com.com.munroes.model.Munro;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

@Repository
public class InMemoryMunroStore implements MunroStore {

    private final Set<Munro> munroes;

    public InMemoryMunroStore(final MunroDataLoader loader) {
        Assert.notNull(loader, "'loader' must not be 'null'");

        this.munroes = new CopyOnWriteArraySet<>();

        loader.loadInto(munroes);
    }
}
