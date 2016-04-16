/**
 *
 */
package com.gen4j.runner;

import com.gen4j.chromosome.Chromosome;
import com.gen4j.population.Population;

/**
 *
 */
public final class GeneticAlgorithmSolution<C extends Chromosome> {

    private final Population<C> population;
    private final int generationsCount;

    public GeneticAlgorithmSolution(final Population<C> population, final int generationsCount) {
        this.population = population;
        this.generationsCount = generationsCount;
    }

    /**
     * @return the generationsCount
     */
    public int generationsCount() {
        return generationsCount;
    }

    /**
     * @return the population
     */
    public Population<C> population() {
        return population;
    }
}
