package com.com.munroes.model;

public enum Designation {
    MUN("Munro"),
    TOP("Munro Top");

    private final String fullName;

    Designation(final String fullName) {
        this.fullName = fullName;
    }

    @Override
    public String toString() {
        return this.fullName;
    }
}
