package com.gen4j.factory;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import java.math.RoundingMode;
import java.util.BitSet;
import java.util.List;
import java.util.Set;

import com.gen4j.genotype.GenotypeEncoder;
import com.gen4j.genotype.bit.BitSetGenotype;
import com.gen4j.phenotype.Phenotype;
import com.google.common.collect.ImmutableList;
import com.google.common.math.IntMath;

public class BitSetGenotypeEncoder implements GenotypeEncoder<BitSetGenotype, String> {

    private static final int DEFAULT_PRECISION = 6;

    private final int nbits;

    private final int precisionValue;

    private final int lowerBound;
    private final int domainLength;

    private final List<String> identifiers;

    private final double twoPowNBits;


    public BitSetGenotypeEncoder(final Set<String> identifiers, final int lowerBound, final int upperBound) {
        this(identifiers, lowerBound, upperBound, DEFAULT_PRECISION);
    }

    public BitSetGenotypeEncoder(
            final Set<String> identifiers,
            final int lowerBound, final int upperBound,
            final int precision) {

        checkArgument(upperBound > lowerBound);
        precisionValue = (int) Math.pow(10, precision);
        this.lowerBound = lowerBound;
        domainLength = upperBound - lowerBound;
        nbits = IntMath.log2(domainLength * precisionValue, RoundingMode.CEILING);
        this.identifiers = ImmutableList.copyOf(checkNotNull(identifiers));
        twoPowNBits = Math.pow(2, nbits);
    }

    public int getNbits() {
        return nbits;
    }

    @Override
    public Phenotype<String> decode(final BitSetGenotype genotype) {

        final Phenotype<String> phenotype = new BitSetPhenotype();
        final BitSet bits = genotype.value();

        int offset = 0;
        int counter = 1;
        for (final String identifier : identifiers) {
            int bitsValue = 0;
            for (int i = offset; i < nbits * counter; i++) {
                bitsValue += bits.get(i) ? IntMath.pow(2, i - offset) : 0;
            }

            final double value = lowerBound + bitsValue * domainLength / (twoPowNBits - 1);
            phenotype.set(identifier, value);

            offset += nbits;
            counter++;
        }
        return phenotype;
    }

    @Override
    public BitSetGenotype encode(final Phenotype<String> phenotype) {
        final BitSet bits = new BitSet();
        int offset = 0;
        for (final String identifier : identifiers) {

            final double x = phenotype.variable(identifier);
            long bitsValue = Math.round(((x + (-1) * lowerBound) * (twoPowNBits - 1) / domainLength));

            for (int i = 0; bitsValue != 0; i++) {
                if (bitsValue % 2 != 0) {
                    bits.set(i + offset);
                }
                bitsValue = bitsValue >>> 1;
            }

            offset += nbits;
        }

        return new BitSetGenotype(bits, nbits);
    }

}