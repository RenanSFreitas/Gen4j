package com.gen4j.utils;

public final class Strings {

    private Strings() {
        throw new AssertionError("No instance for you.");
    }

    public static String reverse(final String string) {
        return new StringBuilder(string).reverse().toString();
    }
}
