package com.gen4j.runner.impl;

import static com.google.common.base.Preconditions.checkState;
import static com.google.common.collect.Collections2.transform;
import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;

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
import com.gen4j.runner.GeneticAlgorithmSolution;
import com.gen4j.runner.listener.GeneticAlgorithmListener;

//TODO: tests for invalid stuff
//TODO: refactor the operators stuff
//TODO: stuff
public class GeneticAlgorithm<C extends Chromosome, V, P> implements com.gen4j.runner.GeneticAlgorithm<C, V, P> {

    private final Map<Selector<C>, Set<GeneticOperator<C>>> operatorsBySelector = new LinkedHashMap<>();

    private final Random random = new Random(System.nanoTime());

    private final List<GeneticAlgorithmListener> listeners = new ArrayList<>();

    @Override
    public void addGeneticOperator(final GeneticOperator<C> operator, final Selector<C> selector) {
        final Set<GeneticOperator<C>> operators = operatorsBySelector.getOrDefault(selector, new LinkedHashSet<>());
        operators.add(operator);
        operatorsBySelector.putIfAbsent(selector, operators);
    }

    @Override
    public void addListener(final GeneticAlgorithmListener listener) {
        listeners.add(requireNonNull(listener));
    }

    @Override
    public void removeListener(final GeneticAlgorithmListener listener) {
        listeners.remove(requireNonNull(listener));
    }

    @Override
    public void clearListeners() {
        listeners.clear();
    }

    private void notifyNewPopulation(final Population<C> population, final int generationCount) {
        for (int i = 0; i < listeners.size(); i++) {
            listeners.get(i).newGeneration(population, generationCount);
        }
    }

    private void notifyNewSolution(final GeneticAlgorithmSolution<C> solution) {
        for (int i = 0; i < listeners.size(); i++) {
            listeners.get(i).newSolution(solution);
        }
    }

    @Override
    public GeneticAlgorithmSolution<C> evolve(final Population<C> population,
            final GeneticAlgorithmFactory<C, V, P> factory) {

        checkState(!operatorsBySelector.isEmpty());

        Population<C> currentPopulation = population;

        int generation = 0;
        notifyNewPopulation(currentPopulation, generation);

        while (!factory.stopCriteria().apply(currentPopulation, generation)) {
            currentPopulation = applyGeneticOperators(currentPopulation, factory);
            generation++;
            notifyNewPopulation(ImmutablePopulation.of(currentPopulation), generation);
        }

        final GeneticAlgorithmSolution<C> solution = new GeneticAlgorithmSolution<>(
                ImmutablePopulation.of(currentPopulation), generation);

        notifyNewSolution(solution);

        return solution;
    }

    private Population<C> applyGeneticOperators(final Population<C> population,
            final GeneticAlgorithmFactory<C, V, P> factory) {

        prepareSelectors(population);

        final List<Individual<C>> generated = new ArrayList<>();

        while(generated.size() < population.size() )
        {
            addGeneratedChromosomes(population, generated, factory.fitnessFunction());
        }

        final Population<C> newPopulation = PopulationBuilder.of(factory)
                .initialChromosomes(generated)
                .size(population.size())
                .build();

        return newPopulation;
    }

    private void prepareSelectors(final Population<C> population) {
        for (final Selector<C> selector : operatorsBySelector.keySet()) {
            selector.population(population);
        }
    }

    private void addGeneratedChromosomes(final Population<C> population, final List<Individual<C>> generated,
            final FitnessFunction<C> fitnessFunction) {

        for (final Entry<Selector<C>, Set<GeneticOperator<C>>> entry : operatorsBySelector.entrySet()) {
            addGeneratedChromosomes(generated, fitnessFunction, entry.getKey(), entry.getValue());
        }
    }

    private void addGeneratedChromosomes(final List<Individual<C>> generated,
            final FitnessFunction<C> fitnessFunction, final Selector<C> selector, final Set<GeneticOperator<C>> operators) {

        for (final GeneticOperator<C> operator : operators) {

            if (!shouldExecute(operator)) {
                continue;
            }

            // Select and collect generic material
            final Collection<C> selected = transform(
                    selector.select(operator.chromosomeCount()), c -> c.chromosome());

            generated.addAll(transform(
                    operator.apply(selected),
                    g -> new GenericIndividual<>(g, fitnessFunction)));
        }
    }

    private boolean shouldExecute(final GeneticOperator<C> operator) {
        return random.nextInt(100) / 100 < operator.probability();
    }

}
