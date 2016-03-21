package com.gen4j.population.impl;

import static java.util.Objects.requireNonNull;

import com.gen4j.fitness.FitnessFunction;
import com.gen4j.genotype.Genotype;
import com.gen4j.population.Chromosome;

public final class ChromosomeImpl<G extends Genotype> implements Chromosome<G> {

    private final G genotype;
    private final FitnessFunction<G> fitnessFunction;
    private double fitness = Double.MIN_VALUE;

    public ChromosomeImpl(final G genotype, final FitnessFunction<G> fitnessFunction) {
        this.fitnessFunction = requireNonNull(fitnessFunction);
        this.genotype = requireNonNull(genotype);
    }

    @Override
    public final G genotype() {
        return genotype;
    }

    @Override
    public final double fitness() {
        if (fitness == Double.MIN_VALUE) {
            fitness = fitnessFunction.evaluate(genotype);
        }
        return fitness;
    }
}
