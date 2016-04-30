package com.gen4j.operator;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.collect.Iterables.getOnlyElement;
import static java.util.Collections.singletonList;

import java.util.Collection;
import java.util.List;

import com.gen4j.chromosome.Chromosome;
import com.gen4j.chromosome.code.ChromosomeCodeType;
import com.gen4j.factory.GeneticAlgorithmFactory;
import com.gen4j.population.Individual;

public final class Reproduction<C extends Chromosome> extends AbstractGeneticOperator<C> {

    private static final int CHROMOSOME_COUNT = 1;

    public Reproduction() {
        super(1, CHROMOSOME_COUNT, ChromosomeCodeType.FLOATING_POINT);
    }

    @Override
    public List<Individual<C>> apply(final Collection<Individual<C>> individuals,
            final GeneticAlgorithmFactory<C> factory, int generationCount) {
        checkArgument(individuals.size() == CHROMOSOME_COUNT);
        return singletonList(getOnlyElement(individuals));
    }

    @Override
    public String toString() {
        return "Chromosome reproduction";
    }
}
