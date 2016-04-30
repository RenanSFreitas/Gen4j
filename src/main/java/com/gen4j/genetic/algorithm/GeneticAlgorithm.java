package com.gen4j.genetic.algorithm;

import com.gen4j.chromosome.Chromosome;
import com.gen4j.factory.GeneticAlgorithmFactory;
import com.gen4j.generation.replacement.GenerationReplacer;
import com.gen4j.operator.GeneticOperator;
import com.gen4j.operator.selection.Selector;
import com.gen4j.population.Individual;
import com.gen4j.population.Population;
import com.gen4j.runner.listener.GeneticAlgorithmListener;

public interface GeneticAlgorithm<C extends Chromosome> {

    static <C extends Chromosome> GeneticAlgorithm<C> create(final Selector<C> selector,
            final GeneticOperator<C> crossOver, final GeneticOperator<C> mutation) {
        return new GeneticAlgorithmImpl<>(selector, crossOver, mutation);
    }

    GeneticAlgorithmSolution<C> evolve(Population<C> population, GeneticAlgorithmFactory<C> factory);

    Individual<C> fittest();

    void addListener(GeneticAlgorithmListener<C> listener);

    void removeListener(GeneticAlgorithmListener<C> listener);

    void setElitismCount(int elitismCount);

    void setPopulationExchanger(GenerationReplacer<C> exchanger);

    void clearListeners();
}
