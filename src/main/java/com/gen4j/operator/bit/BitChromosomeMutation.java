package com.gen4j.operator.bit;

import java.util.BitSet;
import java.util.function.Function;

import com.gen4j.chromosome.bit.BitChromosome;
import com.gen4j.chromosome.code.ChromosomeCodeType;
import com.gen4j.operator.AbstractGeneticOperator;
import com.gen4j.operator.Mutation;
import com.gen4j.population.Individual;

public final class BitChromosomeMutation extends AbstractGeneticOperator<BitChromosome>
        implements Mutation<BitChromosome> {

    public BitChromosomeMutation() {
        super(0.05, ChromosomeCodeType.BIT);
    }

    @Override
    public Individual<BitChromosome> apply(final Individual<BitChromosome> individual,
            final Function<BitChromosome, Individual<BitChromosome>> toIndividual) {

        final BitChromosome chromosome = individual.chromosome();
        final BitSet bits = new BitSet();
        bits.or(chromosome.value());
        final int length = chromosome.length();
        for (int j = 0; j < length; j++) {
            if (random.nextDouble() < probability()) {
                bits.flip(j);
            }
        }
        return toIndividual.apply(new BitChromosome(bits, length));
    }

    @Override
    public String toString() {
        return "Bit flip bit-chromosome mutation";
    }
}
