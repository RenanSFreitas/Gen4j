package com.gen4j.utils;

import static java.util.Objects.requireNonNull;

import java.util.Objects;

import com.google.common.base.MoreObjects;

public class Pair<F, S> {

    private final F first;
    private final S second;

    private Pair(final F first, final S second) {
        this.first = first;
        this.second = second;
    }

    public F first() {
        return first;
    }

    public S second() {
        return second;
    }

    public static <F, S> Pair<F, S> of(final F first, final S second) {
        return new Pair<>(requireNonNull(first, "First is null"), requireNonNull(second, "Second is null"));
    }

    @Override
    public int hashCode() {
        return Objects.hash(first, second);
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof Pair)) {
            return false;
        }

        final Pair<?, ?> that = (Pair<?, ?>) obj;
        return this.first.equals(that.first) && this.second.equals(that.second);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(Pair.class).add("first", first).add("second", second).toString();
    }
}
