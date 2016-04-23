package com.gen4j.population;

import java.util.NavigableMap;

import com.gen4j.chromosome.Chromosome;

public interface PopulationStatistics {

    static <C extends Chromosome> PopulationStatistics of(final Population<C> population) {

        final NavigableMap<Individual<C>, Double> fitness = population.fitness();
        double avg = 0d;
        double total = 0d;
        for (final Double val : fitness.values()) {
            total += val.doubleValue();
        }
        avg = total / fitness.size();
        double std = 0d;
        for (final Double val : fitness.values()) {
            final double d = val.doubleValue() - avg;
            std += d * d;
        }
        std /= population.size();
        std = Math.sqrt(std);

        final double max = fitness.lastKey().fitness();
        final double min = fitness.firstKey().fitness();

        final int populationSize = population.size();

        return new PopulationStatisticsImpl(populationSize, avg, std, total, min, max);
    }

    double totalFitness();

    double meanFitness();

    double maxFitness();

    double minFitness();

    double fitnessStandardDeviation();

    int populationSize();
}
