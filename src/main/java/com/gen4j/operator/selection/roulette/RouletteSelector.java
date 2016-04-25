package com.gen4j.operator.selection.roulette;

import java.util.List;

import com.gen4j.chromosome.Chromosome;
import com.gen4j.operator.selection.AbstractSelector;
import com.gen4j.population.Individual;
import com.gen4j.population.Population;
import com.gen4j.utils.Pair;

public final class RouletteSelector<G extends Chromosome> extends AbstractSelector<G> {

    private Roulette<G> roulette;

    @Override
    public void population(final Population<G> population) {
        super.population(population);
        roulette = Roulette.of(population());
    }

    @Override
    public Pair<Individual<G>, Individual<G>> selectPair() {
        return Pair.of(roulette.sortChromosome(), roulette.sortChromosome());
    }

    @Override
    public List<Individual<G>> select(final int count) {
        return roulette.sortChromosomes(count);
    }
}
