package com.gen4j.population;

import com.gen4j.chromosome.Chromosome;
import com.google.common.math.DoubleMath;

@FunctionalInterface
public interface Criteria<G extends Chromosome> {

    static <C extends Chromosome> Criteria<C> fitnessEquals(final double target, final double tolerance) {
        return (p, g) -> DoubleMath.fuzzyEquals(p.fittest().fitness(), target, tolerance);
    }

    static <C extends Chromosome> Criteria<C> maxGenerations(final int max) {
        return (p, g) -> max - 1 < g;
    }

    static <C extends Chromosome> Criteria<C> noFittestChanges(final int generations, final double minimumImprovement) {
        return new Criteria<C>() {
            private int lastFitnessGeneration = 0;
            private double lastFitness = Integer.MIN_VALUE;

            @Override
            public boolean apply(final Population<C> population, final int generation) {
                if (generation - lastFitnessGeneration < generations) {
                    // continue
                    return false;
                }

                final double currentFitness = population.fittest().fitness();
                final boolean fitnessImproved = DoubleMath.fuzzyCompare(lastFitness, currentFitness,
                        minimumImprovement) < 0;
                if (fitnessImproved) {
                    lastFitness = currentFitness;
                    lastFitnessGeneration = generation;
                }
                return !fitnessImproved;
            }
        };
    }

    boolean apply(Population<G> population, int generation);
}
