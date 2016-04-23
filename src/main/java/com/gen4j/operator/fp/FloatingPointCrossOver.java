package com.gen4j.operator.fp;

import static java.util.Arrays.asList;
import static java.util.Collections.unmodifiableList;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import com.gen4j.chromosome.ChromosomeCoder;
import com.gen4j.chromosome.code.ChromosomeCodeType;
import com.gen4j.chromosome.fp.FloatingPointChromosome;
import com.gen4j.factory.GeneticAlgorithmFactory;
import com.gen4j.fitness.FitnessFunction;
import com.gen4j.operator.AbstractGeneticOperator;
import com.gen4j.population.Individual;
import com.gen4j.population.generic.GenericIndividual;
import com.google.common.base.Preconditions;

public final class FloatingPointCrossOver extends AbstractGeneticOperator<FloatingPointChromosome> {

    public FloatingPointCrossOver() {
        super(0.65, 2, ChromosomeCodeType.FLOATING_POINT);
    }

    @Override
    public List<Individual<FloatingPointChromosome>> apply(
            final Collection<Individual<FloatingPointChromosome>> individuals,
            final GeneticAlgorithmFactory<FloatingPointChromosome> factory) {
        Preconditions.checkArgument(individuals.size() == 2);
        final Iterator<Individual<FloatingPointChromosome>> iterator = individuals.iterator();
        return apply(iterator.next().chromosome(), iterator.next().chromosome(), factory);
    }

    private List<Individual<FloatingPointChromosome>> apply(final FloatingPointChromosome parent1,
            final FloatingPointChromosome parent2,
            final GeneticAlgorithmFactory<FloatingPointChromosome> factory) {

        final double[] parent1Value = parent1.value();
        final double[] parent2Value = parent2.value();

        final int chromosomeValueLength = parent1Value.length;
        final int crossOverPoint = random.nextInt(chromosomeValueLength);

        final double[] offspringValue1 = new double[chromosomeValueLength];
        final double[] offspringValue2 = new double[chromosomeValueLength];

        System.arraycopy(parent1Value, 0, offspringValue1, 0, crossOverPoint);
        System.arraycopy(parent2Value, 0, offspringValue2, 0, crossOverPoint);

        final int length = chromosomeValueLength - crossOverPoint;

        System.arraycopy(parent1Value, crossOverPoint, offspringValue2, crossOverPoint, length);
        System.arraycopy(parent2Value, crossOverPoint, offspringValue1, crossOverPoint, length);

        final FitnessFunction fitnessFunction = factory.fitnessFunction();
        final ChromosomeCoder<FloatingPointChromosome> coder = factory.coder();
        final GenericIndividual<FloatingPointChromosome> offspring1 = new GenericIndividual<>(
                new FloatingPointChromosome(offspringValue1), fitnessFunction, coder);
        final GenericIndividual<FloatingPointChromosome> offspring2 = new GenericIndividual<>(
                new FloatingPointChromosome(offspringValue2), fitnessFunction, coder);

        return unmodifiableList(asList(offspring1, offspring2));
    }

    @Override
    public String toString() {
        return "Single poit floating-point-chromosome cross over";
    }
}
