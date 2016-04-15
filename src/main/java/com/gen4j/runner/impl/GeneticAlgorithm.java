package com.gen4j.runner.impl;

import static com.google.common.base.Preconditions.checkState;
import static com.google.common.collect.Collections2.transform;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import com.gen4j.chromosome.Chromosome;
import com.gen4j.factory.GeneticAlgorithmFactory;
import com.gen4j.fitness.FitnessFunction;
import com.gen4j.operator.GeneticOperator;
import com.gen4j.operator.selection.Selector;
import com.gen4j.population.ImmutablePopulation;
import com.gen4j.population.Individual;
import com.gen4j.population.Population;
import com.gen4j.population.PopulationBuilder;
import com.gen4j.population.generic.GenericIndividual;
import com.gen4j.utils.Pair;

//TODO: tests for invalid stuff
//TODO: refactor the operators stuff
//TODO: stuff
public class GeneticAlgorithm<G extends Chromosome, V, P> implements com.gen4j.runner.GeneticAlgorithm<G, V, P> {

    private final List<Pair<GeneticOperator<G>, Selector<G>>> operators = new ArrayList<>();
    private final Random random = new Random(System.nanoTime());

    @Override
    public void addGeneticOperator(final GeneticOperator<G> operator, final Selector<G> selector) {
        operators.add(Pair.of(operator, selector));
    }

    @Override
    public Population<G> execute(final Population<G> population, final GeneticAlgorithmFactory<G, V, P> factory) {

        checkState(!operators.isEmpty());

        Population<G> currentPopulation = population;

        int generation = 0;
        while (!factory.stopCriteria().apply(currentPopulation, generation)) {

            currentPopulation = applyGeneticOperators(currentPopulation, factory);
            generation++;
        }

        return ImmutablePopulation.of(currentPopulation);
    }

    private Population<G> applyGeneticOperators(final Population<G> population,
            final GeneticAlgorithmFactory<G, V, P> factory) {

        prepareSelectors(population);

        final Collection<Individual<G>> generated = new ArrayList<>();

        while(generated.size() < population.size() )
        {
            addGeneratedChromosomes(population, generated, factory.fitnessFunction());
        }

        final Population<G> newPopulation = PopulationBuilder.of(factory)
                .initialChromosomes(generated)
                .size(population.size())
                .build();

        return newPopulation;
    }

    private void prepareSelectors(final Population<G> population) {
        for (final Pair<GeneticOperator<G>, Selector<G>> pair : operators) {
            pair.second().population(population);
        }
    }

    private void addGeneratedChromosomes(final Population<G> population, final Collection<Individual<G>> generated,
            final FitnessFunction<G> fitnessFunction) {

        for (final Pair<GeneticOperator<G>, Selector<G>> pair : operators) {
            final GeneticOperator<G> operator = pair.first();
            final Selector<G> selector = pair.second();
            if (shouldExecute(operator)) {
                // Select and collect generic material
                final Collection<G> selected = transform(
                        selector.select(operator.chromosomeCount()),
                        c -> c.chromosome());

                generated.addAll(transform(operator.apply(selected), g -> new GenericIndividual<>(g, fitnessFunction)));
            }
        }
    }

    private boolean shouldExecute(final GeneticOperator<G> operator) {
        return random.nextInt(100) / 100 < operator.probability();
    }

}
