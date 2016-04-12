/**
 *
 */
package com.gen4j.operator.selection;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import com.gen4j.genotype.Genotype;
import com.gen4j.population.ImmutablePopulation;
import com.gen4j.population.Population;

/**
 *
 */
public abstract class AbstractSelector<G extends Genotype> implements Selector<G> {

    private Population<G> population;

    @Override
    public void population(final Population<G> population) {
        checkNotNull(population);
        checkArgument(!population.isEmpty());
        this.population = ImmutablePopulation.of(population);
    }

    @Override
    public final Population<G> population() {
        return population;
    }
}
