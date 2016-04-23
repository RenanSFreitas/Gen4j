package com.gen4j.operator.fp;

import static com.google.common.collect.Iterables.getOnlyElement;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.gen4j.chromosome.Range;
import com.gen4j.chromosome.code.ChromosomeCodeType;
import com.gen4j.chromosome.fp.FloatingPointChromosome;
import com.gen4j.factory.GeneticAlgorithmFactory;
import com.gen4j.operator.AbstractGeneticOperator;
import com.gen4j.population.Individual;

public final class FloatingPointMutation extends AbstractGeneticOperator<FloatingPointChromosome> {

    public FloatingPointMutation() {
        super(0.25, 1, ChromosomeCodeType.FLOATING_POINT);
    }

    @Override
    public List<Individual<FloatingPointChromosome>> apply(
            final Collection<Individual<FloatingPointChromosome>> individuals,
            final GeneticAlgorithmFactory<FloatingPointChromosome> factory) {

        final double[] originalValue = getOnlyElement(individuals).chromosome().value();
        final int length = originalValue.length;
        final double[] chromosomeValue = new double[length];
        System.arraycopy(originalValue, 0, chromosomeValue, 0, length);

        final Range range = factory.coder().range();
        chromosomeValue[random.nextInt(length)] = random.nextDouble() * range.length() + range.lowerBound();

        return Collections.singletonList(factory.individual(new FloatingPointChromosome(chromosomeValue)));
    }

    @Override
    public String toString() {
        return "Random float floating-point-chromosome mutation";
    }
}
