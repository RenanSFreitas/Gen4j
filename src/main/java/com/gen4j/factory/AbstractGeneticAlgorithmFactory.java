package com.gen4j.factory;

import java.util.Optional;

import com.gen4j.chromosome.Chromosome;
import com.gen4j.population.Individual;
import com.gen4j.population.generator.ChromosomeGenerator;
import com.gen4j.population.generic.GenericIndividual;

public abstract class AbstractGeneticAlgorithmFactory<C extends Chromosome>
        implements GeneticAlgorithmFactory<C> {

    @Override
    public Optional<ChromosomeGenerator<C>> chromosomeGenerator() {
        return Optional.empty();
    }

    @Override
    public Individual<C> individual(final C chromosome) {
        return new GenericIndividual<>(chromosome, fitnessFunction(), coder());
    }
}
