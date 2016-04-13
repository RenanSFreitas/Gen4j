package com.gen4j.operator.selection;

import static java.util.Collections.singletonList;

import java.util.List;

import com.gen4j.chromosome.Chromosome;
import com.gen4j.population.Individual;

public final class BestFitnessSelector<G extends Chromosome> extends AbstractSelector<G> {

    @Override
    public List<Individual<G>> select(final int count) {
        return singletonList(population().fittest());
    }

}
