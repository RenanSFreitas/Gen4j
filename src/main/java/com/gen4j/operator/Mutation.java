package com.gen4j.operator;

import com.gen4j.chromosome.Chromosome;
import com.gen4j.factory.GeneticAlgorithmFactory;
import com.gen4j.population.Individual;

public interface Mutation<C extends Chromosome> extends GeneticOperator<C> {

    Individual<C> apply(Individual<C> individual, GeneticAlgorithmFactory<C> factory, int generationCount);
}
