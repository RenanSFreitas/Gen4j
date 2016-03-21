package com.gen4j.genotype.bit;

import static com.google.common.base.Preconditions.checkArgument;
import static java.util.Objects.requireNonNull;

import java.math.RoundingMode;
import java.util.BitSet;

import com.gen4j.genotype.Genotype;
import com.google.common.math.IntMath;

public final class BitSetGenotype implements Genotype {

    private final BitSet bits;
    private final int length;

    public BitSetGenotype(final BitSet bits, final int length) {
        this.bits = requireNonNull(bits);

        checkArgument(length > 0 && IntMath.log2(length, RoundingMode.CEILING) <= bits.cardinality());
        this.length = length;
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
        final StringBuilder builder = new StringBuilder();
        for (int i = length - 1; i > -1; i--) {
            builder.append(bits.get(i) ? '1' : '0');
        }
        return builder.toString();
    }

}
