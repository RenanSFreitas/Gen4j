package com.gen4j.operator.fp;

import java.util.function.Function;

import com.gen4j.chromosome.Range;
import com.gen4j.chromosome.code.ChromosomeCodeType;
import com.gen4j.chromosome.fp.FloatingPointChromosome;
import com.gen4j.operator.AbstractGeneticOperator;
import com.gen4j.operator.CrossOver;
import com.gen4j.population.Individual;
import com.gen4j.utils.Pair;

public final class FloatingPointUniformCrossOver extends AbstractGeneticOperator<FloatingPointChromosome>
        implements CrossOver<FloatingPointChromosome> {

    public FloatingPointUniformCrossOver() {
        super(0.25, ChromosomeCodeType.FLOATING_POINT);
    }

    @Override
    public Pair<Individual<FloatingPointChromosome>, Individual<FloatingPointChromosome>> apply(
            final Pair<Individual<FloatingPointChromosome>, Individual<FloatingPointChromosome>> parents,
            final Function<FloatingPointChromosome, Individual<FloatingPointChromosome>> toIndividual) {

        final int length = parents.first().chromosome().length();
        final double[] offspringValue1 = new double[length];
        final double[] offspringValue2 = new double[length];
        final double[] parent1Value = parents.first().chromosome().value();
        final double[] parent2Value = parents.second().chromosome().value();

        for (int i = 0; i < length; i++) {
            if (random.nextBoolean()) {
                offspringValue1[i] = parent1Value[i];
                offspringValue2[i] = parent2Value[i];
            } else {
                offspringValue1[i] = parent2Value[i];
                offspringValue2[i] = parent1Value[i];
            }
        }

        final Range range = parents.first().chromosome().range();
        return Pair.of(
                toIndividual.apply(new FloatingPointChromosome(offspringValue1, range)),
                toIndividual.apply(new FloatingPointChromosome(offspringValue2, range)));
    }

    @Override
    public String toString() {
        return "Floating point uniform cross over";
    }
}
