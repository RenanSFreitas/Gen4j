package com.gen4j.operator;

import java.util.function.Function;

import com.gen4j.chromosome.Chromosome;
import com.gen4j.population.Individual;
import com.gen4j.utils.Pair;

public interface CrossOver<C extends Chromosome> extends GeneticOperator<C> {

    Pair<Individual<C>, Individual<C>> apply(
            final Pair<Individual<C>, Individual<C>> parents,
            Function<C, Individual<C>> toIndividual);
}
