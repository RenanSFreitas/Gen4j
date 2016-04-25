package com.gen4j.population;

import java.util.List;

import com.gen4j.chromosome.Chromosome;

public interface PopulationStatistics {

    static <C extends Chromosome> PopulationStatistics of(final Population<C> population) {

        final List<Individual<C>> fitness = population.fitness();
        double avg = 0d;
        double total = 0d;
        for (final Individual<C> val : fitness) {
            total += val.fitness();
        }
        avg = total / fitness.size();
        double std = 0d;
        for (final Individual<C> val : fitness) {
            final double d = val.fitness() - avg;
            std += d * d;
        }
        std /= population.size();
        std = Math.sqrt(std);

        final double max = fitness.get(fitness.size() - 1).fitness();
        final double min = fitness.get(0).fitness();

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
