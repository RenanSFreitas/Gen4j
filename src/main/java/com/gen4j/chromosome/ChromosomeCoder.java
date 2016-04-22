package com.gen4j.chromosome;

import com.gen4j.coding.ChromosomeCodeType;
import com.gen4j.phenotype.Phenotype;

public interface ChromosomeCoder<C extends Chromosome> {

    Phenotype decode(C chromosome);

    C encode(Phenotype phenotype);

    int chromosomeLength();

    ChromosomeCodeType codeType();

    Range range();
}
