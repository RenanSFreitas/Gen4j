package com.gen4j.operator.bit;

import java.util.BitSet;

import com.gen4j.chromosome.bit.BitChromosome;
import com.gen4j.chromosome.code.ChromosomeCodeType;
import com.gen4j.factory.GeneticAlgorithmFactory;
import com.gen4j.operator.AbstractGeneticOperator;
import com.gen4j.operator.CrossOver;
import com.gen4j.population.Individual;
import com.gen4j.utils.Pair;

public final class BitChromosomeSinglePointCrossOver extends AbstractGeneticOperator<BitChromosome>
        implements CrossOver<BitChromosome> {

    private static class OffspringBitSets {
        BitSet offspring1;
        BitSet offspring2;

        public OffspringBitSets(final BitSet offspring1, final BitSet offspring2) {
            this.offspring1 = offspring1;
            this.offspring2 = offspring2;
        }
    }

    public BitChromosomeSinglePointCrossOver() {
        super(0.25, 2, ChromosomeCodeType.BIT);
    }

    @Override
    public Pair<Individual<BitChromosome>, Individual<BitChromosome>> apply(
            final Pair<Individual<BitChromosome>, Individual<BitChromosome>> parents,
            final GeneticAlgorithmFactory<BitChromosome> factory, final int generationCount) {

        return apply(parents.first().chromosome(), parents.second().chromosome(), factory);
    }

    private Pair<Individual<BitChromosome>, Individual<BitChromosome>> apply(final BitChromosome g1,
            final BitChromosome g2,
            final GeneticAlgorithmFactory<BitChromosome> factory) {

        final int length = g1.length();

        final int crossOverPoint = random.nextInt(length);
        final OffspringBitSets bitSets = cross(crossOverPoint, g1, g2);
        final BitChromosome offspring1 = new BitChromosome(bitSets.offspring1, length);
        final BitChromosome offspring2 = new BitChromosome(bitSets.offspring2, length);

        return Pair.of(factory.individual(offspring1), factory.individual(offspring2));
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

    @Override
    public String toString() {
        return "Single point bit-chromosome cross over";
    }
}
