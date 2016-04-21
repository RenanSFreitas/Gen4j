package com.gen4j.operator;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.collect.Iterables.getOnlyElement;
import static java.util.Collections.singletonList;

import java.util.Collection;
import java.util.List;

import com.gen4j.chromosome.Chromosome;
import com.gen4j.factory.GeneticAlgorithmFactory;
import com.gen4j.population.Individual;
import com.google.common.base.Preconditions;

public final class Reproduction<C extends Chromosome> implements GeneticOperator<C> {

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
    public List<Individual<C>> apply(final Collection<Individual<C>> individuals,
            final GeneticAlgorithmFactory<C> factory) {
        checkArgument(individuals.size() == CHROMOSOME_COUNT);
        return singletonList(getOnlyElement(individuals));
    }

    @Override
    public int chromosomeCount() {
        return CHROMOSOME_COUNT;
    }

}
