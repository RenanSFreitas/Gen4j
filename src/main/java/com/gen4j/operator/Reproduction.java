package com.gen4j.operator;

import static com.google.common.base.Preconditions.checkArgument;

import java.util.Collection;

import com.gen4j.genotype.Genotype;
import com.google.common.base.Preconditions;

public final class Reproduction<G extends Genotype> implements GeneticOperator<G> {

    private static final int CHROMOSOME_COUNT = 1;
    private double probability = 1d;

    @Override
    public double probability() {
        return probability;
    }

    @Override
    public void probability(final double probability) {
        Preconditions.checkArgument(probability > 0d && probability <= 1d);
        this.probability = probability;
    }

    @Override
    public Collection<G> apply(final Collection<G> genotypes) {
        checkArgument(genotypes.size() == CHROMOSOME_COUNT);
        return genotypes;
    }

    @Override
    public int chromosomeCount() {
        return CHROMOSOME_COUNT;
    }

}
