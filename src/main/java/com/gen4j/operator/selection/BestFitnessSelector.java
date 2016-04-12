package com.gen4j.operator.selection;

import static java.util.Collections.singletonList;

import java.util.List;

import com.gen4j.genotype.Genotype;
import com.gen4j.population.Chromosome;

public final class BestFitnessSelector<G extends Genotype> extends AbstractSelector<G> {

    @Override
    public List<Chromosome<G>> select(final int count) {
        return singletonList(population().fittest());
    }

}
