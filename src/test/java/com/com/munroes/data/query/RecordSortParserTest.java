package com.com.munroes.data.query;

import com.com.munroes.model.Designation;
import com.com.munroes.model.GridReference;
import com.com.munroes.model.Munro;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class RecordSortParserTest {

    private final RecordSortParser parser = new RecordSortParser();

    @Test
    void testParserParsesName() {
        final RecordSort<Munro> result = parser.parseSortString("name-ASC");

        final List<Munro> munroes = new ArrayList<>();
        final Munro first = new Munro("A", GridReference.of("NN111111"), 10.0D, Designation.MUN);
        final Munro second = new Munro("B", GridReference.of("NN222222"), 20.0D, Designation.TOP);

        // Initially out of order.
        munroes.addAll(Arrays.asList(second, first));

        result.sort(munroes);

        Assertions.assertAll(
                () -> Assertions.assertEquals(2, munroes.size()),
                () -> Assertions.assertEquals(first, munroes.get(0)),
                () -> Assertions.assertEquals(second, munroes.get(1)));
    }

    @Test
    public void testParserDefaultsToAscending() {
        final RecordSort<Munro> result = parser.parseSortString("name");

        final List<Munro> munroes = new ArrayList<>();
        final Munro first = new Munro("A", GridReference.of("NN111111"), 10.0D, Designation.MUN);
        final Munro second = new Munro("B", GridReference.of("NN222222"), 20.0D, Designation.TOP);

        // Initially out of order.
        munroes.addAll(Arrays.asList(second, first));

        result.sort(munroes);

        Assertions.assertAll(
                () -> Assertions.assertEquals(2, munroes.size()),
                () -> Assertions.assertEquals(first, munroes.get(0)),
                () -> Assertions.assertEquals(second, munroes.get(1)));
    }

    @Test
    public void testParserHandlesHeight() {
        final RecordSort<Munro> result = parser.parseSortString("height-des");

        final List<Munro> munroes = new ArrayList<>();
        final Munro first = new Munro("A", GridReference.of("NN111111"), 10.0D, Designation.MUN);
        final Munro second = new Munro("B", GridReference.of("NN222222"), 20.0D, Designation.TOP);

        // Descending sort, so expected order is now second, first.
        munroes.addAll(Arrays.asList(first, second));

        result.sort(munroes);

        Assertions.assertAll(
                () -> Assertions.assertEquals(2, munroes.size()),
                () -> Assertions.assertEquals(second, munroes.get(0)),
                () -> Assertions.assertEquals(first, munroes.get(1)));
    }
}
