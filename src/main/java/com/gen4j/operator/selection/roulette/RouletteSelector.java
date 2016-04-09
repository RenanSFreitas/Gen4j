package com.gen4j.operator.selection.roulette;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import java.util.List;

import com.gen4j.genotype.Genotype;
import com.gen4j.operator.selection.Selector;
import com.gen4j.population.Chromosome;
import com.gen4j.population.Population;

public final class RouletteSelector<G extends Genotype> implements Selector<G> {

    @Override
    public List<Chromosome<G>> select(final Population<G> population, final int count) {
        checkNotNull(population);
        checkArgument(!population.isEmpty());
        return Roulette.of(population).sortChromosomes(count);
    }
}
