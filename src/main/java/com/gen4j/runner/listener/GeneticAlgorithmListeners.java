/**
 *
 */
package com.gen4j.runner.listener;

import com.gen4j.chromosome.Chromosome;
import com.gen4j.population.Individual;
import com.gen4j.population.Population;

public final class GeneticAlgorithmListeners {

    public GeneticAlgorithmListeners() {
        throw new AssertionError("No instances for utility classes.");
    }

    private static void printFitness(final int generation, final double fitness, final int precision) {
        System.out.printf("generation = %d, fitness = %." + precision + "f\n",
                Integer.valueOf(generation),
                Double.valueOf(fitness));
    }

    public static GeneticAlgorithmListener printBestFitness(final int regularity, final int precision) {
        return new AbstractGeneticAlgorithmListener() {
            @Override
            public void newGeneration(final Population<? extends Chromosome> population, final int generationCount) {

                if (generationCount % regularity != 0) {
                    return;
                }

                printFitness(generationCount, population.fittest().fitness(), precision);
            }
        };
    }

    public static GeneticAlgorithmListener printAverageFitness(final int regularity, final int precision) {
        return new AbstractGeneticAlgorithmListener() {
            @Override
            public void newGeneration(final Population<? extends Chromosome> population, final int generationCount) {

                if (generationCount % regularity != 0) {
                    return;
                }

                double fitness = 0d;
                for (final Individual<? extends Chromosome> individual : population) {
                    fitness += individual.fitness();
                }
                fitness /= population.size();

                printFitness(generationCount, fitness, precision);
            }
        };
    }
}
