package com.gen4j.operator.bit;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collection;
import java.util.List;

import com.gen4j.chromosome.bit.BitChromosome;
import com.gen4j.chromosome.code.ChromosomeCodeType;
import com.gen4j.factory.GeneticAlgorithmFactory;
import com.gen4j.operator.AbstractGeneticOperator;
import com.gen4j.population.Individual;

public final class BitFlipMutation extends AbstractGeneticOperator<BitChromosome> {

    public BitFlipMutation() {
        super(0.01, 1, ChromosomeCodeType.BIT);
    }

    @Override
    public List<Individual<BitChromosome>> apply(final Collection<Individual<BitChromosome>> individuals,
            final GeneticAlgorithmFactory<BitChromosome> factory, int generationCount) {

        final List<Individual<BitChromosome>> result = new ArrayList<>();
        for (final Individual<BitChromosome> individual : individuals) {
            final BitChromosome mutant = new BitChromosome(individual.chromosome());
            final BitSet bits = (BitSet) mutant.value().clone();
            for (int i = 0; i < bits.length(); i++) {
                if (random.nextDouble() < probability()) {
                    bits.flip(i);
                }
            }
            result.add(factory.individual(mutant));
        }
        return result;
    }

    @Override
    public String toString() {
        return "Bit flip mutation";
    }
}
