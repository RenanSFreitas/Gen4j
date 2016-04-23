package com.gen4j.population;

final class PopulationStatisticsImpl implements PopulationStatistics {
    private final int populationSize;
    private final double avg;
    private final double std;
    private final double total;
    private final double min;
    private final double max;

    PopulationStatisticsImpl(final int populationSize, final double avg, final double std, final double total, final double min,
            final double max) {
        this.populationSize = populationSize;
        this.avg = avg;
        this.std = std;
        this.total = total;
        this.min = min;
        this.max = max;
    }

    @Override
    public double totalFitness() {
        return total;
    }

    @Override
    public int populationSize() {
        return populationSize;
    }

    @Override
    public double minFitness() {
        return min;
    }

    @Override
    public double meanFitness() {
        return avg;
    }

    @Override
    public double maxFitness() {
        return max;
    }

    @Override
    public double fitnessStandardDeviation() {
        return std;
    }
}