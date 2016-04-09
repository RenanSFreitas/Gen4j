package com.gen4j.runner;

import com.gen4j.factory.GeneticAlgorithmFactory;
import com.gen4j.genotype.Genotype;
import com.gen4j.operator.GeneticOperator;
import com.gen4j.operator.selection.Selector;
import com.gen4j.population.Population;

public interface GeneticAlgorithm<G extends Genotype, V, P> {

    Population<G> execute(Population<G> population, GeneticAlgorithmFactory<G, V, P> factory);

    void addGeneticOperator(GeneticOperator<G> operator, Selector<G> selector);
}
