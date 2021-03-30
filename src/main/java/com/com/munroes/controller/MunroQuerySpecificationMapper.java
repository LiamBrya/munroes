package com.com.munroes.controller;

import com.com.munroes.data.query.MunroQuerySpecification;
import com.com.munroes.data.query.RecordSortParser;
import com.com.munroes.data.query.RecordSorter;
import com.com.munroes.model.Designation;
import com.com.munroes.model.Munro;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * {@link ControllerAdvice} for providing a {@link MunroQuerySpecification} from the
 * query-parameters specified in a request.
 */
@ControllerAdvice
class MunroQuerySpecificationMapper {

    private final RecordSortParser parser;

    MunroQuerySpecificationMapper(final RecordSortParser parser) {
        Assert.notNull(parser, "'parser' must not be 'null' or empty");

        this.parser = parser;
    }

    @ModelAttribute
    MunroQuerySpecification generateQuery(final @RequestParam(required = false) Integer limit,
                                          final @RequestParam(required = false) Double minHeight,
                                          final @RequestParam(required = false) Double maxHeight,
                                          final @RequestParam(required = false, name = "type")
                                                  List<String> types,
                                          final @RequestParam(required = false, name = "sort")
                                                  List<String> sorts) {

        return new MunroQuerySpecification(
                limit, minHeight, maxHeight, buildTypesSet(types), buildSorters(sorts));
    }


    /**
     * Generates a {@link Set} of the desired {@link Designation Designations} from the query
     * parameters in the request.
     * <p>
     * If no {@code types} are specified, then this will default to allowing <strong>all</strong>
     * {@code Designations}.
     *
     * @param types the {@code types} found in the query-parameter of the request.
     *
     * @return a {@code Set} containing the desired types.
     */
    // No real benefit in supporting multiple of these at the moment.  However this allows for
    // extension to other types trivially - as well as removes the element of surprise if a
    // client requests both types, but only the first is observed.
    private static Set<Designation> buildTypesSet(final List<String> types) {
        if (types == null) {
            // If no types specified, then return null.
            // This is interpreted in the InMemoryMunroStore as meaning no filtering on type.
            return null;
        }

        final Set<Designation> allowedTypes = new HashSet<>(types.size());
        for (final String type : types) {
            allowedTypes.add(Designation.valueOf(type.toUpperCase()));
        }

        // Additional logic could be placed here for if all types were provided, to avoid
        // unnecessary filtering in the store.
        return Collections.unmodifiableSet(allowedTypes);
    }

    /**
     * Generates a {@link List} of {@link Comparator Comparators} (for {@link Munro Munroes}) as
     * detailed by the query-parameter(s) for {@code sort-ing} in the request.
     *
     * @param sorterStrings the {@code sort} query-parameter(s) from the request
     *
     * @return a {@code List} of the requested {@code Comparators}.  The order of these will match
     * the order they were specified in the query-parameters.
     */
    private List<Comparator<Munro>> buildSorters(final List<String> sorterStrings) {
        if (sorterStrings == null) {
            // No sorting required.
            return Collections.emptyList();
        }

        final List<RecordSorter<Munro>> sorters = new ArrayList<>(sorterStrings.size());
        for (final String sorterString : sorterStrings) {
            final RecordSorter<Munro> recordSorter = this.parser.parseSortString(sorterString);

            // 'null' is used to indicate an unsupported/unknown property being sorted on.
            // In this case the sorting on that property is a no-op, and so no sorter is added.
            if (recordSorter != null) {
                sorters.add(recordSorter);
            }
        }

        return Collections.unmodifiableList(sorters);
    }
}
