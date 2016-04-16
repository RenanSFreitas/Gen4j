package com.gen4j.runner.listener;

import com.gen4j.chromosome.Chromosome;
import com.gen4j.population.Population;
import com.gen4j.runner.GeneticAlgorithmSolution;

public interface GeneticAlgorithmListener {

    void newGeneration(Population<? extends Chromosome> population, int generationCount);

    void newSolution(GeneticAlgorithmSolution<? extends Chromosome> solution);

}
