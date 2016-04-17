package com.gen4j.chromosome.bit;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import java.math.RoundingMode;
import java.util.BitSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.gen4j.chromosome.ChromosomeCoder;
import com.gen4j.phenotype.Phenotype;
import com.gen4j.phenotype.bit.BitSetPhenotype;
import com.google.common.collect.ImmutableList;
import com.google.common.math.DoubleMath;
import com.google.common.math.IntMath;

public class BitChromosomeCoder implements ChromosomeCoder<BitChromosome, String> {

    private final double precisionValue;

    private final double lowerBound;
    private final double domainLength;

    private final List<String> identifiers;

    private final double nbits;
    private final double twoPowNBits;

    private final double chromosomeLength;

    private final Map<BitChromosome, Phenotype<String>> decodeCache = new HashMap<>();
    private final Map<Phenotype<String>, BitChromosome> encodeCache = new HashMap<>();

    public BitChromosomeCoder(
            final Set<String> identifiers,
            final int lowerBound, final int upperBound,
            final int precision) {

        checkArgument(upperBound > lowerBound);
        precisionValue = (int) Math.pow(10, precision);
        this.lowerBound = lowerBound;
        domainLength = upperBound - lowerBound;
        nbits = DoubleMath.log2(domainLength * precisionValue, RoundingMode.CEILING);
        twoPowNBits = Math.pow(2, nbits);
        this.identifiers = ImmutableList.copyOf(checkNotNull(identifiers));
        chromosomeLength = nbits * identifiers.size();
    }

    public int getNbits() {
        return (int) nbits;
    }

    @Override
    public int chromosomeLength() {
        return (int) chromosomeLength;
    }

    @Override
    public Phenotype<String> decode(final BitChromosome chromosome) {

        Phenotype<String> phenotype = null;
        phenotype = decodeCache.get(chromosome);
        if (phenotype != null) {
            return phenotype;
        }
        phenotype = new BitSetPhenotype();
        decodeCache.put(chromosome, phenotype);

        final BitSet bits = chromosome.value();

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
    public BitChromosome encode(final Phenotype<String> phenotype) {
        BitChromosome chromosome = encodeCache.get(phenotype);
        if (chromosome != null) {
            return chromosome;
        }
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
        chromosome = new BitChromosome(bits, (int) nbits);
        encodeCache.put(phenotype, chromosome);
        return chromosome;
    }

}