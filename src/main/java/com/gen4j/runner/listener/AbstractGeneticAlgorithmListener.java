/**
 *
 */
package com.gen4j.runner.listener;

import com.gen4j.chromosome.Chromosome;
import com.gen4j.population.Individual;
import com.gen4j.population.Population;
import com.gen4j.runner.GeneticAlgorithmSolution;

public abstract class AbstractGeneticAlgorithmListener<C extends Chromosome> implements GeneticAlgorithmListener<C> {

    @Override
    public void newGeneration(final Population<C> population, final int generationCount, final Individual<C> fittest) {
        // Override me
    }

    @Override
    public void newSolution(final GeneticAlgorithmSolution<C> solution, final Individual<C> fittest) {
        // Override me
    }

    @Override
    public void newFittest(final Individual<C> newFittest, final Individual<C> oldFittest) {
        // Override me
    }
}
