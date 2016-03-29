package com.gen4j.operator;

import com.gen4j.genotype.Genotype;
import com.gen4j.utils.Pair;

public interface CrossOver<G extends Genotype> extends GeneticOperator {

    double DEFAULT_PROBABILITY = 0.7;

    Pair<G, G> apply(G g1, G g2);
}
