package com.gen4j.operator.fp;

import java.util.ArrayList;
import java.util.Collection;
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
            final GeneticAlgorithmFactory<FloatingPointChromosome> factory, int generationCount) {

        final List<Individual<FloatingPointChromosome>> result = new ArrayList<>();

        for (final Individual<FloatingPointChromosome> individual : individuals) {
            final double[] originalValue = individual.chromosome().value();
            final int length = originalValue.length;
            final double[] chromosomeValue = new double[length];
            final Range range = factory.coder().range();
            for (int i = 0; i < chromosomeValue.length; i++) {
                if (random.nextDouble() < probability()) {
                    chromosomeValue[i] = random.nextDouble() * range.length() + range.lowerBound();
                } else {
                    chromosomeValue[i] = originalValue[i];
                }
            }
            result.add(factory.individual(new FloatingPointChromosome(chromosomeValue)));
        }

        return result;
    }

    @Override
    public String toString() {
        return "Random float floating-point-chromosome mutation";
    }
}
