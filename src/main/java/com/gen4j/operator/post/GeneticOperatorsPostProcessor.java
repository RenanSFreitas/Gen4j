package com.gen4j.operator.post;

import java.util.Optional;

import com.gen4j.chromosome.Chromosome;
import com.gen4j.population.Individual;

public interface GeneticOperatorsPostProcessor<C extends Chromosome> {

    Optional<Individual<C>> postProcess(Optional<Individual<C>> individual);
}
