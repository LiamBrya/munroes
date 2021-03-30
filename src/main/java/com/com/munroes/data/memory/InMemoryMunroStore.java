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
import java.util.function.BiFunction;
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

        // Could lazy-load these on first query - however at present that is the only purpose of
        // this service, and doing so would require thread-safety caution to ensure only the
        // first request does so, and that if there are multiple /first/ requests concurrently,
        // only one of them does so and the others await the result.
        final Set<Munro> localMunroes = new HashSet<>();
        loader.loadInto(localMunroes);

        this.munroes = Collections.unmodifiableSet(localMunroes);
    }


    @Override
    public List<Munro> query(final MunroQuerySpecification query) {
        final List<Munro> result = buildQueryStream(query).collect(Collectors.toList());

        return Collections.unmodifiableList(result);
    }


    /**
     * Builds a {@link Stream} of {@link Munro Munroes} using the {@link #munroes} stored in memory,
     * and the provided {@link MunroQuerySpecification query} for specifying restrictions on the
     * elements returned.
     *
     * @param query the query detailing which {@code Munroes} to return.
     *
     * @return an {@code Stream} of {@code Munroes} which match the provided {@code query}
     */
    // This logic is placed here as typically this would be done in DB layer.
    // If this was intended to /always/ be in memory, then more of the logic could fall into the
    // query-specification.
    private Stream<Munro> buildQueryStream(final MunroQuerySpecification query) {
        Stream<Munro> stream = munroes.stream();

        stream = applyIfNotNull(query.getTypes(), stream,
                (i, s) -> s.filter(m -> i.contains(m.getDesignation())));

        stream = applyIfNotNull(query.getMaxHeight(), stream,
                (i, s) -> s.filter(m -> m.getHeight() <= i));
        stream = applyIfNotNull(query.getMinHeight(), stream,
                (i, s) -> s.filter(m -> m.getHeight() >= i));

        stream = applyIfNotNull(query.getSorter(), stream, (i, s) -> s.sorted(i));

        // Ensure that 'limit' is observed last.  Otherwise fewer results than expected may get
        // returned after other restrictions are applied.
        stream = applyIfNotNull(query.getLimit(), stream, (i, s) -> s.limit(i));

        return stream;
    }



    private static <I, T> Stream<T> applyIfNotNull(final I item,
                                                   final Stream<T> stream,
                                                   final BiFunction<I, Stream<T>, Stream<T>> operator) {
        if (item != null) {
            return operator.apply(item, stream);
        }
        return stream;
    }
}
