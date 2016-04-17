/**
 *
 */
package com.gen4j.runner;

import com.gen4j.chromosome.Chromosome;
import com.gen4j.population.Individual;
import com.gen4j.population.Population;

/**
 *
 */
public final class GeneticAlgorithmSolution<C extends Chromosome> {

    private final Population<C> population;
    private final int generationsCount;
    private final Individual<C> fittest;

    public GeneticAlgorithmSolution(final Population<C> population, final int generationsCount,
            final Individual<C> fittest) {
        this.population = population;
        this.generationsCount = generationsCount;
        this.fittest = fittest;
    }

    /**
     * @return the generations count
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

    /**
     * @return
     */
    public Individual<C> fittest() {
        return fittest;
    }
}
