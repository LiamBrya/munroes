package com.com.munroes.controller;

import com.com.munroes.data.query.MunroQuerySpecification;
import com.com.munroes.data.query.RecordSort;
import com.com.munroes.data.query.RecordSortParser;
import com.com.munroes.model.Designation;
import com.com.munroes.model.Munro;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
                                          final @RequestParam(required = false, name = "type") List<String> types,
                                          final @RequestParam(required = false, name = "sort") List<String> sorts) {

        return new MunroQuerySpecification(
                limit, minHeight, maxHeight, buildTypesSet(types), buildSorters(sorts));
    }


    private static Set<Designation> buildTypesSet(final List<String> types) {
        if (types == null) {
            // If no types specified, default to all being valid.
            return EnumSet.allOf(Designation.class);
        }

        final Set<Designation> allowedTypes = new HashSet<>(types.size());
        for (final String type : types) {
            allowedTypes.add(Designation.valueOf(type.toUpperCase()));
        }
        return Collections.unmodifiableSet(allowedTypes);
    }

    private List<RecordSort<Munro>> buildSorters(final List<String> sorterStrings) {
        if (sorterStrings == null) {
            // No sorting required.
            return Collections.emptyList();
        }

        final List<RecordSort<Munro>> sorters = new ArrayList<>(sorterStrings.size());
        for (final String sorterString : sorterStrings) {
            final RecordSort<Munro> recordSorter = this.parser.parseSortString(sorterString);
            if (recordSorter != null) {
                sorters.add(recordSorter);
            }
        }

        return Collections.unmodifiableList(sorters);
    }

}
