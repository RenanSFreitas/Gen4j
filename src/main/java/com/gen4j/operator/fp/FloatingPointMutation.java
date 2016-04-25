package com.gen4j.operator.fp;

import java.util.function.Function;

import com.gen4j.chromosome.Range;
import com.gen4j.chromosome.code.ChromosomeCodeType;
import com.gen4j.chromosome.fp.FloatingPointChromosome;
import com.gen4j.operator.AbstractGeneticOperator;
import com.gen4j.operator.Mutation;
import com.gen4j.population.Individual;

public final class FloatingPointMutation extends AbstractGeneticOperator<FloatingPointChromosome>
        implements Mutation<FloatingPointChromosome> {

    public FloatingPointMutation() {
        super(0.25, ChromosomeCodeType.FLOATING_POINT);
    }

    @Override
    public Individual<FloatingPointChromosome> apply(final Individual<FloatingPointChromosome> individual,
            final Function<FloatingPointChromosome, Individual<FloatingPointChromosome>> toIndividual) {

        final FloatingPointChromosome chromosome = individual.chromosome();
        final double[] originalValue = chromosome.value();
        final int length = originalValue.length;
        final double[] newValue = new double[length];
        System.arraycopy(originalValue, 0, newValue, 0, length);

        final Range range = chromosome.range();
        for (int j = 0; j < chromosome.length(); j++) {
            if (random.nextDouble() < probability()) {
                newValue[random.nextInt(length)] = random.nextDouble() * range.length() + range.lowerBound();
            }
        }
        return toIndividual.apply(new FloatingPointChromosome(newValue, range));
    }

    @Override
    public String toString() {
        return "Random float floating-point-chromosome mutation";
    }

}
