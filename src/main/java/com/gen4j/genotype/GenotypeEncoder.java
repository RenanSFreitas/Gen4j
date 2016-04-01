package com.gen4j.genotype;

import com.gen4j.phenotype.Phenotype;

public interface GenotypeEncoder<G extends Genotype, V> {

    Phenotype<V> decode(G genotype);

    G encode(Phenotype<V> phenotype);
}
