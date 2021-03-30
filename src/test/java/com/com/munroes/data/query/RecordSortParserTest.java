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

    private static final Munro first =
            new Munro("A", GridReference.of("NN111111"), 10.0D, Designation.MUN);
    private static final Munro second =
            new Munro("B", GridReference.of("NN222222"), 20.0D, Designation.TOP);

    private final RecordSortParser parser = new RecordSortParser();

    @Test
    void testParserParsesName() {
        final RecordSorter<Munro> result = parser.parseSortString("name-ASC");

        final List<Munro> munroes = new ArrayList<>();
        // Initially out of order.
        munroes.addAll(Arrays.asList(second, first));

        munroes.sort(result);

        Assertions.assertAll(
                () -> Assertions.assertEquals(2, munroes.size()),
                () -> Assertions.assertEquals(first, munroes.get(0)),
                () -> Assertions.assertEquals(second, munroes.get(1)));
    }

    @Test
    public void testParserDefaultsToAscending() {
        final RecordSorter<Munro> result = parser.parseSortString("name");

        final List<Munro> munroes = new ArrayList<>();

        // Initially out of order.
        munroes.addAll(Arrays.asList(second, first));

        munroes.sort(result);

        Assertions.assertAll(
                () -> Assertions.assertEquals(2, munroes.size()),
                () -> Assertions.assertEquals(first, munroes.get(0)),
                () -> Assertions.assertEquals(second, munroes.get(1)));
    }

    @Test
    public void testParserHandlesHeight() {
        final RecordSorter<Munro> result = parser.parseSortString("height-des");

        final List<Munro> munroes = new ArrayList<>();

        // Descending sort, so expected order is now second, first.
        munroes.addAll(Arrays.asList(first, second));

        munroes.sort(result);

        Assertions.assertAll(
                () -> Assertions.assertEquals(2, munroes.size()),
                () -> Assertions.assertEquals(second, munroes.get(0)),
                () -> Assertions.assertEquals(first, munroes.get(1)));
    }
}
