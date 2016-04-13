package com.gen4j.population.generic;

import static java.util.Objects.requireNonNull;

import java.util.Objects;

import com.gen4j.chromosome.Chromosome;
import com.gen4j.fitness.FitnessFunction;
import com.gen4j.population.Individual;
import com.google.common.base.MoreObjects;

public final class GenericIndividual<C extends Chromosome> implements Individual<C> {

    private final C chromosome;
    private final FitnessFunction<C> fitnessFunction;
    private double fitness = Double.MIN_VALUE;

    public GenericIndividual(final C chromosome, final FitnessFunction<C> fitnessFunction) {
        this.fitnessFunction = requireNonNull(fitnessFunction);
        this.chromosome = requireNonNull(chromosome);
    }

    @Override
    public final C chromosome() {
        return chromosome;
    }

    @Override
    public final double fitness() {
        if (fitness == Double.MIN_VALUE) {
            fitness = fitnessFunction.evaluate(chromosome);
        }
        return fitness;
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean equals(final Object obj) {
        if( this == obj ) {
            return true;
        }
        if (!(obj instanceof GenericIndividual)) {
            return false;
        }
        final GenericIndividual<Chromosome> that = (GenericIndividual<Chromosome>) obj;
        return this.fitness == that.fitness && this.chromosome().equals(that.chromosome());
    }

    @Override
    public int hashCode() {
        return Objects.hash(fitness, chromosome());
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(Individual.class)
                .add("gen", chromosome())
                .add("fit", fitness())
                .toString();
    }
}
