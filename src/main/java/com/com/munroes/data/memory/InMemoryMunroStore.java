package com.com.munroes.data.memory;

import com.com.munroes.data.MunroStore;
import com.com.munroes.data.load.MunroDataLoader;
import com.com.munroes.data.query.MunroQuerySpecification;
import com.com.munroes.model.Munro;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Implementation of {@link MunroStore} which stores the data in memory.
 * <p>
 * The {@link MunroDataLoader} passed in at construction is used to provide the values stored in
 * memory.
 */
@Repository
public class InMemoryMunroStore implements MunroStore {

    private final Set<Munro> munroes;

    public InMemoryMunroStore(final MunroDataLoader loader) {
        Assert.notNull(loader, "'loader' must not be 'null'");

        final Set<Munro> localMunroes = new HashSet<>();
        loader.loadInto(localMunroes);

        this.munroes = Collections.unmodifiableSet(localMunroes);
    }


    @Override
    public List<Munro> query(final MunroQuerySpecification query) {
        final List<Munro> unsortedResult = buildQueryStream(query).collect(Collectors.toList());

        // Reverse order for iteration over sorters.
        // This gives the highest preference on final order to the sorters earliest in the list.
        for (int i = query.getSorters().size() - 1; i >= 0; i--) {
            query.getSorters().get(i).sort(unsortedResult);
        }

        return Collections.unmodifiableList(unsortedResult);
    }


    /**
     * Builds a {@link Stream} of {@link Munro Munroes} using the {@link #munroes} stored in memory,
     * and the provided {@link MunroQuerySpecification query} for specifying restrictions on the
     * elements returned.
     * <p>
     * Note: The returned {@code Stream} will <strong>not</strong> be sorted.
     *
     * @param query the query detailing which {@code Munroes} to return.
     *
     * @return an unsorted {@code Stream} of {@code Munroes} which match the provided {@code query}
     */
    // This logic is placed here as typically this would be done in SQL/Hibernate layer.
    // If this was intended to /always/ be in memory, then more of the logic could fall into the
    // query-specification.
    private Stream<Munro> buildQueryStream(final MunroQuerySpecification query) {
        Stream<Munro> stream = munroes.stream();

        stream = stream.filter(munro -> query.getTypes().contains(munro.getDesignation()));

        if (query.getMaxHeight() != null) {
            stream = stream.filter(munro -> munro.getHeight() <= query.getMaxHeight());
        }
        if (query.getMinHeight() != null) {
            stream = stream.filter(munro -> munro.getHeight() >= query.getMinHeight());
        }

        // Ensure that 'limit' is observed last.  Otherwise fewer results than expected may get
        // returned after other restrictions are applied.
        if (query.getLimit() != null) {
            stream = stream.limit(query.getLimit());
        }

        return stream;
    }
}
