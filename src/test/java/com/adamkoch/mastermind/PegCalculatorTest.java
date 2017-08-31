/*
 *             DO WHAT THE FUCK YOU WANT TO PUBLIC LICENSE
 *                     Version 2, December 2004
 *
 *  Copyright (C) 2004 Sam Hocevar <sam@hocevar.net>
 *
 *  Everyone is permitted to copy and distribute verbatim or modified
 *  copies of this license document, and changing it is allowed as long
 *  as the name is changed.
 *
 *             DO WHAT THE FUCK YOU WANT TO PUBLIC LICENSE
 *    TERMS AND CONDITIONS FOR COPYING, DISTRIBUTION AND MODIFICATION
 *
 *   0. You just DO WHAT THE FUCK YOU WANT TO.
 *
 */

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
        PegCalculator pegCalculator =  new PegCalculator();
        assertEquals(2, pegCalculator.calculateSameColor(Arrays.asList(Peg.RED, Peg.WHITE), Arrays.asList(Peg.RED,
                Peg.WHITE)));
        assertEquals(3, pegCalculator.calculateSameColor(Arrays.asList(Peg.RED, Peg.RED, Peg.WHITE, Peg.BLUE),
                Arrays.asList(Peg.RED, Peg.WHITE, Peg.BLUE, Peg.BLUE)));
        assertEquals(1, pegCalculator.calculateSameColor(Arrays.asList(Peg.RED),
                Arrays.asList(Peg.RED, Peg.WHITE, Peg.BLUE, Peg.BLUE)));
        assertEquals(1, pegCalculator.calculateSameColor(Arrays.asList(Peg.BLUE),
                Arrays.asList(Peg.RED, Peg.WHITE, Peg.BLUE, Peg.BLUE)));
        assertEquals(2, pegCalculator.calculateSameColor(Arrays.asList(Peg.BLUE, Peg.BLUE),
                Arrays.asList(Peg.RED, Peg.WHITE, Peg.BLUE, Peg.BLUE)));
    }

    @Test
    void calculateSameColorAndSamePlace() {
        PegCalculator pegCalculator =  new PegCalculator();
        assertEquals(1,
                pegCalculator.calculateNumberOfEqualObjectsInSamePlace(Arrays.asList(Peg.RED, Peg.RED, Peg.WHITE, Peg.WHITE),
                        Arrays.asList(Peg.RED, Peg.WHITE, Peg.BLUE, Peg.BLUE)));
        assertEquals(2,
                pegCalculator.calculateNumberOfEqualObjectsInSamePlace(Arrays.asList(Peg.RED, Peg.WHITE), Arrays.asList(Peg.RED,
                        Peg.WHITE)));
        assertEquals(3,
                pegCalculator.calculateNumberOfEqualObjectsInSamePlace(Arrays.asList(Peg.RED, Peg.BLUE, Peg.WHITE, Peg.WHITE),
                        Arrays.asList(Peg.RED, Peg.BLUE, Peg.BLUE, Peg.WHITE)));
    }

}