package com.com.munroes.model;

public enum Designation {
    MUN("Munro"),
    TOP("Munro Top");

    private final String fullName;

    Designation(final String fullName) {
        this.fullName = fullName;
    }

    public static Designation from(final String name) {
        try {
            return Designation.valueOf(name.toUpperCase());
        } catch (final IllegalArgumentException iaEx) {
            throw new IllegalArgumentException("Unrecognised Designation: " + name, iaEx);
        }
    }

    @Override
    public String toString() {
        return this.fullName;
    }
}
