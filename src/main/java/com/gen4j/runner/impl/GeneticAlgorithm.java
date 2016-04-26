package com.gen4j.runner.impl;

import static com.google.common.base.Preconditions.checkArgument;
import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

import com.gen4j.chromosome.Chromosome;
import com.gen4j.factory.GeneticAlgorithmFactory;
import com.gen4j.operator.GeneticOperator;
import com.gen4j.operator.selection.Selector;
import com.gen4j.population.Individual;
import com.gen4j.population.Population;
import com.gen4j.population.PopulationBuilder;
import com.gen4j.runner.GeneticAlgorithmSolution;
import com.gen4j.runner.listener.GeneticAlgorithmListener;

//TODO: tests for invalid stuff
//TODO: refactor the operators stuff
//TODO: stuff
public class GeneticAlgorithm<C extends Chromosome> implements com.gen4j.runner.GeneticAlgorithm<C> {

    private final Selector<C> selector;
    private final GeneticOperator<C> crossOver;
    private final GeneticOperator<C> mutation;

    private final Map<Selector<C>, GeneticOperator<C>> extraOperatorsBySelector = new LinkedHashMap<>();

    private final Random random = new Random(System.nanoTime());

    private final List<GeneticAlgorithmListener<C>> listeners = new ArrayList<>();

    private Individual<C> fittest;

    private final GeneticAlgorithmSolution.Builder<C> solutionBuilder = GeneticAlgorithmSolution.builder();

    public GeneticAlgorithm(final Selector<C> selector, final GeneticOperator<C> crossOver,
            final GeneticOperator<C> mutation) {
        checkArgument(crossOver.chromosomeCodeType() == mutation.chromosomeCodeType(),
                "Multiple code types. Should be either BIT or FLOATING_POINT.");

        this.selector = Objects.requireNonNull(selector);
        this.crossOver = crossOver;
        this.mutation = mutation;

        solutionBuilder.codeType(crossOver.chromosomeCodeType());
    }

    @Override
    public void addGeneticOperator(final GeneticOperator<C> operator, final Selector<C> selector) {
        extraOperatorsBySelector.put(selector, operator);
    }

    @Override
    public void addListener(final GeneticAlgorithmListener<C> listener) {
        listeners.add(requireNonNull(listener));
    }

    @Override
    public void removeListener(final GeneticAlgorithmListener<C> listener) {
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

        int generation = 0;
        notifyNewPopulation(population, generation);

        Population<C> current = population;

        while (!factory.stopCriteria().apply(current, generation)) {

            selector.population(current);

            current = applyGeneticOperators(selector.select(current.size()), factory);
            storeFittest(current);
            generation++;

            notifyNewPopulation(current, generation);
        }

        final GeneticAlgorithmSolution<C> solution = solutionBuilder.population(current)
                .fittest(fittest)
                .generationsCount(generation)
                .build();

        notifyNewSolution(solution);

        return solution;
    }

    private void storeFittest(final Population<C> currentPopulation) {
        final Individual<C> currentFittest = currentPopulation.fittest();
        if (fittest == null || fittest.fitness() < currentFittest.fitness()) {
            fittest = currentFittest;
        }
    }

    private Population<C> applyGeneticOperators(final List<Individual<C>> selected,
            final GeneticAlgorithmFactory<C> factory) {

        final List<Individual<C>> next = new ArrayList<>(selected.size());

        while (next.size() < selected.size()) {

            List<Individual<C>> individuals = selector.select(2);
            if (random.nextDouble() < crossOver.probability()) {
                individuals = crossOver.apply(individuals, factory);
            }

            individuals = mutation.apply(individuals, factory);

            next.addAll(individuals);
        }

        // selected.add(fittest);

        return PopulationBuilder.of(factory)
                .initialChromosomes(next)
                .size(next.size())
                .build();
    }
}
