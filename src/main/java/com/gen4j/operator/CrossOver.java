package com.gen4j.operator;

import com.gen4j.chromosome.Chromosome;
import com.gen4j.factory.GeneticAlgorithmFactory;
import com.gen4j.population.Individual;
import com.gen4j.utils.Pair;

public interface CrossOver<C extends Chromosome> extends GeneticOperator<C> {

    Pair<Individual<C>, Individual<C>> apply(Pair<Individual<C>, Individual<C>> parents,
            GeneticAlgorithmFactory<C> factory, int generationCount);
}
