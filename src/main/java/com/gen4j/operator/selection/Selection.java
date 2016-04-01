package com.gen4j.operator.selection;

import java.util.List;

import com.gen4j.genotype.Genotype;
import com.gen4j.population.Chromosome;
import com.gen4j.population.Population;

public interface Selection<G extends Genotype> {

    List<Chromosome<G>> select(Population<G> population, int count);
}
