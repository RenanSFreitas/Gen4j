package com.gen4j.operator.selection;

import java.util.List;

import com.gen4j.chromosome.Chromosome;
import com.gen4j.population.Individual;
import com.gen4j.population.Population;
import com.gen4j.utils.Pair;

public interface Selector<C extends Chromosome> {

    void population(Population<C> population);

    Population<C> population();

    Pair<Individual<C>, Individual<C>> selectPair();

    List<Individual<C>> select(int count);
}
