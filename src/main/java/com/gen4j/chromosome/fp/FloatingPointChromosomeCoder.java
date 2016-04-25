package com.gen4j.chromosome.fp;

import static java.util.Objects.requireNonNull;

import java.util.List;

import com.gen4j.chromosome.ChromosomeCoder;
import com.gen4j.chromosome.Range;
import com.gen4j.phenotype.Phenotype;
import com.gen4j.phenotype.StandardPhenotype;
import com.google.common.base.Preconditions;

public class FloatingPointChromosomeCoder implements ChromosomeCoder<FloatingPointChromosome> {

    private final List<String> identifiers;
    private final Range range;
    private final double scale;

    public FloatingPointChromosomeCoder(final List<String> identifiers, final Range range, final double scale) {
        Preconditions.checkArgument(identifiers != null && !identifiers.isEmpty());
        this.identifiers = identifiers;
        this.range = requireNonNull(range);
        this.scale = scale;
    }
    @Override
    public Phenotype decode(final FloatingPointChromosome chromosome) {
        final Phenotype phenotype = new StandardPhenotype();
        final double[] chromosomeValue = chromosome.value();
        for (int i = 0; i < chromosomeValue.length; i++) {
            final double val = chromosomeValue[i];
            phenotype.set(identifiers.get(i), val / scale);
        }
        return phenotype;
    }

    @Override
    public FloatingPointChromosome encode(final Phenotype phenotype) {
        final double[] chromosomeValue = new double[identifiers.size()];
        for (int i = 0; i < chromosomeValue.length; i++) {
            chromosomeValue[i] = scale * phenotype.variable(identifiers.get(i));
        }
        return new FloatingPointChromosome(chromosomeValue, range);
    }

    @Override
    public int chromosomeLength() {
        return identifiers.size();
    }

    @Override
    public Range range() {
        return range;
    }
}
