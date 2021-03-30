package com.com.munroes.service;

import com.com.munroes.data.query.MunroQuerySpecification;
import com.com.munroes.model.Munro;

import java.util.List;

/**
 * Service layer for obtaining {@link Munro Munroes}.
 * <p>
 * Note: Only retrieval is supported.
 */
// This is an unnecessary layer for the complexity of this API, however if/when more
// logic were needed, the implementations of this would be the place to put it to avoid
// transactions bleeding out beyond their needed scope.
public interface MunroService {

    /**
     * Obtains a {@link List} of {@link Munro Munroes} which match the provided {@link
     * MunroQuerySpecification query} details.
     *
     * @param query the {@code query} to be observed
     *
     * @return a {@code List} of the matching {@code Munroes}.
     */
    List<Munro> query(MunroQuerySpecification query);
}
