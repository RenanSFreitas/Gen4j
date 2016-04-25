package com.gen4j.operator.bit;

import java.util.BitSet;
import java.util.function.Function;

import com.gen4j.chromosome.bit.BitChromosome;
import com.gen4j.chromosome.code.ChromosomeCodeType;
import com.gen4j.operator.AbstractGeneticOperator;
import com.gen4j.operator.CrossOver;
import com.gen4j.population.Individual;
import com.gen4j.utils.Pair;

public final class BitChromosomeUniformCrossOver extends AbstractGeneticOperator<BitChromosome>
        implements CrossOver<BitChromosome> {

    public BitChromosomeUniformCrossOver() {
        super(0.25, ChromosomeCodeType.BIT);
    }

    @Override
    public Pair<Individual<BitChromosome>, Individual<BitChromosome>> apply(
            final Pair<Individual<BitChromosome>, Individual<BitChromosome>> parents,
            final Function<BitChromosome, Individual<BitChromosome>> toIndividual) {

        final BitChromosome parent1Chromosome = parents.first().chromosome();
        final BitSet parent1Value = parent1Chromosome.value();
        final BitSet parent2Value = parents.second().chromosome().value();

        final int length = parent1Chromosome.length();
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

        return Pair.of(
                toIndividual.apply(new BitChromosome(offspring1Value, length)),
                toIndividual.apply(new BitChromosome(offspring2Value, length)));
    }

    @Override
    public String toString() {
        return "Uniform bit-chromosome cross over";
    }
}
