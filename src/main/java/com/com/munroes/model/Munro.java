package com.com.munroes.model;

import lombok.Data;
import lombok.NonNull;

@Data
public final class Munro {

    private final @NonNull String name;
    private final @NonNull GridReference gridReference;
    private final double height;
    private final @NonNull Designation designation;
}
