package com.gen4j.operator.bit;

import java.util.BitSet;
import java.util.function.Function;

import com.gen4j.chromosome.bit.BitChromosome;
import com.gen4j.chromosome.code.ChromosomeCodeType;
import com.gen4j.operator.AbstractGeneticOperator;
import com.gen4j.operator.CrossOver;
import com.gen4j.population.Individual;
import com.gen4j.utils.Pair;

public final class BitChromosomeCrossOver extends AbstractGeneticOperator<BitChromosome>
        implements CrossOver<BitChromosome> {

    public BitChromosomeCrossOver() {
        super(0.25, ChromosomeCodeType.BIT);
    }

    @Override
    public Pair<Individual<BitChromosome>, Individual<BitChromosome>> apply(
            final Pair<Individual<BitChromosome>, Individual<BitChromosome>> parents,
            final Function<BitChromosome, Individual<BitChromosome>> toIndividual) {

        final int crossOverPoint = random.nextInt(parents.first().chromosome().length());
        return cross(crossOverPoint, parents.first().chromosome(), parents.second().chromosome(), toIndividual);
    }

    private Pair<Individual<BitChromosome>, Individual<BitChromosome>> cross(final int crossOverPoint,
            final BitChromosome parent1, final BitChromosome parent2,
            final Function<BitChromosome, Individual<BitChromosome>> toIndividual) {
        if (parent1.equals(parent2)) {
            return Pair.of(toIndividual.apply(parent1), toIndividual.apply(parent2));
        }
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
        return Pair.of(
                toIndividual.apply(new BitChromosome(offspring1, length)),
                toIndividual.apply(new BitChromosome(offspring2, length)));
    }

    @Override
    public String toString() {
        return "Single point bit-chromosome cross over";
    }
}
