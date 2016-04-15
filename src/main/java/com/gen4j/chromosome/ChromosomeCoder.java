package com.gen4j.chromosome;

import com.gen4j.phenotype.Phenotype;

public interface ChromosomeCoder<G extends Chromosome, V> {

    Phenotype<V> decode(G chromosome);

    G encode(Phenotype<V> phenotype);

    int chromosomeLength();
}
