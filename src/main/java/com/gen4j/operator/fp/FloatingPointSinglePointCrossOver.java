package com.gen4j.operator.fp;

import com.gen4j.chromosome.code.ChromosomeCodeType;
import com.gen4j.chromosome.fp.FloatingPointChromosome;
import com.gen4j.factory.GeneticAlgorithmFactory;
import com.gen4j.operator.AbstractGeneticOperator;
import com.gen4j.operator.CrossOver;
import com.gen4j.population.Individual;
import com.gen4j.utils.Pair;

public final class FloatingPointSinglePointCrossOver extends AbstractGeneticOperator<FloatingPointChromosome>
        implements CrossOver<FloatingPointChromosome> {

    public FloatingPointSinglePointCrossOver() {
        super(0.65, 2, ChromosomeCodeType.FLOATING_POINT);
    }

    @Override
    public Pair<Individual<FloatingPointChromosome>, Individual<FloatingPointChromosome>> apply(
            final Pair<Individual<FloatingPointChromosome>, Individual<FloatingPointChromosome>> parents,
            final GeneticAlgorithmFactory<FloatingPointChromosome> factory, final int generationCount) {
        return apply(parents.first().chromosome(), parents.second().chromosome(), factory);
    }

    private Pair<Individual<FloatingPointChromosome>, Individual<FloatingPointChromosome>> apply(
            final FloatingPointChromosome parent1,
            final FloatingPointChromosome parent2,
            final GeneticAlgorithmFactory<FloatingPointChromosome> factory) {

        final double[] parent1Value = parent1.value();
        final double[] parent2Value = parent2.value();

        final int chromosomeValueLength = parent1Value.length;
        final int crossOverPoint = random.nextInt(chromosomeValueLength);

        final double[] offspringValue1 = new double[chromosomeValueLength];
        final double[] offspringValue2 = new double[chromosomeValueLength];

        System.arraycopy(parent1Value, 0, offspringValue1, 0, crossOverPoint);
        System.arraycopy(parent2Value, 0, offspringValue2, 0, crossOverPoint);

        final int length = chromosomeValueLength - crossOverPoint;

        System.arraycopy(parent1Value, crossOverPoint, offspringValue2, crossOverPoint, length);
        System.arraycopy(parent2Value, crossOverPoint, offspringValue1, crossOverPoint, length);

        return Pair.of(
                factory.individual(new FloatingPointChromosome(offspringValue1)), 
                factory.individual(new FloatingPointChromosome(offspringValue1)));
    }

    @Override
    public String toString() {
        return "Single point floating-point-chromosome cross over";
    }
}
