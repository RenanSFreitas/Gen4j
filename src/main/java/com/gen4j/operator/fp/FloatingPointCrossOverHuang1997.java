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
import com.google.common.base.Preconditions;
import com.google.common.math.DoubleMath;

public final class FloatingPointCrossOverHuang1997 extends AbstractGeneticOperator<FloatingPointChromosome>
 {

    public FloatingPointCrossOverHuang1997() {
        super(0.25, 2, ChromosomeCodeType.FLOATING_POINT);
    }

    @Override
    public List<Individual<FloatingPointChromosome>> apply(
            final Collection<Individual<FloatingPointChromosome>> individuals,
            final GeneticAlgorithmFactory<FloatingPointChromosome> factory) {

        Preconditions.checkArgument(individuals.size() == 2);
        final Iterator<Individual<FloatingPointChromosome>> iterator = individuals.iterator();
        return apply(iterator.next().chromosome(), iterator.next().chromosome(), factory);
    }

    private List<Individual<FloatingPointChromosome>> apply(final FloatingPointChromosome parent1,
            final FloatingPointChromosome parent2, final GeneticAlgorithmFactory<FloatingPointChromosome> factory) {

        final double[] parent1Value = parent1.value();
        final double[] parent2Value = parent2.value();

        final int chromosomeValueLength = parent1Value.length;
        final int crossOverPoint = random.nextInt(chromosomeValueLength);

        final double[] offspringValue1 = new double[chromosomeValueLength];
        final double[] offspringValue2 = new double[chromosomeValueLength];

        System.arraycopy(parent1Value, 0, offspringValue1, 0, crossOverPoint);
        System.arraycopy(parent2Value, 0, offspringValue2, 0, crossOverPoint);

        final int length = chromosomeValueLength - crossOverPoint - 1;

        if (length > 0) {
            System.arraycopy(parent1Value, crossOverPoint + 1, offspringValue2, crossOverPoint + 1, length);
            System.arraycopy(parent2Value, crossOverPoint + 1, offspringValue1, crossOverPoint + 1, length);
        }

        final double pow2indexPlus1 = Math.pow(2d, crossOverPoint + 1);
        final double d1 = parent1Value[crossOverPoint] % pow2indexPlus1;
        final double d2 = parent2Value[crossOverPoint] % pow2indexPlus1;

        if (!DoubleMath.fuzzyEquals(d1, d2, 0.000001)) {
            offspringValue1[crossOverPoint] = parent1Value[crossOverPoint] - d1 + d2;
            offspringValue2[crossOverPoint] = parent2Value[crossOverPoint] - d2 + d1;
        } else {
            offspringValue1[crossOverPoint] = parent1Value[crossOverPoint];
            offspringValue2[crossOverPoint] = parent2Value[crossOverPoint];
        }

        return Arrays.asList(
                factory.individual(new FloatingPointChromosome(offspringValue1)),
                factory.individual(new FloatingPointChromosome(offspringValue2)));
    }

    @Override
    public String toString() {
        return "Single poit floating-point-chromosome cross over (Huang, 1997)";
    }
}
