package com.gen4j.utils;

import java.util.List;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;

public final class Lists {

    private Lists() {
        throw new AssertionError("No instance allowed");
    }

    public static <T> List<T> repeat(final T instance, final int times) {

        Preconditions.checkNotNull(instance);

        final Builder<T> builder = ImmutableList.<T> builder();

        for (int i = 0; i < times; i++) {
            builder.add(instance);
        }

        return builder.build();
    }
}
