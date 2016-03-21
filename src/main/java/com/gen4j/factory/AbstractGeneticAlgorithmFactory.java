package com.gen4j.factory;

import com.gen4j.fitness.FitnessCache;
import com.gen4j.fitness.FitnessFunction;
import com.gen4j.genotype.Genotype;

public abstract class AbstractGeneticAlgorithmFactory<G extends Genotype, K> implements GeneticAlgorithmFactory<G, K> {

    private FitnessCache<G> fitnessCache;

    protected abstract FitnessFunction<G> internalFitnessFunction();

    @Override
    public FitnessFunction<G> fitnessFunction() {
        if (fitnessCache == null) {
            fitnessCache = new FitnessCache<>(internalFitnessFunction());
        }
        return fitnessCache;
    }

}
