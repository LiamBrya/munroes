package com.com.munroes.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.util.Assert;

@EqualsAndHashCode
public final class GridReference {

    private static final String REGEX = "^[a-zA-Z]{2}\\d{6}$";

    @Getter
    private final String reference;

    private GridReference(final String reference) {
        Assert.hasText(reference, "'reference' must not be 'null' or empty");
        Assert.isTrue(reference.matches(REGEX),
                "The provided reference: '" + reference
                        + "' is not recognised as a valid grid reference");

        this.reference = reference;
    }


    public static GridReference of(final String reference) {
        return new GridReference(reference);
    }
}
