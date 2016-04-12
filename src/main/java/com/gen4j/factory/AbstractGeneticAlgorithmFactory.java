package com.gen4j.factory;

import java.util.Optional;

import com.gen4j.genotype.Genotype;
import com.gen4j.population.generator.RandomGenotypeGenerator;

public abstract class AbstractGeneticAlgorithmFactory<G extends Genotype, V, P>
        implements GeneticAlgorithmFactory<G, V, P> {

    @Override
    public Optional<RandomGenotypeGenerator<G>> genotypeGenerator() {
        return Optional.empty();
    }
}
