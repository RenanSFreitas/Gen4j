package com.gen4j.operator.selection;

import static java.util.Collections.singletonList;

import java.util.List;

import com.gen4j.genotype.Genotype;
import com.gen4j.population.Chromosome;
import com.gen4j.population.Population;

public final class BestFitnessSelector<G extends Genotype> implements Selector<G> {

    @Override
    public List<Chromosome<G>> select(final Population<G> population, final int count) {
        return singletonList(population.fitness().lastKey());
    }

}
