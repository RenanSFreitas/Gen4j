/**
 *
 */
package com.gen4j.population.generator;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Random;

import com.gen4j.chromosome.Range;
import com.gen4j.chromosome.fp.FloatingPointChromosome;

public class RandomFloatingPointChromosomeGenerator implements ChromosomeGenerator<FloatingPointChromosome> {

    private final Random random = new Random(System.nanoTime());
    private final Range range;

    public RandomFloatingPointChromosomeGenerator(final Range optimizationRange) {
        range = checkNotNull(optimizationRange);
    }

    @Override
    public FloatingPointChromosome generate(final int length) {
        final double[] chromosomeValue = new double[length];
        for(int i = 0; i < chromosomeValue.length; i++ ) {
            chromosomeValue[i] = random.nextDouble() * range.length() + range.lowerBound();
        }
        return new FloatingPointChromosome(chromosomeValue, range);
    }

}
