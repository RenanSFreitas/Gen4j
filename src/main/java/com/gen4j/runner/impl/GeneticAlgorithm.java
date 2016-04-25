package com.gen4j.runner.impl;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.function.Function;

import com.gen4j.chromosome.Chromosome;
import com.gen4j.factory.GeneticAlgorithmFactory;
import com.gen4j.operator.CrossOver;
import com.gen4j.operator.GeneticOperator;
import com.gen4j.operator.Mutation;
import com.gen4j.operator.selection.Selector;
import com.gen4j.population.Individual;
import com.gen4j.population.Population;
import com.gen4j.population.PopulationBuilder;
import com.gen4j.runner.GeneticAlgorithmSolution;
import com.gen4j.runner.listener.GeneticAlgorithmListener;
import com.gen4j.utils.Pair;

//TODO: tests for invalid stuff
//TODO: refactor the operators stuff
//TODO: stuff
public class GeneticAlgorithm<C extends Chromosome> implements com.gen4j.runner.GeneticAlgorithm<C> {

    private final Selector<C> selector;
    private final CrossOver<C> crossOver;
    private final Mutation<C> mutation;

    private final Random random = new Random(System.nanoTime());

    private final List<GeneticAlgorithmListener<C>> listeners = new ArrayList<>();

    private Individual<C> fittest;

    private final GeneticAlgorithmSolution.Builder<C> solutionBuilder = GeneticAlgorithmSolution.builder();

    public GeneticAlgorithm(final Selector<C> selector, final CrossOver<C> crossOver, final Mutation<C> mutation) {
        checkNotNull(crossOver);
        checkNotNull(mutation);
        checkArgument(crossOver.chromosomeCodeType() == mutation.chromosomeCodeType(),
                "Cross over and mutation code types mismatch");

        this.selector = Objects.requireNonNull(selector);
        solutionBuilder.codeType(crossOver.chromosomeCodeType());
        this.crossOver = crossOver;
        this.mutation = mutation;
    }

    @Override
    public void addGeneticOperator(final GeneticOperator<C> operator, final Selector<C> selector) {
        throw new UnsupportedOperationException();
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

    private void notifyNewFittest(final Individual<C> newFittest, final Individual<C> oldFittest) {
        for (int i = 0; i < listeners.size(); i++) {
            listeners.get(i).newFittest(newFittest, oldFittest);
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

        int generationCount = 0;

        notifyNewPopulation(population, generationCount);

        Population<C> current = population;

        while (!factory.stopCriteria().apply(current, generationCount, fittest)) {

            selector.population(current);

            current = applyGeneticOperators(current, factory);
            storeFittest(current);
            generationCount++;

            notifyNewPopulation(current, generationCount);
        }

        final GeneticAlgorithmSolution<C> solution = solutionBuilder.population(current)
                .fittest(fittest)
                .generationsCount(generationCount)
                .build();

        notifyNewSolution(solution);

        return solution;
    }

    private void storeFittest(final Population<C> currentPopulation) {
        final Individual<C> currentFittest = currentPopulation.fittest();
        if (fittest == null || fittest.fitness() < currentFittest.fitness()) {
            notifyNewFittest(currentFittest, fittest);
            fittest = currentFittest;
        }
    }

    private Population<C> applyGeneticOperators(final Population<C> current,
            final GeneticAlgorithmFactory<C> factory) {

        final Function<C, Individual<C>> toIndividual = c -> factory.individual(c);

        final List<Individual<C>> newIndividuals = new ArrayList<>();

        while (newIndividuals.size() < current.size() - 1) {
            final Pair<Individual<C>, Individual<C>> parents = selector.selectPair();

            Pair<Individual<C>, Individual<C>> offspring = null;

            if(random.nextDouble() < crossOver.probability()) {
                offspring = crossOver.apply(parents, toIndividual);
            } else {
                offspring = parents;
            }

            newIndividuals.add(mutation.apply(offspring.first(), toIndividual));
            newIndividuals.add(mutation.apply(offspring.second(), toIndividual));

//            if (random.nextDouble() < 0.3) {
//                newIndividuals.addAll(selector.select(3));
//            }
        }
        newIndividuals.add(fittest);

        return PopulationBuilder.of(factory)
                .initial(newIndividuals)
                .size(newIndividuals.size())
                .build();
    }

}
