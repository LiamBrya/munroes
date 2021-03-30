package com.com.munroes.data.query;

import com.com.munroes.model.Munro;
import org.springframework.stereotype.Component;

import javax.swing.SortOrder;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * Parser for converting a {@code sort} query-parameter into a valid {@link RecordSorter}.
 */
@Component
public class RecordSorterParser {

    private final Map<String, Function<Munro, Comparable>> propertyFunctionMap;

    {
        final Map<String, Function<Munro, Comparable>> localPropertyFunctionMap =
                new HashMap<>();

        localPropertyFunctionMap.put("height", Munro::getHeight);
        localPropertyFunctionMap.put("name", Munro::getName);
        localPropertyFunctionMap.put("category", munro -> munro.getDesignation().toString());
        localPropertyFunctionMap
                .put("gridReference", munro -> munro.getGridReference().getReference());

        this.propertyFunctionMap = Collections.unmodifiableMap(localPropertyFunctionMap);
    }

    public RecordSorter<Munro> parseSortString(final String sortString) {
        final SortDetails sortDetails = new SortDetails(sortString);

        if (!this.propertyFunctionMap.containsKey(sortDetails.property)) {
            // Not technically an error to try and sort on some non-existent property, it just
            // has no impact.
            return null;
        }

        return new RecordSorter<>(this.propertyFunctionMap.get(sortDetails.property),
                sortDetails.order);
    }


    /**
     * Helper class for splitting apart a single string into a sort-specification.
     * <p>
     * Overkill in this case, but allows simple extension, as well as setting per-property
     * defaults.
     */
    private static final class SortDetails {

        private final SortOrder order;
        private final String property;

        private SortDetails(final String sortDetails) {
            final String[] parts = sortDetails.split("-");

            this.property = parts[0].toLowerCase();
            if (parts.length == 1) {
                this.order = SortOrder.ASCENDING;
            } else {
                this.order = parseOrder(parts);
            }
        }


        private static SortOrder parseOrder(final String[] parts) {
            switch (parts[1].toUpperCase()) {
                case "ASC":
                    return SortOrder.ASCENDING;
                case "DES":
                    return SortOrder.DESCENDING;
                default:
                    throw new IllegalArgumentException("Unknown order: " + parts[1]);
            }
        }
    }
}
