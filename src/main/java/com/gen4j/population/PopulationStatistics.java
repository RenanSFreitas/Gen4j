package com.gen4j.population;

import java.math.RoundingMode;
import java.util.List;

import com.gen4j.chromosome.Chromosome;
import com.google.common.math.DoubleMath;

public interface PopulationStatistics {

    static <C extends Chromosome> PopulationStatistics of(final Population<C> population) {

        final List<Individual<C>> fitness = population.fitness();

        double avg = 0d;
        double total = 0d;
        for (final Individual<C> ind : fitness) {
            total += ind.fitness();
        }
        avg = total / fitness.size();
        double std = 0d;
        for (final Individual<C> ind : fitness) {
            final double d = ind.fitness() - avg;
            std += d * d;
        }
        std /= population.size();
        std = Math.sqrt(std);

        final double max = fitness.get(fitness.size() - 1).fitness();
        final double min = fitness.get(0).fitness();
        final double med = fitness.get(DoubleMath.roundToInt(fitness.size() / 2d, RoundingMode.FLOOR)).fitness();
        final int populationSize = population.size();

        return new PopulationStatisticsImpl(populationSize, avg, std, med, total, min, max);
    }

    double totalFitness();

    double medianFitness();

    double meanFitness();

    double maxFitness();

    double minFitness();

    double fitnessStandardDeviation();

    int populationSize();
}
