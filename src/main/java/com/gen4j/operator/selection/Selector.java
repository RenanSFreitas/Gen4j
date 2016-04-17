package com.gen4j.operator.selection;

import java.util.List;

import com.gen4j.chromosome.Chromosome;
import com.gen4j.population.Individual;
import com.gen4j.population.Population;

public interface Selector<G extends Chromosome> {

    void population(Population<G> population);

    Population<G> population();

    List<Individual<G>> select(int count);
}
