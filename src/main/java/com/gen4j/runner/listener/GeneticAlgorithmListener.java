package com.gen4j.runner.listener;

import com.gen4j.chromosome.Chromosome;
import com.gen4j.population.Individual;
import com.gen4j.population.Population;
import com.gen4j.runner.GeneticAlgorithmSolution;

public interface GeneticAlgorithmListener<C extends Chromosome> {

    void newGeneration(Population<C> population, int generationCount, Individual<C> fittest);

    void newSolution(GeneticAlgorithmSolution<C> solution, Individual<C> fittest);

}
