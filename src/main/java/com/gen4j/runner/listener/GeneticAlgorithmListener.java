package com.gen4j.runner.listener;

import com.gen4j.chromosome.Chromosome;
import com.gen4j.population.Population;

public interface GeneticAlgorithmListener {

    void newGeneration(Population<? extends Chromosome> population, int generationCount);
}
