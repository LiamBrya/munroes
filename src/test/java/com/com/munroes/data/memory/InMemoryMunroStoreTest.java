package com.com.munroes.data.memory;

import com.com.munroes.data.MunroStore;
import com.com.munroes.data.query.MunroQuerySpecification;
import com.com.munroes.data.query.RecordSorter;
import com.com.munroes.model.Designation;
import com.com.munroes.model.Munro;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.swing.SortOrder;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@SpringBootTest
public class InMemoryMunroStoreTest {

    @Autowired
    private MunroStore store;

    @Test
    void emptyQueryReturnsAllMunroes() {
        final List<Munro> munroes = this.store.query(
                new MunroQuerySpecification(null, null, null, null, null));

        Assertions.assertEquals(509, munroes.size());
    }

    @Test
    void limitedQueryReturnsCorrectAmount() {
        final int expectedLimit = 10;
        final List<Munro> munroes = this.store.query(
                new MunroQuerySpecification(expectedLimit, null, null, null, null));

        Assertions.assertEquals(expectedLimit, munroes.size());
    }

    @Test
    void queryWithMinHeightReturnsOnlyThoseOverHeight() {
        final double minHeight = 800.0D; // M
        final List<Munro> munroes = this.store.query(
                new MunroQuerySpecification(null, minHeight, null, null, null));

        munroes.forEach(munro -> {
            Assertions.assertTrue(munro.getHeight() >= minHeight);
        });
    }

    @Test
    void queryWithMaxHeightReturnsOnlyThoseOverHeight() {
        final double maxHeight = 1000.0D; // M
        final List<Munro> munroes = this.store.query(
                new MunroQuerySpecification(null, null, maxHeight, null, null));

        munroes.forEach(munro -> {
            Assertions.assertTrue(munro.getHeight() <= maxHeight);
        });
    }

    @Test
    void queryWithTypeSpecifiedReturnsOnlyThatType() {
        final Designation expectedType = Designation.TOP;

        final List<Munro> munroes = this.store.query(
                new MunroQuerySpecification(null,
                                            null,
                                            null,
                                            Collections.singleton(expectedType),
                                            null));

        munroes.forEach(munro -> {
            Assertions.assertEquals(expectedType, munro.getDesignation());
        });
    }

    @Test
    void queryWithHeightSortReturnsAppropriateOrder() {
        final RecordSorter<Munro> heightDescendingSorter = new RecordSorter<>(Munro::getHeight,
                                                                              SortOrder.DESCENDING);

        final List<Munro> munroes = this.store.query(
                new MunroQuerySpecification(null, null, null, null,
                                            Collections.singletonList(heightDescendingSorter)));

        // Entering a lambda, so variable needs to be final.
        // Slightly hacky to use a 1-element array for this approach.
        // Start at Double.MAX_VALUE to ensure first element works.
        final double[] lastHeight = new double[] {Double.MAX_VALUE};
        munroes.forEach(munro -> {
            // Current one should be less high (or equal to) the previous.
            Assertions.assertTrue(munro.getHeight() <= lastHeight[0]);

            // Update the stored 'lastHeight'.
            lastHeight[0] = munro.getHeight();
                        });
    }

    // This test will need updating if more types are added, logic currently relies on there
    // being only two types.
    @Test
    void queryWithMultipleSortersReturnsAppropriateOrder() {
        final RecordSorter<Munro> designationSorter = new RecordSorter<>(Munro::getDesignation,
                                                                         SortOrder.ASCENDING);
        final RecordSorter<Munro> heightSorter = new RecordSorter<>(Munro::getHeight,
                                                                    SortOrder.ASCENDING);

        final List<Comparator<Munro>> sorters =
                Arrays.asList(designationSorter, heightSorter);

        final List<Munro> munroes = this.store.query(
                new MunroQuerySpecification(null, null, null, null, sorters));

        // Tracking designation and last height across results.
        Designation currentType = Designation.MUN;
        double lastHeight = Double.MIN_VALUE;
        boolean isFirstOfNewType = true;

        for (final Munro munro : munroes) {
            if (currentType != munro.getDesignation()) {
                if (isFirstOfNewType) {
                    // One swap of type is allowed.  Any more swaps and they weren't sorted by type
                    // first.
                    currentType = munro.getDesignation();

                    // Reset the last height when changing types, since height was a secondary
                    // ordering.
                    lastHeight = Double.MIN_VALUE;

                    isFirstOfNewType = false;
                } else {
                    // More than one swap, the sorting didn't work.
                    Assertions.fail("Expected ordering by type");
                }
            }

            Assertions.assertEquals(currentType, munro.getDesignation());
            Assertions.assertTrue(munro.getHeight() >= lastHeight);
            lastHeight = munro.getHeight();
        }
    }
}
