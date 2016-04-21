package com.gen4j.operator.selection;

import java.util.List;

import com.gen4j.chromosome.Chromosome;
import com.gen4j.population.Individual;
import com.gen4j.population.Population;

public interface Selector<C extends Chromosome> {

    void population(Population<C> population);

    Population<C> population();

    List<Individual<C>> select(int count);
}
