package com.gen4j.fitness;

import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.reset;
import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.easymock.annotation.Mock;
import org.powermock.modules.junit4.PowerMockRunner;

import com.gen4j.genotype.Genotype;

@RunWith(PowerMockRunner.class)
public class FitnessCacheTest {

    private FitnessCache<Genotype> subject;

    @Mock
    private FitnessFunction<Genotype> fitnessFunction;

    @Mock
    private Genotype genotype;

    @Before
    public void setUp() {
        subject = new FitnessCache<>(fitnessFunction);
    }

    @Test
    public void testFitnessCacheHitAndMiss() {

        final double fitnessValue = 1.5;
        final int missCount = 1;

        reset(fitnessFunction);
        expect(fitnessFunction.evaluate(eq(genotype))).andReturn(fitnessValue).times(missCount);
        replay(fitnessFunction);

        assertEquals(fitnessValue, subject.evaluate(genotype), 0);
        assertEquals(fitnessValue, subject.evaluate(genotype), 0);
        assertEquals(fitnessValue, subject.evaluate(genotype), 0);
    }

}
