package com.com.munroes.model;

import lombok.Data;
import lombok.NonNull;

import javax.validation.constraints.Positive;

@Data
public final class Munro {

    private final @NonNull String name;
    private final @NonNull GridReference gridReference;
    private final @Positive double height;
    private final @NonNull Designation designation;
}
