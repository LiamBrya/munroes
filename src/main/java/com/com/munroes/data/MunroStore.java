package com.com.munroes.data;

import com.com.munroes.data.query.MunroQuerySpecification;
import com.com.munroes.model.Munro;

import java.util.List;

/**
 * Basic repository for Munro elements.
 * <p>
 * Only retrieval is supported.
 */
public interface MunroStore {

    /**
     * Obtains a {@link List} of {@link Munro Munroes} which match the provided
     * {@link MunroQuerySpecification query} details.
     *
     * @param query the {@code query} to be observed
     *
     * @return a {@code List} of the matching {@code Munroes}.
     */
    List<Munro> query(MunroQuerySpecification query);
}
