package com.gen4j.factory;

import com.gen4j.fitness.FitnessFunction;
import com.gen4j.genotype.GenotypeEncoder;
import com.gen4j.genotype.bit.BitSetGenotype;

public class BitGeneticAlgorithmFactory<I extends Enum<I>> extends AbstractGeneticAlgorithmFactory<BitSetGenotype, I> {

    @Override
    public GenotypeEncoder<BitSetGenotype, I> encoder() {
        return null;
    }

    @Override
    protected FitnessFunction<BitSetGenotype> internalFitnessFunction() {
        // TODO Auto-generated method stub
        return null;
    }

}
