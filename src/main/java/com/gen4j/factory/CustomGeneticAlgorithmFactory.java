/**
 *
 */
package com.gen4j.factory;

import static java.util.Objects.requireNonNull;

import com.gen4j.chromosome.Chromosome;
import com.gen4j.chromosome.ChromosomeCoder;
import com.gen4j.fitness.FitnessFunction;
import com.gen4j.population.Criteria;

public class CustomGeneticAlgorithmFactory<C extends Chromosome> extends GenericGeneticAlgorithmFactory<C> {

    private final ChromosomeCoder<C> coder;
    private final FitnessFunction fitnessFunction;
    private final Criteria<C> criteria;

    protected CustomGeneticAlgorithmFactory(final ChromosomeCoder<C> coder,
            final FitnessFunction fitnessFunction,
            final Criteria<C> criteria) {

        this.coder = requireNonNull(coder);
        this.fitnessFunction = requireNonNull(fitnessFunction);
        this.criteria = requireNonNull(criteria);
    }

    @Override
    public final ChromosomeCoder<C> coder() {
        return coder;
    }

    @Override
    public final FitnessFunction fitnessFunction() {
        return fitnessFunction;
    }

    @Override
    public final Criteria<C> stopCriteria() {
        return criteria;
    }
}
