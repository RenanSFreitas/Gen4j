package com.gen4j.factory;

import com.gen4j.chromosome.Chromosome;
import com.gen4j.population.PopulationInstantiator;
import com.gen4j.population.generic.GenericPopulation;

public abstract class GenericGeneticAlgorithmFactory<C extends Chromosome> extends AbstractGeneticAlgorithmFactory<C> {

    @Override
    public PopulationInstantiator<C> populationInstantiator() {
        return GenericPopulation.intantiator();
    }

}
