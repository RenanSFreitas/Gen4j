package com.gen4j.population;

import com.gen4j.chromosome.Chromosome;
import com.google.common.math.DoubleMath;

public abstract class AbstractCriteria<C extends Chromosome> implements Criteria<C> {

    private static final double TOLERANCE = 0.000001;
    private boolean populationConverged;

    @Override
    public final boolean apply(final Population<C> population, final int generation) {

        final PopulationStatistics statistics = population.statistics();

        populationConverged = DoubleMath.fuzzyEquals(statistics.meanFitness(), statistics.maxFitness(), TOLERANCE);

        if (populationConverged) {
            return true;
        }

        return doApply(population, generation);
    }

    @Override
    public final boolean getPopulationConverged() {
        return populationConverged;
    }

    protected abstract boolean doApply(Population<C> population, int generation);
}
