package com.gen4j.operator.bit;

import static java.util.Arrays.asList;
import static java.util.Collections.unmodifiableList;

import java.util.BitSet;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import com.gen4j.chromosome.bit.BitChromosome;
import com.gen4j.factory.GeneticAlgorithmFactory;
import com.gen4j.operator.GeneticOperator;
import com.gen4j.population.Individual;
import com.google.common.base.Preconditions;

public final class BitSetCrossOver implements GeneticOperator<BitChromosome> {

    private final Random random = new Random(System.nanoTime());

    private double probability = 0.25;

    private static class OffspringBitSets {
        BitSet offspring1;
        BitSet offspring2;

        public OffspringBitSets(final BitSet offspring1, final BitSet offspring2) {
            this.offspring1 = offspring1;
            this.offspring2 = offspring2;
        }
    }

    @Override
    public int chromosomeCount() {
        return 2;
    }

    @Override
    public List<Individual<BitChromosome>> apply(final Collection<Individual<BitChromosome>> individuals,
            final GeneticAlgorithmFactory<BitChromosome> factory) {

        Preconditions.checkArgument(individuals.size() == 2);
        final Iterator<Individual<BitChromosome>> iterator = individuals.iterator();
        return apply(iterator.next().chromosome(), iterator.next().chromosome(), factory);
    }

    private List<Individual<BitChromosome>> apply(final BitChromosome g1, final BitChromosome g2,
            final GeneticAlgorithmFactory<BitChromosome> factory) {

        final int length = g1.length();

        final int crossOverPoint = random.nextInt(length);
        final OffspringBitSets bitSets = cross(crossOverPoint, g1, g2);
        final BitChromosome offspring1 = new BitChromosome(bitSets.offspring1, length);
        final BitChromosome offspring2 = new BitChromosome(bitSets.offspring2, length);

        return unmodifiableList(asList(factory.individual(offspring1), factory.individual(offspring2)));
    }

    private OffspringBitSets cross(final int crossOverPoint, final BitChromosome parent1, final BitChromosome parent2) {
        final int length = parent1.length();
        final BitSet offspring1 = new BitSet(length);
        final BitSet offspring2 = new BitSet(length);
        final BitSet p1 = parent1.value();
        final BitSet p2 = parent2.value();
        offspring1.or(p1);
        offspring2.or(p2);
        for (int i = crossOverPoint; i < length; i++) {
            offspring1.set(i, p2.get(i));
            offspring2.set(i, p1.get(i));
        }
        return new OffspringBitSets(offspring1, offspring2);
    }

    private OffspringBitSets cross0(final int crossOverPoint, final BitChromosome parent1, final BitChromosome parent2) {
        final int length = parent1.length();
        final BitSet offspring1 = new BitSet(length);
        final BitSet offspring2 = new BitSet(length);
        final BitSet p1 = parent1.value();
        final BitSet p2 = parent2.value();
        for (int i = 0; i < crossOverPoint; i++) {
            offspring1.set(i, p1.get(i));
            offspring2.set(i, p2.get(i));
        }
        for (int i = crossOverPoint; i < length; i++) {
            offspring1.set(i, p2.get(i));
            offspring2.set(i, p1.get(i));
        }
        return new OffspringBitSets(offspring1, offspring2);
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
