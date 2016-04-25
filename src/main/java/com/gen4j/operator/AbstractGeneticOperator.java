package com.gen4j.operator;

import static com.google.common.base.Preconditions.checkArgument;
import static java.util.Objects.requireNonNull;

import java.util.Random;

import com.gen4j.chromosome.Chromosome;
import com.gen4j.chromosome.code.ChromosomeCodeType;

public abstract class AbstractGeneticOperator<C extends Chromosome> implements GeneticOperator<C> {

    protected final Random random = new Random(System.nanoTime());

    private double probability;

    private final ChromosomeCodeType codeType;

    public AbstractGeneticOperator(final double probability, final ChromosomeCodeType codeType) {

        checkArgument(probability > 0d && probability < 1d);

        this.probability = probability;
        this.codeType = requireNonNull(codeType);
    }

    @Override
    public double probability() {
        return probability;
    }

    @Override
    public void probability(final double probability) {
        checkArgument(probability > 0d && probability < 1d);
        this.probability = probability;
    }

    @Override
    public final ChromosomeCodeType chromosomeCodeType() {
        return codeType;
    }

}
