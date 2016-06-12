package com.gen4j.operator.fp;

import com.gen4j.chromosome.code.ChromosomeCodeType;
import com.gen4j.chromosome.fp.FloatingPointChromosome;
import com.gen4j.factory.GeneticAlgorithmFactory;
import com.gen4j.operator.AbstractGeneticOperator;
import com.gen4j.operator.CrossOver;
import com.gen4j.population.Individual;
import com.gen4j.utils.Pair;

public final class FloatingPointUniformCrossOver extends AbstractGeneticOperator<FloatingPointChromosome>
        implements CrossOver<FloatingPointChromosome>
 {

    public FloatingPointUniformCrossOver() {
        super(0.25, 2, ChromosomeCodeType.FLOATING_POINT);
    }

    @Override
    public Pair<Individual<FloatingPointChromosome>, Individual<FloatingPointChromosome>> apply(
            final Pair<Individual<FloatingPointChromosome>, Individual<FloatingPointChromosome>> parents,
            final GeneticAlgorithmFactory<FloatingPointChromosome> factory, final int generationCount) {

        return apply(parents.first().chromosome(), parents.second().chromosome(), factory);
    }

    private Pair<Individual<FloatingPointChromosome>, Individual<FloatingPointChromosome>> apply(
            final FloatingPointChromosome parent1, final FloatingPointChromosome parent2,
            final GeneticAlgorithmFactory<FloatingPointChromosome> factory) {

        int length = parent1.length();

        if (parent1.length() != parent2.length()) {
            length = Math.min(parent1.length(), parent2.length());
        }

        final double[] offspringValue1 = new double[length];
        final double[] offspringValue2 = new double[length];
        final double[] parent1Value = parent1.value();
        final double[] parent2Value = parent2.value();

        for (int i = 0; i < length; i++) {
            if (random.nextBoolean()) {
                offspringValue1[i] = parent1Value[i];
                offspringValue2[i] = parent2Value[i];
            } else {
                offspringValue1[i] = parent2Value[i];
                offspringValue2[i] = parent1Value[i];
            }
        }

        return Pair.of(
                factory.individual(new FloatingPointChromosome(offspringValue1)),
                factory.individual(new FloatingPointChromosome(offspringValue2)));
    }

    @Override
    public String toString() {
        return "Floating point uniform cross over";
    }
}
