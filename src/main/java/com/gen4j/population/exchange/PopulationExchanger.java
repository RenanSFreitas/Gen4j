package com.gen4j.population.exchange;

import com.gen4j.chromosome.Chromosome;
import com.gen4j.factory.GeneticAlgorithmFactory;
import com.gen4j.population.Population;

public interface PopulationExchanger<C extends Chromosome> {

    Population<C> exchange(Population<C> parent, Population<C> offspring,
            GeneticAlgorithmFactory<C> factory);
}
