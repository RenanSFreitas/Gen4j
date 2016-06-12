package com.gen4j.chromosome.fp;

import static com.google.common.base.Preconditions.checkArgument;
import static java.util.Objects.requireNonNull;

import java.util.Arrays;
import java.util.Objects;

import com.gen4j.chromosome.Chromosome;

/**
 * Chromosome of real valued genes.
 */
public class FloatingPointChromosome implements Chromosome {

    private final double[] value;

    public FloatingPointChromosome(final double[] value) {
        this.value = requireNonNull(value);
        checkArgument(value.length > 0);
    }

    @Override
    public double[] value() {
        return value;
    }

    @Override
    public int length() {
        return value.length;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof FloatingPointChromosome)) {
            return false;
        }

        return Arrays.equals(value, ((FloatingPointChromosome) obj).value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

}
