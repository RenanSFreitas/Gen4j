package com.gen4j.operator;

import java.util.function.Function;

import com.gen4j.chromosome.Chromosome;
import com.gen4j.population.Individual;

public interface Mutation<C extends Chromosome> extends GeneticOperator<C> {

    Individual<C> apply(Individual<C> individual, Function<C, Individual<C>> toIndividual);
}
