package com.gen4j.factory;

import java.util.Optional;

import com.gen4j.fitness.FitnessCache;
import com.gen4j.fitness.FitnessFunction;
import com.gen4j.genotype.Genotype;
import com.gen4j.population.generator.RandomGenotypeGenerator;

public abstract class AbstractGeneticAlgorithmFactory<G extends Genotype, V, P>
        implements GeneticAlgorithmFactory<G, V, P> {

    private FitnessCache<G> fitnessCache;

    protected abstract FitnessFunction<G> internalFitnessFunction();

    @Override
    public FitnessFunction<G> fitnessFunction() {
        if (fitnessCache == null) {
            fitnessCache = new FitnessCache<>(internalFitnessFunction());
        }
        return fitnessCache;
    }

    @Override
    public Optional<RandomGenotypeGenerator<G>> genotypeGenerator() {
        return Optional.empty();
    }
}
