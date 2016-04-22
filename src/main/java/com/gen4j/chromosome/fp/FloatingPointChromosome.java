package com.gen4j.chromosome.fp;

import static com.google.common.base.Preconditions.checkArgument;
import static java.util.Objects.requireNonNull;

import com.gen4j.chromosome.Chromosome;

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

}
