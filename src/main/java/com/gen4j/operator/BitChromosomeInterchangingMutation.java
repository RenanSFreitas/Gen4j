package com.gen4j.operator;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collection;
import java.util.List;

import com.gen4j.chromosome.bit.BitChromosome;
import com.gen4j.chromosome.code.ChromosomeCodeType;
import com.gen4j.factory.GeneticAlgorithmFactory;
import com.gen4j.population.Individual;

public final class BitChromosomeInterchangingMutation extends AbstractGeneticOperator<BitChromosome> {

    public BitChromosomeInterchangingMutation() {
        super(0.01, 1, ChromosomeCodeType.BIT);
    }

    @Override
    public List<Individual<BitChromosome>> apply(final Collection<Individual<BitChromosome>> individuals,
            final GeneticAlgorithmFactory<BitChromosome> factory, final int generationCount) {

        final List<Individual<BitChromosome>> result = new ArrayList<>();
        for (final Individual<BitChromosome> individual : individuals) {
            final BitChromosome mutant = new BitChromosome(individual.chromosome());
            final BitSet bits = (BitSet) mutant.value().clone();
            for (int i = 0; i < bits.length(); i++) {
                if (random.nextDouble() < probability()) {
                    final int otherBitIndex = random.nextInt(bits.length());
                    final boolean otherBitValue = bits.get(otherBitIndex);
                    bits.set(otherBitIndex, bits.get(i));
                    bits.set(i, otherBitValue);
                }
            }
            result.add(factory.individual(mutant));
        }
        return result;
    }

    @Override
    public String toString() {
        return "Interchanging bit-chromosome mutation";
    }
}
