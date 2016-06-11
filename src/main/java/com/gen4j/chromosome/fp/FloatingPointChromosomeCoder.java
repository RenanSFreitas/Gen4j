package com.gen4j.chromosome.fp;

import java.util.List;

import com.gen4j.chromosome.ChromosomeCoder;
import com.gen4j.chromosome.Range;
import com.gen4j.phenotype.Phenotype;
import com.gen4j.phenotype.StandardPhenotype;
import com.gen4j.utils.Lists;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;

/**
 * A {@link ChromosomeCoder coder} for real valued chromosomes.
 */
public class FloatingPointChromosomeCoder implements ChromosomeCoder<FloatingPointChromosome> {

    private final List<String> identifiers;
    private final List<Range> ranges;

    public FloatingPointChromosomeCoder(final List<String> identifiers, final Range range) {
        this(identifiers, Lists.repeat(range, identifiers.size()));
    }

    public FloatingPointChromosomeCoder(final List<String> identifiers, final List<Range> ranges) {
        Preconditions.checkArgument(identifiers != null && !identifiers.isEmpty());
        this.identifiers = identifiers;
        this.ranges = ImmutableList.copyOf(ranges);
    }

    @Override
    public Phenotype decode(final FloatingPointChromosome chromosome) {
        final Phenotype phenotype = new StandardPhenotype();
        final double[] chromosomeValue = chromosome.value();
        for (int i = 0; i < chromosomeValue.length; i++) {
            final double val = chromosomeValue[i];
            phenotype.set(identifiers.get(i), val);
        }
        return phenotype;
    }

    @Override
    public FloatingPointChromosome encode(final Phenotype phenotype) {
        final double[] chromosomeValue = new double[identifiers.size()];
        for (int i = 0; i < chromosomeValue.length; i++) {
            chromosomeValue[i] = phenotype.variable(identifiers.get(i));
        }
        return new FloatingPointChromosome(chromosomeValue);
    }

    @Override
    public int chromosomeLength() {
        return identifiers.size();
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
