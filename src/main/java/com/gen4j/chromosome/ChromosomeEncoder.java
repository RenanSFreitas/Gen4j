package com.gen4j.chromosome;

import com.gen4j.phenotype.Phenotype;

public interface ChromosomeEncoder<G extends Chromosome, V> {

    Phenotype<V> decode(G chromosome);

    G encode(Phenotype<V> phenotype);
}
