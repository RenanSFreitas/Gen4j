package com.gen4j.population.impl;

import static java.util.Objects.requireNonNull;

import java.util.Objects;

import com.gen4j.fitness.FitnessFunction;
import com.gen4j.genotype.Genotype;
import com.gen4j.population.Chromosome;
import com.google.common.base.MoreObjects;

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

    @Override
    @SuppressWarnings("unchecked")
    public boolean equals(final Object obj) {
        if( this == obj ) {
            return true;
        }
        if (!(obj instanceof ChromosomeImpl)) {
            return false;
        }
        final ChromosomeImpl<Genotype> that = (ChromosomeImpl<Genotype>) obj;
        return this.genotype().equals(that.genotype()) && this.fitnessFunction.equals(that.fitnessFunction);
    }

    @Override
    public int hashCode() {
        return Objects.hash(genotype(), fitnessFunction);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(Chromosome.class)
                .add("gen", genotype())
                .add("fit", fitness())
                .toString();
    }
}
