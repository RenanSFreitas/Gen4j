package com.gen4j.factory;

import java.util.Optional;

import com.gen4j.chromosome.Chromosome;
import com.gen4j.population.generator.ChromosomeGenerator;

public abstract class AbstractGeneticAlgorithmFactory<G extends Chromosome, V, P>
        implements GeneticAlgorithmFactory<G, V, P> {

    @Override
    public Optional<ChromosomeGenerator<G>> chromosomeGenerator() {
        return Optional.empty();
    }
}
