package com.gen4j.population;

import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.joining;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.gen4j.chromosome.Chromosome;
import com.gen4j.factory.GeneticAlgorithmFactory;

public final class PopulationBuilder<C extends Chromosome>
{
    private int size;

    private final GeneticAlgorithmFactory<C> factory;
    private Collection<Individual<C>> initialChromosomes = Collections.emptyList();

    private int chromosomeLength;

    private PopulationBuilder(final GeneticAlgorithmFactory<C> factory)
    {
        this.factory = requireNonNull(factory);
    }

    public static <G extends Chromosome> PopulationBuilder<G> of(final GeneticAlgorithmFactory<G> factory) {
        return new PopulationBuilder<>(factory);
    }

    public PopulationBuilder<C> size(final int size)
    {
        this.size = size;
        return this;
    }

    public PopulationBuilder<C> initialChromosomes(final Individual<C>[] chromosomes) {
        initialChromosomes = Arrays.asList(chromosomes);
        return this;
    }

    public PopulationBuilder<C> initialChromosomes(final Collection<Individual<C>> chromosomes) {
        initialChromosomes = chromosomes;
        return this;
    }

    public Population<C> build()
    {
        checkState();
        final Population<C> population = newPopulationInstance();
        population.addAll(initialChromosomes);
        chromosomeLength = factory.coder().chromosomeLength();
        for (int i = population.size(); i < size; i++)
        {
            population.add(factory.individual(newGenotype()));
        }
        return ImmutablePopulation.of(population);
    }

    private C newGenotype()
    {
        return factory.chromosomeGenerator().get().generate(chromosomeLength);
    }

    private Population<C> newPopulationInstance()
    {
        return factory.populationInstantiator().instantiate();
    }

    private void checkState()
    {
        final List<String> errors = new ArrayList<>();
        checkState(errors);
        if (!errors.isEmpty())
        {
            throw new IllegalStateException(errors.stream().collect(joining("\n")));
        }
    }

    private void checkState(final List<String> errorMessages)
    {
        check(size > 0, "Population size should be greater than zero.", errorMessages);
        check(factory.coder().chromosomeLength() > 0, "Chromosome length should be greater than zero.", errorMessages);

        checkInitialChromosomesAndGenotypeGenerator(errorMessages);
    }

    private void checkInitialChromosomesAndGenotypeGenerator(final List<String> errorMessages) {

        if (factory.chromosomeGenerator().isPresent()) {
            return;
        }

        if (initialChromosomes.size() < size) {
            errorMessages.add(
                    "Genotype generator must be provided when initial chromosomes cardinality is less than population size");
        }
    }

    private void check(final boolean expression, final String errorMessage, final List<String> errorMessages)
    {
        if (!expression)
        {
            errorMessages.add(errorMessage);
        }
    }

}
