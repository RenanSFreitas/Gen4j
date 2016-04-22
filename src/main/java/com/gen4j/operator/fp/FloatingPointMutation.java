package com.gen4j.operator.fp;

import static com.google.common.collect.Iterables.getOnlyElement;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import com.gen4j.chromosome.Range;
import com.gen4j.chromosome.fp.FloatingPointChromosome;
import com.gen4j.factory.GeneticAlgorithmFactory;
import com.gen4j.operator.GeneticOperator;
import com.gen4j.population.Individual;
import com.google.common.base.Preconditions;

public class FloatingPointMutation implements GeneticOperator<FloatingPointChromosome> {

    private final Random random = new Random(System.nanoTime());
    private double probability;

    @Override
    public double probability() {
        return probability;
    }

    @Override
    public void probability(final double probability) {
        Preconditions.checkArgument(probability > 0d && probability < 1d);
        this.probability = probability;
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
    public int chromosomeCount() {
        return 1;
    }

}
