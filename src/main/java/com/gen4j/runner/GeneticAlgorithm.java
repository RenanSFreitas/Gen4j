package com.gen4j.runner;

import com.gen4j.chromosome.Chromosome;
import com.gen4j.factory.GeneticAlgorithmFactory;
import com.gen4j.operator.GeneticOperator;
import com.gen4j.operator.selection.Selector;
import com.gen4j.population.Individual;
import com.gen4j.population.Population;
import com.gen4j.runner.listener.GeneticAlgorithmListener;

public interface GeneticAlgorithm<C extends Chromosome, V, P> {

    GeneticAlgorithmSolution<C> evolve(Population<C> population, GeneticAlgorithmFactory<C, V, P> factory);

    Individual<C> fittest();

    void addGeneticOperator(GeneticOperator<C> operator, Selector<C> selector);

    void addListener(GeneticAlgorithmListener listener);

    void removeListener(GeneticAlgorithmListener listener);

    void clearListeners();
}
