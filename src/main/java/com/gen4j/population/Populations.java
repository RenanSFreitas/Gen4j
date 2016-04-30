package com.gen4j.population;

import com.gen4j.chromosome.Chromosome;
import com.google.common.math.DoubleMath;

public final class Populations {

    private Populations() {
        throw new UnsupportedOperationException();
    }

    private static final double TOLERANCE = 0.000001;

    public static <C extends Chromosome> boolean converged(final Population<C> population) {
        final PopulationStatistics statistics = population.statistics();
        return DoubleMath.fuzzyEquals(statistics.meanFitness(), statistics.maxFitness(), TOLERANCE);
    }
}
