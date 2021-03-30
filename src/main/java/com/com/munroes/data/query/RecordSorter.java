package com.com.munroes.data.query;

import org.springframework.util.Assert;

import javax.swing.SortOrder;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;

/**
 * Basic sorting-class which will {@link List#sort(Comparator) sort} a {@code List} using some
 * specific property (as detailed by the {@link #propertyFinder} provided at construction).
 *
 * @param <T> the type of records to be sorted.
 */
// Usage ensures that 'Comparable' is used appropriately for the given types.
// Without this a more complex lookup model would need to be developed rather than storing
// the basic Functions in a Map in RecordSortParser.
@SuppressWarnings("rawtypes")
public class RecordSorter<T> implements Comparator<T> {

    private final Function<T, Comparable> propertyFinder;
    private final SortOrder order;

    public RecordSorter(final Function<T, Comparable> propertyFinder,
                        final SortOrder sortOrder) {
        Assert.notNull(propertyFinder, "'propertyFinder' must not be 'null'");
        Assert.notNull(sortOrder, "'sortOrder' must not be 'null'");

        this.propertyFinder = propertyFinder;
        this.order = sortOrder;
    }


    @Override
    public int compare(final T o1, final T o2) {
        if (order == SortOrder.UNSORTED) {
            // No sorting required, specified as UNSORTED
            // This isn't expected to happen, but no reason to break if it does.
            return 0;
        }

        // To make this more general, reflection could be used to obtain the property and
        // check that it implemented an appropriate 'Comparable' to itself.
        // That seemed overkill when there's only 4 properties on our API class, but it would
        // make this class a lot simpler to use.
        int result = this.propertyFinder.apply(o1)
                                        .compareTo(this.propertyFinder.apply(o2));

        // Negate the result for descending sorting.
        return this.order == SortOrder.ASCENDING ? result : -result;
    }
}
