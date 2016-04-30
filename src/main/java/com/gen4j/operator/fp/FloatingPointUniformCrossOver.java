package com.gen4j.operator.fp;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import com.gen4j.chromosome.code.ChromosomeCodeType;
import com.gen4j.chromosome.fp.FloatingPointChromosome;
import com.gen4j.factory.GeneticAlgorithmFactory;
import com.gen4j.operator.AbstractGeneticOperator;
import com.gen4j.population.Individual;

public final class FloatingPointUniformCrossOver extends AbstractGeneticOperator<FloatingPointChromosome>
 {

    public FloatingPointUniformCrossOver() {
        super(0.25, 2, ChromosomeCodeType.FLOATING_POINT);
    }

    @Override
    public List<Individual<FloatingPointChromosome>> apply(
            final Collection<Individual<FloatingPointChromosome>> individuals,
            final GeneticAlgorithmFactory<FloatingPointChromosome> factory, int generationCount) {

        final Iterator<Individual<FloatingPointChromosome>> iterator = individuals.iterator();
        return apply(iterator.next().chromosome(), iterator.next().chromosome(), factory);
    }

    private List<Individual<FloatingPointChromosome>> apply(final FloatingPointChromosome parent1,
            final FloatingPointChromosome parent2, final GeneticAlgorithmFactory<FloatingPointChromosome> factory) {

        final int length = parent1.length();
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

        return Arrays.asList(
                factory.individual(new FloatingPointChromosome(offspringValue1)),
                factory.individual(new FloatingPointChromosome(offspringValue2)));
    }

    @Override
    public String toString() {
        return "Floating point uniform cross over";
    }
}
