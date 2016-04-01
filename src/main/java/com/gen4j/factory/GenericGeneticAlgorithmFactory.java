package com.gen4j.factory;

import com.gen4j.genotype.Genotype;
import com.gen4j.population.PopulationInstantiator;
import com.gen4j.population.generic.GenericPopulation;

public abstract class GenericGeneticAlgorithmFactory<G extends Genotype, K>
        extends AbstractGeneticAlgorithmFactory<G, K, Integer> {

    @Override
    public PopulationInstantiator<Integer, G> populationInstantiator() {
        return GenericPopulation.intantiator();
    }

}
