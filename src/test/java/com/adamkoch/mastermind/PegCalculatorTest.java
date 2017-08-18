package com.adamkoch.mastermind;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * <p>Created by aakoch on 2017-08-12.</p>
 *
 * @author aakoch
 * @since 1.0.0
 */
class PegCalculatorTest {

    @Test
    void calculateSameColor() {
        assertEquals(2, PegCalculator.calculateSameColor(Arrays.asList(Peg.RED, Peg.WHITE), Arrays.asList(Peg.RED,
                Peg.WHITE)));
        assertEquals(3, PegCalculator.calculateSameColor(Arrays.asList(Peg.RED, Peg.RED, Peg.WHITE, Peg.BLUE),
                Arrays.asList(Peg.RED, Peg.WHITE, Peg.BLUE, Peg.BLUE)));
    }

    @Test
    void calculateSameColorAndSamePlace() {
        assertEquals(1,
                PegCalculator.calculateSameColorAndSamePlace(Arrays.asList(Peg.RED, Peg.RED, Peg.WHITE, Peg.WHITE),
                        Arrays.asList(Peg.RED, Peg.WHITE, Peg.BLUE, Peg.BLUE)));
        assertEquals(2,
                PegCalculator.calculateSameColorAndSamePlace(Arrays.asList(Peg.RED, Peg.WHITE), Arrays.asList(Peg.RED,
                        Peg.WHITE)));
        assertEquals(3,
                PegCalculator.calculateSameColorAndSamePlace(Arrays.asList(Peg.RED, Peg.BLUE, Peg.WHITE, Peg.WHITE),
                        Arrays.asList(Peg.RED, Peg.BLUE, Peg.BLUE, Peg.WHITE)));
    }

}