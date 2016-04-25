package com.gen4j.chromosome.fp;

import static com.google.common.base.Preconditions.checkArgument;
import static java.util.Objects.requireNonNull;

import com.gen4j.chromosome.Chromosome;
import com.gen4j.chromosome.Range;

public class FloatingPointChromosome implements Chromosome {

    private final double[] value;
    private final Range range;

    public FloatingPointChromosome(final double[] value, final Range range) {
        this.value = requireNonNull(value);
        checkArgument(value.length > 0);
        this.range = range;
    }

    @Override
    public double[] value() {
        return value;
    }

    @Override
    public int length() {
        return value.length;
    }

    public Range range() {
        return range;
    }
}
