package com.gen4j.chromosome.bit;

import static com.google.common.base.Preconditions.checkArgument;
import static java.util.Objects.requireNonNull;

import java.math.RoundingMode;
import java.util.BitSet;

import com.gen4j.chromosome.Chromosome;
import com.gen4j.utils.BitSets;
import com.google.common.math.IntMath;

public final class BitChromosome implements Chromosome {

    private final BitSet bits;
    private final int length;

    public BitChromosome(final BitSet bits, final int length) {
        this.bits = requireNonNull(bits);

        checkArgument(length > 0 && IntMath.log2(length, RoundingMode.CEILING) <= bits.size());
        this.length = length;
    }

    public BitChromosome(final BitChromosome chromosome) {
        length = requireNonNull(chromosome).length();
        bits = new BitSet(length());
        bits.or(chromosome.value());
    }

    @Override
    public BitSet value() {
        return bits;
    }

    @Override
    public int length() {
        return length;
    }

    @Override
    public int hashCode() {
        return bits.hashCode();
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj == this) {
            return true;
        }

        if (!(obj instanceof BitChromosome)) {
            return false;
        }

        return bits.equals(((BitChromosome) obj).bits);
    }

    @Override
    public String toString() {
        return BitSets.toString(value(), length());
    }

}
