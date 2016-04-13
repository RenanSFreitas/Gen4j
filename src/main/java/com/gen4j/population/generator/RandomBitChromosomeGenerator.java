package com.gen4j.population.generator;

import java.util.BitSet;
import java.util.Random;

import com.gen4j.chromosome.bit.BitChromosome;

public class RandomBitChromosomeGenerator implements ChromosomeGenerator<BitChromosome>
{
    private static final Random RANDOM = new Random(System.nanoTime());

    @Override
    public BitChromosome generate(int length)
    {
        BitSet bits = new BitSet(length);
        for (int i = 0; i < length; i++)
        {
            bits.set(i, RANDOM.nextBoolean());
        }
        return new BitChromosome(bits, length);
    }

}
