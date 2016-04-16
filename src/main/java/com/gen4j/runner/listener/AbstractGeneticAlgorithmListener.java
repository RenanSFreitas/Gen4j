/**
 *
 */
package com.gen4j.runner.listener;

import com.gen4j.chromosome.Chromosome;
import com.gen4j.population.Population;
import com.gen4j.runner.GeneticAlgorithmSolution;

public abstract class AbstractGeneticAlgorithmListener implements GeneticAlgorithmListener {

    @Override
    public void newGeneration(final Population<? extends Chromosome> population, final int generationCount) {
        // Override me
    }

    @Override
    public void newSolution(final GeneticAlgorithmSolution<? extends Chromosome> solution) {
        // Override me
    }
}
