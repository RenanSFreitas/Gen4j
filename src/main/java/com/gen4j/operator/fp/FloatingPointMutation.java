package com.gen4j.operator.fp;

import com.gen4j.chromosome.Range;
import com.gen4j.chromosome.code.ChromosomeCodeType;
import com.gen4j.chromosome.fp.FloatingPointChromosome;
import com.gen4j.factory.GeneticAlgorithmFactory;
import com.gen4j.operator.AbstractGeneticOperator;
import com.gen4j.operator.Mutation;
import com.gen4j.population.Individual;

public final class FloatingPointMutation extends AbstractGeneticOperator<FloatingPointChromosome>
        implements Mutation<FloatingPointChromosome> {

    public FloatingPointMutation() {
        super(0.25, 1, ChromosomeCodeType.FLOATING_POINT);
    }

    @Override
    public Individual<FloatingPointChromosome> apply(final Individual<FloatingPointChromosome> individual,
            final GeneticAlgorithmFactory<FloatingPointChromosome> factory, final int generationCount) {


        final double[] originalValue = individual.chromosome().value();
        final int length = originalValue.length;
        final double[] mutantValue = new double[length];
        for (int i = 0; i < mutantValue.length; i++) {
            final Range range = factory.coder().range(i);
            if (random.nextDouble() < probability()) {
                mutantValue[i] = random.nextDouble() * range.length() + range.lowerBound();
            } else {
                mutantValue[i] = originalValue[i];
            }
        }

        return factory.individual(new FloatingPointChromosome(mutantValue));
    }

    @Override
    public String toString() {
        return "Random float floating-point-chromosome mutation";
    }
}
