package com.gen4j.chromosome.bit;

import static com.google.common.base.Preconditions.checkArgument;

import java.math.RoundingMode;
import java.util.Arrays;
import java.util.BitSet;
import java.util.List;

import com.gen4j.chromosome.ChromosomeCoder;
import com.gen4j.chromosome.Range;
import com.gen4j.phenotype.Phenotype;
import com.gen4j.phenotype.StandardPhenotype;
import com.gen4j.utils.Lists;
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

    private final double[] nbits;
    private final double[] twoPowNBits;

    private final double chromosomeLength;

    private final List<Range> ranges;

    public BitChromosomeCoder(
            final List<String> variables,
            final Range range,
            final int precision) {
        this(variables, Lists.repeat(range, variables.size()), precision);
    }

    public BitChromosomeCoder(final List<String> variables, final List<Range> ranges, final int precision) {

        checkArgument(variables != null);
        checkArgument(ImmutableSet.copyOf(variables).size() == variables.size());

        precisionValue = (int) Math.pow(10, precision);
        this.ranges = ImmutableList.copyOf(ranges);
        nbits = ranges.stream().mapToDouble(r -> DoubleMath.log2(r.length() * precisionValue, RoundingMode.CEILING))
                .toArray();
        twoPowNBits = Arrays.stream(nbits).map(n -> Math.pow(2, n)).toArray();
        identifiers = ImmutableList.copyOf(variables);
        chromosomeLength = Arrays.stream(nbits).sum();
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
        for (int index = 0; index < identifiers.size(); index++) {
            final String identifier = identifiers.get(index);
            int bitsValue = 0;
            for (int i = offset; i < nbits[index] * counter; i++) {
                bitsValue += bits.get(i) ? IntMath.pow(2, i - offset) : 0;
            }

            final Range range = ranges.get(index);
            final double value = range.lowerBound() + bitsValue * range.length() / (twoPowNBits[index] - 1);
            phenotype.set(identifier, value);

            offset += nbits[index];
            counter++;
        }
        return phenotype;
    }

    @Override
    public BitChromosome encode(final Phenotype phenotype) {
        final BitSet bits = new BitSet();
        int offset = 0;
        for (int index = 0; index < identifiers.size(); index++) {
            final String identifier = identifiers.get(index);

            final double x = phenotype.variable(identifier);
            final Range range = ranges.get(index);
            long bitsValue = Math.round(((x + (-1) * range.lowerBound()) * (twoPowNBits[index] - 1) / range.length()));

            for (int i = 0; bitsValue != 0; i++) {
                if (bitsValue % 2 != 0) {
                    bits.set(i + offset);
                }
                bitsValue = bitsValue >>> 1;
            }

            offset += nbits[index];
        }
        return new BitChromosome(bits, (int) chromosomeLength);
    }

    @Override
    public Range range(final int geneIndex) {
        return ranges.get(geneIndex);
    }

    @Override
    public List<Range> ranges() {
        return ranges;
    }
}