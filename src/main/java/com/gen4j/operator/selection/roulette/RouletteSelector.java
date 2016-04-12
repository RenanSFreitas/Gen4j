package com.gen4j.operator.selection.roulette;

import java.util.List;

import com.gen4j.genotype.Genotype;
import com.gen4j.operator.selection.AbstractSelector;
import com.gen4j.population.Chromosome;
import com.gen4j.population.Population;

public final class RouletteSelector<G extends Genotype> extends AbstractSelector<G> {

    private Roulette<G> roulette;

    @Override
    public void population(final Population<G> population) {
        super.population(population);
        roulette = Roulette.of(population());
    }

    @Override
    public List<Chromosome<G>> select(final int count) {
        return roulette.sortChromosomes(count);
    }
}
