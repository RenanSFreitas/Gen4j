package com.gen4j.operator.selection;

import java.util.Collection;

import com.gen4j.chromosome.Chromosome;
import com.gen4j.population.Individual;
import com.gen4j.population.Population;

public interface Selector<G extends Chromosome> {

    void population(Population<G> population);

    Population<G> population();

    Collection<Individual<G>> select(int count);
}
