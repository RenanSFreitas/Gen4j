package com.gen4j.operator;

import java.util.Collection;
import java.util.List;

import com.gen4j.chromosome.Chromosome;
import com.gen4j.factory.GeneticAlgorithmFactory;
import com.gen4j.population.Individual;

public interface GeneticOperator<C extends Chromosome> {

    double probability();

    void probability(double probability);

    List<Individual<C>> apply(Collection<Individual<C>> individuals, GeneticAlgorithmFactory<C> factory);

    int chromosomeCount();
}
