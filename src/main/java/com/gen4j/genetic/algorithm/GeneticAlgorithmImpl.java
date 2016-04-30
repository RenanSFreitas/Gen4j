package com.gen4j.genetic.algorithm;

import static com.google.common.base.Preconditions.checkArgument;
import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import com.gen4j.chromosome.Chromosome;
import com.gen4j.factory.GeneticAlgorithmFactory;
import com.gen4j.generation.replacement.GenerationReplacer;
import com.gen4j.generation.replacement.StandardGenerationReplacer;
import com.gen4j.operator.GeneticOperator;
import com.gen4j.operator.selection.Selector;
import com.gen4j.population.Individual;
import com.gen4j.population.Population;
import com.gen4j.population.PopulationBuilder;
import com.gen4j.runner.listener.GeneticAlgorithmListener;

final class GeneticAlgorithmImpl<C extends Chromosome> implements GeneticAlgorithm<C> {

    private final Selector<C> selector;
    private final GeneticOperator<C> crossOver;
    private final GeneticOperator<C> mutation;

    private final Random random = new Random(System.nanoTime());

    private final List<GeneticAlgorithmListener<C>> listeners = new ArrayList<>();

    private Individual<C> fittest;

    private int elitismCount;

    private GenerationReplacer<C> replacer = StandardGenerationReplacer.GENERATIONAL.get();

    private final GeneticAlgorithmSolution.Builder<C> solutionBuilder = GeneticAlgorithmSolution.builder();

    GeneticAlgorithmImpl(final Selector<C> selector, final GeneticOperator<C> crossOver,
            final GeneticOperator<C> mutation) {
        checkArgument(crossOver.chromosomeCodeType() == mutation.chromosomeCodeType(),
                "Multiple code types. Should be either BIT or FLOATING_POINT.");

        this.selector = Objects.requireNonNull(selector);
        this.crossOver = crossOver;
        this.mutation = mutation;

        solutionBuilder.codeType(crossOver.chromosomeCodeType());
    }

    @Override
    public void setElitismCount(final int elitismCount) {
        checkArgument(elitismCount > -1);
        this.elitismCount = elitismCount;
    }

    @Override
    public void setPopulationExchanger(final GenerationReplacer<C> exchanger) {
        this.replacer = requireNonNull(exchanger);
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

    private void notifyEvolutionInterrupted(final Throwable cause) {
        for (final GeneticAlgorithmListener<C> listener : listeners) {
            listener.evolutionInterruped(cause);
        }
    }

    @Override
    public Individual<C> fittest() {
        return fittest;
    }

    @Override
    public GeneticAlgorithmSolution<C> evolve(final Population<C> population,
            final GeneticAlgorithmFactory<C> factory) {

        GeneticAlgorithmSolution<C> solution = null;

        try {

            solution = performEvolution(population, factory);

        } catch (final Throwable cause) {
            notifyEvolutionInterrupted(cause);
            throw cause;
        }

        return solution;
    }

    private GeneticAlgorithmSolution<C> performEvolution(final Population<C> population,
            final GeneticAlgorithmFactory<C> factory) {

        checkArgument(population.size() > elitismCount, "Population size %s too small for elitism of %s individuals",
                population.size(), elitismCount);

        storeFittest(population);

        int generation = 0;
        notifyNewPopulation(population, generation);

        Population<C> currentGeneration = population;

        while (!factory.stopCriteria().apply(currentGeneration, generation)) {

            currentGeneration = applyGeneticOperators(currentGeneration, factory, generation);
            storeFittest(currentGeneration);
            generation++;

            notifyNewPopulation(currentGeneration, generation);
        }

        final GeneticAlgorithmSolution<C> solution = solutionBuilder.population(currentGeneration)
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

    private Population<C> applyGeneticOperators(final Population<C> generation,
            final GeneticAlgorithmFactory<C> factory, final int generationCount) {

        selector.population(generation);
        final List<Individual<C>> nextGenerationList = new ArrayList<>(generation.size());

        nextGenerationList.addAll(getFittestIndividuals(generation));

        while (nextGenerationList.size() < generation.size()) {

            List<Individual<C>> individuals = selector.select(2);
            if (random.nextDouble() < crossOver.probability()) {
                individuals = crossOver.apply(individuals, factory, generationCount);
            }

            individuals = mutation.apply(individuals, factory, generationCount);

            nextGenerationList.addAll(individuals);
        }

        final Population<C> nextGeneration = PopulationBuilder.of(factory)
                .initialChromosomes(nextGenerationList)
                .size(nextGenerationList.size())
                .build();

        return replacer.replace(generation, nextGeneration, factory);
    }

    private Collection<? extends Individual<C>> getFittestIndividuals(final Population<C> current) {
        final List<Individual<C>> individuals = current.fitness();
        return individuals.subList(individuals.size() - elitismCount, individuals.size());
    }
}
