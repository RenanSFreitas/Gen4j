package com.gen4j.operator.bit;

import static com.google.common.base.Preconditions.checkArgument;
import static java.util.Arrays.asList;
import static java.util.Collections.unmodifiableList;

import java.util.BitSet;
import java.util.Collection;
import java.util.Iterator;
import java.util.Random;

import com.gen4j.chromosome.bit.BitChromosome;
import com.gen4j.operator.GeneticOperator;
import com.google.common.base.Preconditions;

public final class BitSetCrossOver implements GeneticOperator<BitChromosome> {

    private final Random random = new Random(System.nanoTime());

    private double probability = 0.25;

    @Override
    public int chromosomeCount() {
        return 2;
    }

    @Override
    public Collection<BitChromosome> apply(final Collection<BitChromosome> chromosomes) {

        Preconditions.checkArgument(chromosomes.size() == 2);
        final Iterator<BitChromosome> iterator = chromosomes.iterator();
        return apply(iterator.next(), iterator.next());
    }

    private Collection<BitChromosome> apply(final BitChromosome g1, final BitChromosome g2) {

        final int length = g1.length();
        checkArgument(length == g2.length(), "Genotypes must have the same size");

        // TODO maybe it will be good to generated a batch of random ints
        // and generated them again when the batch is out of new ints
        final int crossOverPoint = random.nextInt(length);
        final BitChromosome offspring1 = new BitChromosome(cross(crossOverPoint, g1, g2), length);
        final BitChromosome offspring2 = new BitChromosome(cross(crossOverPoint, g2, g1), length);
        return unmodifiableList(asList(offspring1, offspring2));
    }

    private BitSet cross(final int crossOverPoint, final BitChromosome g1, final BitChromosome g2) {
        final int length = g1.length();
        final BitSet bitSet = new BitSet(length);
        for (int i = 0; i < crossOverPoint; i++) {
            bitSet.set(i, g1.value().get(i));
        }
        for (int i = crossOverPoint; i < length; i++) {
            bitSet.set(i, g2.value().get(i));
        }
        return bitSet;
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
