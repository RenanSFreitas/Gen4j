package com.gen4j.population;

import com.gen4j.chromosome.Chromosome;
import com.google.common.math.DoubleMath;

@FunctionalInterface
public interface Criteria<G extends Chromosome> {

    static <C extends Chromosome> Criteria<C> fitnessEquals(final double target, final double tolerance) {
        return (p, g) -> DoubleMath.fuzzyEquals(p.fittest().fitness(), target, tolerance);
    }

    static <C extends Chromosome> Criteria<C> maxGenerations(final int max) {
        return (p, g) -> max <= g;
    }

    boolean apply(Population<G> population, int generation);
}
