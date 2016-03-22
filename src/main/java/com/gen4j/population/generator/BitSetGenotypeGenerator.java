package com.gen4j.population.generator;

import java.util.BitSet;
import java.util.Random;

import com.gen4j.genotype.bit.BitSetGenotype;

public class BitSetGenotypeGenerator implements RandomGenotypeGenerator<BitSetGenotype>
{
    private static final Random RANDOM = new Random(System.nanoTime());

    @Override
    public BitSetGenotype generate(int length)
    {
        BitSet bits = new BitSet(length);
        for (int i = 0; i < length; i++)
        {
            bits.set(i, RANDOM.nextBoolean());
        }
        return new BitSetGenotype(bits, length);
    }

}
