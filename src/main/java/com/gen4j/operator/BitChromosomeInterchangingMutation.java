package com.gen4j.operator;

import java.util.BitSet;

import com.gen4j.chromosome.bit.BitChromosome;
import com.gen4j.chromosome.code.ChromosomeCodeType;
import com.gen4j.factory.GeneticAlgorithmFactory;
import com.gen4j.population.Individual;

public final class BitChromosomeInterchangingMutation extends AbstractGeneticOperator<BitChromosome>
        implements Mutation<BitChromosome> {

    public BitChromosomeInterchangingMutation() {
        super(0.01, 1, ChromosomeCodeType.BIT);
    }

    @Override
    public Individual<BitChromosome> apply(final Individual<BitChromosome> individual,
            final GeneticAlgorithmFactory<BitChromosome> factory, final int generationCount) {

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
        return factory.individual(mutant);
    }

    @Override
    public String toString() {
        return "Interchanging bit-chromosome mutation";
    }
}
