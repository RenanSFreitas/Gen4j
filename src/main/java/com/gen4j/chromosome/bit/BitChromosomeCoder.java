package com.gen4j.chromosome.bit;

import static com.google.common.base.Preconditions.checkArgument;
import static java.util.Objects.requireNonNull;

import java.math.RoundingMode;
import java.util.BitSet;
import java.util.List;

import com.gen4j.chromosome.ChromosomeCoder;
import com.gen4j.chromosome.Range;
import com.gen4j.phenotype.Phenotype;
import com.gen4j.phenotype.StandardPhenotype;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.math.DoubleMath;
import com.google.common.math.IntMath;

/**
 * {@link ChromosomeCoder} for {@link BitChromosome}s.
 */
public class BitChromosomeCoder implements ChromosomeCoder<BitChromosome> {

    private final double precisionValue;

    private final List<String> identifiers;

    private final double nbits;
    private final double twoPowNBits;

    private final double chromosomeLength;

    private final Range range;

    public BitChromosomeCoder(
            final List<String> variables,
            final Range range,
            final int precision) {

        checkArgument(variables != null);
        checkArgument(ImmutableSet.copyOf(variables).size() == variables.size());

        precisionValue = (int) Math.pow(10, precision);
        this.range = requireNonNull(range);
        nbits = DoubleMath.log2(range.length() * precisionValue, RoundingMode.CEILING);
        twoPowNBits = Math.pow(2, nbits);
        identifiers = ImmutableList.copyOf(variables);
        chromosomeLength = nbits * variables.size();
    }

    @Override
    public int chromosomeLength() {
        return (int) chromosomeLength;
    }

    @Override
    public Phenotype decode(final BitChromosome chromosome) {

        final Phenotype phenotype = new StandardPhenotype();
        final BitSet bits = chromosome.value();
        int offset = 0;
        int counter = 1;
        for (final String identifier : identifiers) {
            int bitsValue = 0;
            for (int i = offset; i < nbits * counter; i++) {
                bitsValue += bits.get(i) ? IntMath.pow(2, i - offset) : 0;
            }

            final double value = range.lowerBound() + bitsValue * range.length() / (twoPowNBits - 1);
            phenotype.set(identifier, value);

            offset += nbits;
            counter++;
        }
        return phenotype;
    }

    @Override
    public BitChromosome encode(final Phenotype phenotype) {
        final BitSet bits = new BitSet();
        int offset = 0;
        for (final String identifier : identifiers) {

            final double x = phenotype.variable(identifier);
            long bitsValue = Math.round(((x + (-1) * range.lowerBound()) * (twoPowNBits - 1) / range.length()));

            for (int i = 0; bitsValue != 0; i++) {
                if (bitsValue % 2 != 0) {
                    bits.set(i + offset);
                }
                bitsValue = bitsValue >>> 1;
            }

            offset += nbits;
        }
        return new BitChromosome(bits, (int) chromosomeLength);
    }

    @Override
    public Range range() {
        return range;
    }
}