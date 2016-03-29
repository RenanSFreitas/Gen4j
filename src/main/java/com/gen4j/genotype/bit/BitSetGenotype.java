package com.gen4j.genotype.bit;

import static com.google.common.base.Preconditions.checkArgument;
import static java.util.Objects.requireNonNull;

import java.math.RoundingMode;
import java.util.BitSet;

import com.gen4j.genotype.Genotype;
import com.gen4j.utils.BitSets;
import com.google.common.math.IntMath;

public final class BitSetGenotype implements Genotype {

    private final BitSet bits;
    private final int length;

    public BitSetGenotype(final BitSet bits, final int length) {
        this.bits = requireNonNull(bits);

        checkArgument(length > 0 && IntMath.log2(length, RoundingMode.CEILING) <= bits.size());
        this.length = length;
    }

    public BitSetGenotype(final BitSetGenotype genotype) {
        length = requireNonNull(genotype).length();
        bits = new BitSet(length());
        bits.or(genotype.value());
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

        if (!(obj instanceof BitSetGenotype)) {
            return false;
        }

        return bits.equals(((BitSetGenotype) obj).bits);
    }

    @Override
    public String toString() {
        return BitSets.toString(value(), length());
    }

}
