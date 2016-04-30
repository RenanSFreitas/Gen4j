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

public class FloatingPointNonUniformMutation extends AbstractGeneticOperator<FloatingPointChromosome> {

    private final int maxGenerations;

    public FloatingPointNonUniformMutation(final int maxGenerations) {
        super(0.25, 1, ChromosomeCodeType.FLOATING_POINT);
        this.maxGenerations = maxGenerations;
    }

    @Override
    public List<Individual<FloatingPointChromosome>> apply(
            final Collection<Individual<FloatingPointChromosome>> individuals,
            final GeneticAlgorithmFactory<FloatingPointChromosome> factory, final int generationCount) {

        final List<Individual<FloatingPointChromosome>> result = new ArrayList<>();

        for (final Individual<FloatingPointChromosome> individual : individuals) {
            final double[] originalValue = individual.chromosome().value();
            final int length = originalValue.length;
            final double[] chromosomeValue = new double[length];
            for (int i = 0; i < chromosomeValue.length; i++) {
                if (random.nextDouble() >= probability()) {
                    // Just copies the original value
                    chromosomeValue[i] = originalValue[i];
                } else {
                    chromosomeValue[i] = mutate(originalValue[i], generationCount, factory.coder().range());
                }
            }
            result.add(factory.individual(new FloatingPointChromosome(chromosomeValue)));
        }

        return result;
    }

    private double mutate(final double originalValue, final int generationCount, final Range range) {
        if (random.nextBoolean()) {
            return originalValue + delta(generationCount, range.upperBound() - originalValue);
        }
        return originalValue - delta(generationCount, originalValue - range.lowerBound());
    }

    /**
     * Delta function implemented as defined by Michalewicz (1996, p.112) in
     * "Evolution Programs", 3rd edition.
     * <p>
     *
     * y * ( (1-r ^ ((1-t/T)^b) )
     *
     * @param generationCount
     * @param y
     * @return
     */
    private double delta(final int generationCount, final double y) {
        return y * (1 - Math.pow(random.nextDouble(), Math.pow((1 - generationCount / maxGenerations), 2)));
    }

    @Override
    public String toString() {
        return "Floating-point-chromosome non uniform mutation";
    }

}