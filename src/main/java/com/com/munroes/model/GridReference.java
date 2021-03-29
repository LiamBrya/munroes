package com.com.munroes.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.util.Assert;

@EqualsAndHashCode
public final class GridReference {

    private static final String REGEX = "^[a-zA-Z]{2}\\d{6}$";

    @Getter
    private final String reference;

    public GridReference(final String reference) {
        Assert.isTrue(REGEX.matches(reference),
            "The provided reference: '" + reference + "' is not recognised as a valid grid reference");

        this.reference = reference;
    }
}
