package com.gen4j.generation.replacement;

import com.gen4j.chromosome.Chromosome;
import com.gen4j.factory.GeneticAlgorithmFactory;
import com.gen4j.population.Population;

public interface GenerationReplacer<C extends Chromosome> {

    Population<C> replace(Population<C> generation, Population<C> nextGeneration, GeneticAlgorithmFactory<C> factory);
}
