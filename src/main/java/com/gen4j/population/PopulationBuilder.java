package com.gen4j.population;

import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.joining;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import com.gen4j.chromosome.Chromosome;
import com.gen4j.factory.GeneticAlgorithmFactory;
import com.gen4j.population.generic.GenericIndividual;

public final class PopulationBuilder<G extends Chromosome, V, P>
{
    private int size;

    private final GeneticAlgorithmFactory<G, V, P> factory;
    private Optional<P> populationInstantiatorParameter = Optional.empty();
    private Collection<Individual<G>> initialChromosomes = Collections.emptyList();

    private int chromosomeLength;

    private PopulationBuilder(final GeneticAlgorithmFactory<G, V, P> factory)
    {
        this.factory = requireNonNull(factory);
    }

    public static <G extends Chromosome, V, P> PopulationBuilder<G, V, P> of(
            final GeneticAlgorithmFactory<G, V, P> factory) {
        return new PopulationBuilder<>(factory);
    }

    public PopulationBuilder<G, V, P> constructorParameter(final P parameter)
    {
        populationInstantiatorParameter = Optional.of(parameter);
        return this;
    }

    public PopulationBuilder<G, V, P> size(final int size)
    {
        this.size = size;
        return this;
    }

    public PopulationBuilder<G, V, P> initialChromosomes(final Collection<Individual<G>> chromosomes) {
        initialChromosomes = chromosomes;
        return this;
    }

    public Population<G> build()
    {
        checkState();
        final Population<G> population = newPopulationInstance();
        population.addAll(initialChromosomes);
        chromosomeLength = factory.coder().chromosomeLength();
        for (int i = population.size(); i < size; i++)
        {
            final boolean added = population.add(new GenericIndividual<>(newGenotype(), factory.fitnessFunction()));

            if (!added) {
                i--;
            }
        }
        return population;
    }

    private G newGenotype()
    {
        return factory.chromosomeGenerator().get().generate(chromosomeLength);
    }

    private Population<G> newPopulationInstance()
    {
        return factory.populationInstantiator().instantiate(populationInstantiatorParameter);
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
