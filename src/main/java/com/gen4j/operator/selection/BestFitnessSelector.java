package com.gen4j.operator.selection;

import java.util.List;

import com.gen4j.chromosome.Chromosome;
import com.gen4j.population.Individual;
import com.gen4j.utils.Pair;

public final class BestFitnessSelector<G extends Chromosome> extends AbstractSelector<G> {

    @Override
    public Pair<Individual<G>, Individual<G>> selectPair() {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Individual<G>> select(final int count) {
        throw new UnsupportedOperationException();
    }
}
