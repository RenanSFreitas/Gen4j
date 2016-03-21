package com.gen4j.genotype;

import com.gen4j.phenotype.Phenotype;

public interface GenotypeEncoder<G extends Genotype, K> {

    Phenotype<K> decode(G genotype);

    G encode(Phenotype<K> phenotype);
}
