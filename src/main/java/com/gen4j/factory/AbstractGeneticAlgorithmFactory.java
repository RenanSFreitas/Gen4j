package com.gen4j.factory;

import java.util.Optional;

import com.gen4j.chromosome.Chromosome;
import com.gen4j.population.Individual;
import com.gen4j.population.PopulationInstantiator;
import com.gen4j.population.generator.ChromosomeGenerator;
import com.gen4j.population.generic.GenericIndividual;
import com.gen4j.population.generic.GenericPopulation;

/**
 * Base class for factories.
 *
 * @param <C>
 *            the specialization of the {@link Chromosome}
 */
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

    @Override
    public PopulationInstantiator<C> populationInstantiator() {
        return GenericPopulation.intantiator();
    }
}
