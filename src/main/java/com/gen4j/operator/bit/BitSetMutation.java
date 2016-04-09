package com.gen4j.operator.bit;

import static com.google.common.collect.Iterables.getOnlyElement;

import java.util.BitSet;
import java.util.Collection;
import java.util.Collections;
import java.util.Random;

import com.gen4j.genotype.bit.BitSetGenotype;
import com.gen4j.operator.GeneticOperator;
import com.google.common.base.Preconditions;

public final class BitSetMutation implements GeneticOperator<BitSetGenotype> {

    private final Random random = new Random(System.nanoTime());

    private double probability = 0.01;

    @Override
    public int chromosomeCount() {
        return 1;
    }

    @Override
    public Collection<BitSetGenotype> apply(final Collection<BitSetGenotype> genotypes) {

        final BitSetGenotype mutant = new BitSetGenotype(getOnlyElement(genotypes));
        final BitSet bits = mutant.value();
        bits.flip(random.nextInt(mutant.length()));
        return Collections.singletonList(mutant);
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
