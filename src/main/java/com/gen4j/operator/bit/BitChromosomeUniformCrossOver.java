package com.gen4j.operator.bit;

import static java.util.Arrays.asList;
import static java.util.Collections.unmodifiableList;

import java.util.BitSet;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import com.gen4j.chromosome.bit.BitChromosome;
import com.gen4j.chromosome.code.ChromosomeCodeType;
import com.gen4j.factory.GeneticAlgorithmFactory;
import com.gen4j.operator.AbstractGeneticOperator;
import com.gen4j.population.Individual;

public class BitChromosomeUniformCrossOver extends AbstractGeneticOperator<BitChromosome> {

    public BitChromosomeUniformCrossOver() {
        super(0.25, 2, ChromosomeCodeType.BIT);
    }

    @Override
    public List<Individual<BitChromosome>> apply(final Collection<Individual<BitChromosome>> individuals,
            final GeneticAlgorithmFactory<BitChromosome> factory) {

        final Iterator<Individual<BitChromosome>> iterator = individuals.iterator();
        return apply(iterator.next().chromosome(), iterator.next().chromosome(), factory);
    }

    private List<Individual<BitChromosome>> apply(final BitChromosome parent1, final BitChromosome parent2,
            final GeneticAlgorithmFactory<BitChromosome> factory) {

        final BitSet parent1Value = parent1.value();
        final BitSet parent2Value = parent2.value();

        final int length = parent1.length();
        final BitSet offspring1Value = new BitSet(length);
        final BitSet offspring2Value = new BitSet(length);

        for (int i = 0; i < length; i++) {
            if (random.nextBoolean()) {
                offspring1Value.set(i, parent1Value.get(i));
                offspring2Value.set(i, parent2Value.get(i));
            } else {
                offspring1Value.set(i, parent2Value.get(i));
                offspring2Value.set(i, parent1Value.get(i));
            }
        }

        return unmodifiableList(asList(
                factory.individual(new BitChromosome(offspring1Value, length)),
                factory.individual(new BitChromosome(offspring2Value, length))));
    }

    @Override
    public String toString() {
        return "Uniform bit-chromosome cross over";
    }
}
