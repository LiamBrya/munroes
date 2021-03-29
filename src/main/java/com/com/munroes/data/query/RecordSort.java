package com.com.munroes.data.query;

import org.springframework.util.Assert;

import javax.swing.SortOrder;
import java.util.List;
import java.util.function.Function;

// Usage ensures that 'Comparable' is used appropriately for the given types.
// Without this a more complex lookup model would need to be developed rather than storing
// the basic Functions in a Map in RecordSortParser.
@SuppressWarnings("rawtypes")
public class RecordSort<T> {

    private final Function<T, Comparable> propertyFinder;
    private final SortOrder order;

    public RecordSort(final Function<T, Comparable> propertyFinder,
                      final SortOrder sortOrder) {
        Assert.notNull(propertyFinder, "'propertyFinder' must not be 'null'");
        Assert.notNull(sortOrder, "'sortOrder' must not be 'null'");

        this.propertyFinder = propertyFinder;
        this.order = sortOrder;
    }


    public void sort(final List<T> records) {
        records.sort((first, second) -> {
            if (order == SortOrder.UNSORTED) {
                // No sorting required, specified as UNSORTED
                return 0;
            }

            int result = this.propertyFinder.apply(first)
                    .compareTo(this.propertyFinder.apply(second));

            return this.order == SortOrder.ASCENDING ? result : -result;
        });
    }
}
