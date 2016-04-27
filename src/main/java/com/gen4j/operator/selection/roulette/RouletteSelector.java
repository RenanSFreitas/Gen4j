package com.gen4j.operator.selection.roulette;

import static com.google.common.base.Preconditions.checkArgument;

import java.util.List;

import com.gen4j.chromosome.Chromosome;
import com.gen4j.operator.selection.AbstractSelector;
import com.gen4j.population.Individual;
import com.gen4j.population.Population;

public final class RouletteSelector<G extends Chromosome> extends AbstractSelector<G> {

    private Roulette<G> roulette;
    private final int selectionPressure;

    public RouletteSelector(final int selectionPressure) {
        checkArgument(selectionPressure < 0, "Roulette Wheel selection pressure should be below 0. Please, check the documentation");
        this.selectionPressure = selectionPressure;
    }

    @Override
    public void population(final Population<G> population) {
        super.population(population);
        roulette = Roulette.of(population(), selectionPressure);
    }

    @Override
    public List<Individual<G>> select(final int count) {
        return roulette.sortChromosomes(count);
    }
}
