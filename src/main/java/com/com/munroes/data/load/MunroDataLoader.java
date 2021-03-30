package com.com.munroes.data.load;

import com.com.munroes.model.Munro;

import java.util.Set;

/**
 * Declares a means for loading data into a given {@link Set} of {@link Munro Munroes}.
 */
// This is only useful for pre-population of an in-memory store.  With a database, there would be
// no use for pre-loading data.
public interface MunroDataLoader {

    /**
     * Will load this {@link MunroDataLoader MunroDataLoader's} data into the provided {@link Set}
     * of {@link Munro Munroes}.
     *
     * @param munroes the {@code Set} of {@code Munroes} to load data into.
     */
    void loadInto(Set<Munro> munroes);
}
