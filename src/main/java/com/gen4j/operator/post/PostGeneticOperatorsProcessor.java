package com.gen4j.operator.post;

import com.gen4j.chromosome.Chromosome;
import com.gen4j.population.Individual;

public interface PostGeneticOperatorsProcessor<C extends Chromosome> {

    Individual<C> postProcess(Individual<C> individual);
}
