package com.com.munroes.data.query;

import org.springframework.util.Assert;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Implementation of {@link Comparator} which defers comparison to the {@link #comparators} provided
 * at construction.
 *
 * @param <T> the type of elements this {@code Comparator} can {@link #compare(Object, Object) compare}
 */
public class CompositeComparator<T> implements Comparator<T> {

    private final List<Comparator<T>> comparators;

    public CompositeComparator(final List<Comparator<T>> comparators) {
        Assert.notNull(comparators, "'comparators' must not be 'null'");

        this.comparators = Collections.unmodifiableList(comparators);
    }


    /**
     * {@inheritDoc}
     * <p>
     * Implementation defers comparison to the {@link #comparators} provided at construction.
     */
    @Override
    public int compare(final T o1, final T o2) {
        int result = 0;
        for (final Comparator<T> comparator : this.comparators) {
            result = comparator.compare(o1, o2);
            if (result != 0) {
                // Stop once the first comparator marks them as different.
                break;
            }
        }
        return result;
    }
}
