package com.gen4j.runner.impl;

import static com.google.common.base.Preconditions.checkArgument;
import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.Set;

import com.gen4j.chromosome.Chromosome;
import com.gen4j.factory.GeneticAlgorithmFactory;
import com.gen4j.operator.GeneticOperator;
import com.gen4j.operator.selection.Selector;
import com.gen4j.population.Individual;
import com.gen4j.population.Population;
import com.gen4j.population.PopulationBuilder;
import com.gen4j.runner.GeneticAlgorithmSolution;
import com.gen4j.runner.listener.GeneticAlgorithmListener;
import com.google.common.collect.ImmutableList;

//TODO: tests for invalid stuff
//TODO: refactor the operators stuff
//TODO: stuff
public class GeneticAlgorithm<C extends Chromosome> implements com.gen4j.runner.GeneticAlgorithm<C> {

    private final Selector<C> selector;
    private final List<GeneticOperator<C>> operators;
    private int[] individualCounts;

    private final Map<Selector<C>, GeneticOperator<C>> extraOperatorsBySelector = new LinkedHashMap<>();

    private final Random random = new Random(System.nanoTime());

    private final List<GeneticAlgorithmListener> listeners = new ArrayList<>();

    private Individual<C> fittest;

    public GeneticAlgorithm(final Selector<C> selector, final Set<GeneticOperator<C>> operators) {
        checkArgument(operators != null && !operators.isEmpty());
        this.selector = Objects.requireNonNull(selector);
        this.operators = ImmutableList.copyOf(operators);
    }

    @Override
    public void addGeneticOperator(final GeneticOperator<C> operator, final Selector<C> selector) {
        extraOperatorsBySelector.put(selector, operator);
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
            listeners.get(i).newGeneration(population, generationCount, fittest);
        }
    }

    private void notifyNewSolution(final GeneticAlgorithmSolution<C> solution) {
        for (int i = 0; i < listeners.size(); i++) {
            listeners.get(i).newSolution(solution, fittest);
        }
    }

    @Override
    public Individual<C> fittest() {
        return fittest;
    }

    @Override
    public GeneticAlgorithmSolution<C> evolve(final Population<C> population,
            final GeneticAlgorithmFactory<C> factory) {

        storeFittest(population);

        initializeIndividualCount(population);

        int generation = 0;
        notifyNewPopulation(population, generation);

        Population<C> currentPopulation = population;

        while (!factory.stopCriteria().apply(currentPopulation, generation)) {

            selector.population(currentPopulation);
            currentPopulation = applyGeneticOperators(selector.select(currentPopulation.size()), factory);
            storeFittest(currentPopulation);
            generation++;

            notifyNewPopulation(currentPopulation, generation);
        }

        final GeneticAlgorithmSolution<C> solution = new GeneticAlgorithmSolution<>(currentPopulation, generation,
                fittest);

        notifyNewSolution(solution);

        return solution;
    }

    private void initializeIndividualCount(final Population<C> population) {
        individualCounts = new int[population.size()];
        for (int i = 0; i < operators.size(); i++) {
            final GeneticOperator<C> operator = operators.get(i);
            individualCounts[i] = (int) Math.floor(operator.chromosomeCount() * population.size());
        }
    }

    private void storeFittest(final Population<C> currentPopulation) {
        final Individual<C> currentFittest = currentPopulation.fittest();
        if (fittest == null || fittest.fitness() < currentFittest.fitness()) {
            fittest = currentFittest;
        }
    }

    private Population<C> applyGeneticOperators(final List<Individual<C>> selected,
            final GeneticAlgorithmFactory<C> factory) {

        for (int i = 0; i < operators.size(); i++) {
            applyGeneticOperator(selected, factory, operators.get(i), individualCounts[i]);
        }

        return PopulationBuilder.of(factory)
                .initialChromosomes(selected)
                .size(selected.size())
                .build();
    }

    private void applyGeneticOperator(final List<Individual<C>> selected,
            final GeneticAlgorithmFactory<C> factory,
            final GeneticOperator<C> operator,
            final int individualsCount) {

        final int operatorCount = operator.chromosomeCount();
        final List<Individual<C>> operatorInput = new ArrayList<>(operatorCount);

        for (int count = individualsCount; count > 0; count -= operatorCount) {
            final int[] indexes = new int[operatorCount];
            for (int i = 0; i < operatorCount; i++) {
                indexes[i] = random.nextInt(selected.size());
                operatorInput.add(selected.get(indexes[i]));
            }

            final List<Individual<C>> result = operator.apply(operatorInput, factory);
            for (int i = 0; i < operatorCount; i++) {
                selected.set(indexes[i], result.get(i));
            }
            operatorInput.clear();
        }
    }
}
