package com.gen4j.operator.fp;

import java.util.function.Function;

import com.gen4j.chromosome.Range;
import com.gen4j.chromosome.code.ChromosomeCodeType;
import com.gen4j.chromosome.fp.FloatingPointChromosome;
import com.gen4j.operator.AbstractGeneticOperator;
import com.gen4j.operator.CrossOver;
import com.gen4j.population.Individual;
import com.gen4j.utils.Pair;
import com.google.common.math.DoubleMath;

public final class FloatingPointCrossOverHuang1997 extends AbstractGeneticOperator<FloatingPointChromosome>
        implements CrossOver<FloatingPointChromosome> {

    public FloatingPointCrossOverHuang1997() {
        super(0.25, ChromosomeCodeType.FLOATING_POINT);
    }

    @Override
    public Pair<Individual<FloatingPointChromosome>, Individual<FloatingPointChromosome>> apply(
            final Pair<Individual<FloatingPointChromosome>, Individual<FloatingPointChromosome>> parents,
            final Function<FloatingPointChromosome, Individual<FloatingPointChromosome>> toIndividual) {

        final double[] parent1Value = parents.first().chromosome().value();
        final double[] parent2Value = parents.second().chromosome().value();

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

        final Range range = parents.first().chromosome().range();
        return Pair.of(toIndividual.apply(new FloatingPointChromosome(offspringValue1, range)),
                toIndividual.apply(new FloatingPointChromosome(offspringValue2, range)));
    }

    @Override
    public String toString() {
        return "Single poit floating-point-chromosome cross over (Huang, 1997)";
    }
}
