package com.gen4j.operator.bit;

import java.util.BitSet;

import com.gen4j.chromosome.bit.BitChromosome;
import com.gen4j.chromosome.code.ChromosomeCodeType;
import com.gen4j.factory.GeneticAlgorithmFactory;
import com.gen4j.operator.AbstractGeneticOperator;
import com.gen4j.operator.Mutation;
import com.gen4j.population.Individual;

public final class BitFlipMutation extends AbstractGeneticOperator<BitChromosome> implements Mutation<BitChromosome> {

    public BitFlipMutation() {
        super(0.01, 1, ChromosomeCodeType.BIT);
    }

    @Override
    public Individual<BitChromosome> apply(final Individual<BitChromosome> individual,
            final GeneticAlgorithmFactory<BitChromosome> factory, final int generationCount) {

        final BitChromosome mutant = new BitChromosome(individual.chromosome());
        final BitSet bits = mutant.value();
        for (int i = 0; i < bits.length(); i++) {
            if (random.nextDouble() < probability()) {
                bits.flip(i);
            }
        }
        return factory.individual(mutant);
    }

    @Override
    public String toString() {
        return "Bit flip mutation";
    }
}
