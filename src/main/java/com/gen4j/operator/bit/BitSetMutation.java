package com.gen4j.operator.bit;

import static java.util.Objects.requireNonNull;

import java.util.BitSet;
import java.util.Random;

import com.gen4j.genotype.bit.BitSetGenotype;
import com.gen4j.operator.Mutation;
import com.google.common.base.Preconditions;

public final class BitSetMutation implements Mutation<BitSetGenotype> {

    private final Random random = new Random(System.nanoTime());

    private double probability = DEFAULT_PROBABILITY;

    @Override
    public BitSetGenotype mutate(final BitSetGenotype genotype) {

        final BitSetGenotype mutant = new BitSetGenotype(requireNonNull(genotype));
        final BitSet bits = mutant.value();
        bits.flip(random.nextInt(mutant.length()));
        return mutant;
    }

    @Override
    public double probability() {
        return probability;
    }

    @Override
    public void probability(final double probability) {
        Preconditions.checkArgument(probability > 0d && probability < 1d);
        this.probability = probability;
    }

}
