/**
 *
 */
package com.gen4j.population.generator;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import java.math.RoundingMode;
import java.util.List;
import java.util.Random;

import com.gen4j.chromosome.Range;
import com.gen4j.chromosome.fp.FloatingPointChromosome;
import com.google.common.collect.ImmutableList;
import com.google.common.math.DoubleMath;

public class RandomFloatingPointChromosomeGenerator implements ChromosomeGenerator<FloatingPointChromosome> {

    private final Random random = new Random(System.nanoTime());
    private final List<Range> optimizationRanges;
    private final RoundingMode roundingMode;

    public RandomFloatingPointChromosomeGenerator(final List<Range> optimizationRanges) {
        this(optimizationRanges, RoundingMode.UNNECESSARY);
    }

    public RandomFloatingPointChromosomeGenerator(final List<Range> optimizationRanges,
            final RoundingMode roundingMode) {

        checkNotNull(optimizationRanges);
        checkArgument(!optimizationRanges.isEmpty());
        this.optimizationRanges = ImmutableList.copyOf(optimizationRanges);
        this.roundingMode = checkNotNull(roundingMode);
    }

    @Override
    public FloatingPointChromosome generate(final int length) {
        final double[] chromosomeValue = new double[length];
        for(int i = 0; i < chromosomeValue.length; i++ ) {
            final Range range = optimizationRanges.get(i);
            final double value = random.nextDouble() * range.length() + range.lowerBound();
            if (RoundingMode.UNNECESSARY.equals(roundingMode)) {
                chromosomeValue[i] = value;
            } else {
                chromosomeValue[i] = DoubleMath.roundToLong(value, roundingMode);
            }
        }
        return new FloatingPointChromosome(chromosomeValue);
    }

}
