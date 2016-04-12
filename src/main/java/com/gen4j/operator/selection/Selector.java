package com.gen4j.operator.selection;

import java.util.Collection;

import com.gen4j.genotype.Genotype;
import com.gen4j.population.Chromosome;
import com.gen4j.population.Population;

public interface Selector<G extends Genotype> {

    void population(Population<G> population);

    Population<G> population();

    Collection<Chromosome<G>> select(int count);
}
