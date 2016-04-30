package com.gen4j.population;

import com.gen4j.chromosome.Chromosome;

public abstract class AbstractCriteria<C extends Chromosome> implements Criteria<C> {

    @Override
    public final boolean apply(final Population<C> population, final int generation) {

        if (Populations.converged(population)) {
            return true;
        }

        return doApply(population, generation);
    }

    protected abstract boolean doApply(Population<C> population, int generation);
}
