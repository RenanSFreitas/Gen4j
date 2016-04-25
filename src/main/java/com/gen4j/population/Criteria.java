package com.gen4j.population;

import com.gen4j.chromosome.Chromosome;
import com.google.common.math.DoubleMath;

@FunctionalInterface
public interface Criteria<C extends Chromosome> {

    static <C extends Chromosome> Criteria<C> fitnessEquals(final double target, final double tolerance) {
        return new Criteria<C>() {
            @Override
            public boolean apply(final Population<C> p, final int g, final Individual<C> fittest) {
                return DoubleMath.fuzzyEquals(fittest.fitness(), target, tolerance);
            }

            @Override
            public String toString() {
                return String.format("Fitness equals to %f with tolerance %f", target, tolerance);
            }
        };
    }

    static <C extends Chromosome> Criteria<C> maxGenerations(final int max) {
        // return (p, g) -> max - 1 < g;
        return new Criteria<C>() {
            @Override
            public boolean apply(final Population<C> p, final int g, final Individual<C> fittest) {
                return max - 1 < g;
            }

            @Override
            public String toString() {
                return String.format("Reach %d generations", max);
            }
        };
    }

    static <C extends Chromosome> Criteria<C> noFittestChanges(final int generations, final double minimumImprovement) {
        return new Criteria<C>() {
            private int lastFitnessGeneration = 0;
            private double lastFitness = Integer.MIN_VALUE;

            @Override
            public boolean apply(final Population<C> population, final int generation, final Individual<C> fittest) {
                if (generation - lastFitnessGeneration < generations) {
                    // continue
                    return false;
                }

                final double currentFitness = fittest.fitness();
                final boolean fitnessImproved = DoubleMath.fuzzyCompare(lastFitness, currentFitness,
                        minimumImprovement) < 0;
                if (fitnessImproved) {
                    lastFitness = currentFitness;
                    lastFitnessGeneration = generation;
                }
                return !fitnessImproved;
            }

            @Override
            public String toString() {
                return String.format("Fittest improvements below minimum of %f for %d generations",
                        minimumImprovement, generations);
            }
        };
    }

    boolean apply(Population<C> current, int generation, Individual<C> fittest);
}
