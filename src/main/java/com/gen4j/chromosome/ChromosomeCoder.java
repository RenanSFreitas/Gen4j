package com.gen4j.chromosome;

import java.util.List;

import com.gen4j.phenotype.Phenotype;

/**
 * A {@link ChromosomeCoder} is responsible for both decoding and encoding
 * tasks. Those tasks are, basically, conversion tasks from {@link Chromosome}s
 * to {@link Phenotype}s and the opposite.
 *
 * @param <C>
 *            the {@link Chromosome} specialization
 */
public interface ChromosomeCoder<C extends Chromosome> {

    /**
     * Converts a given {@link Chromosome} into its {@link Phenotype phenotypic}
     * representation.
     *
     * @param chromosome
     *            the chromosome to be converted
     * @return the phenotypic representation
     */
    Phenotype decode(C chromosome);

    /**
     * Converts a given {@link Phenotype} into its {@link Chromosome
     * chromosomic} representation.
     *
     * @param phenotype
     *            the phenotype to be converted
     * @return the chromosomic representation
     */
    C encode(Phenotype phenotype);

    /**
     * Returns the length of chromosomes created from this coder.
     */
    int chromosomeLength();

    /**
     * Returns the range of the variable represented in the chromosome.
     *
     */
    Range range(int geneIndex);

    /**
     * Returns the ranges of variables represented in the chromosome.
     *
     */
    List<Range> ranges();
}
