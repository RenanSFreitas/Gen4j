/**
 *
 */
package com.gen4j.population.generator;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Random;

import com.gen4j.chromosome.Range;
import com.gen4j.chromosome.fp.FloatingPointChromosome;

public class RandomFloatingPointChromosomeGenerator implements ChromosomeGenerator<FloatingPointChromosome> {

    private final double lowerBound;
    private final Random random = new Random(System.nanoTime());
    private final double domainLength;

    public RandomFloatingPointChromosomeGenerator(final Range optimizationRange) {
        checkNotNull(optimizationRange);
        lowerBound = optimizationRange.lowerBound();
        domainLength = optimizationRange.length();
    }

    @Override
    public FloatingPointChromosome generate(final int length) {
        final double[] chromosomeValue = new double[length];
        for(int i = 0; i < chromosomeValue.length; i++ ) {
            chromosomeValue[i] = random.nextDouble() * domainLength + lowerBound;
        }
        return new FloatingPointChromosome(chromosomeValue);
    }

}
