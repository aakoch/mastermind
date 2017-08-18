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

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

/**
 * <p>Created by aakoch on 2017-08-09.</p>
 *
 * @author aakoch
 * @since 1.0.0
 */
class BoardTest {

    @Test
    public void test1() {
        Board board = new Board(Peg.WHITE, Peg.WHITE, Peg.WHITE, Peg.WHITE);
        Indicator[] correctIndicators = board.guess(Arrays.asList(Peg.WHITE, Peg.WHITE, Peg.WHITE, Peg.WHITE));

        assertEquals(new Indicator[]{Indicator.CORRECT_COLOR_AND_PLACEMENT, Indicator
                        .CORRECT_COLOR_AND_PLACEMENT, Indicator.CORRECT_COLOR_AND_PLACEMENT, Indicator
                        .CORRECT_COLOR_AND_PLACEMENT},
                correctIndicators);
    }

    @Test
    public void test2() {
        Board board = new Board(Peg.WHITE, Peg.WHITE, Peg.BLUE, Peg.WHITE);
        Indicator[] correctIndicators = board.guess(Arrays.asList(Peg.WHITE, Peg.WHITE, Peg.WHITE, Peg.BLUE));

        assertEquals(new Indicator[]{Indicator.CORRECT_COLOR_AND_PLACEMENT, Indicator.CORRECT_COLOR_AND_PLACEMENT,
                Indicator.CORRECT_COLOR, Indicator.CORRECT_COLOR}, correctIndicators);
    }

    @Test
    public void test3() {
        Board board = new Board(Peg.RED, Peg.WHITE, Peg.BLUE, Peg.WHITE);
        Indicator[] correctIndicators = board.guess(Arrays.asList(Peg.WHITE, Peg.WHITE, Peg.RED, Peg.BLUE));

        assertEquals(new Indicator[]{Indicator.CORRECT_COLOR_AND_PLACEMENT, Indicator.CORRECT_COLOR,
                Indicator.CORRECT_COLOR, Indicator.CORRECT_COLOR}, correctIndicators);
    }

    @Test
    public void test_real1() {
        Board board = new Board(Peg.WHITE, Peg.BLUE, Peg.WHITE, Peg.RED);
        Indicator[] correctIndicators = board.guess(Arrays.asList(Peg.WHITE, Peg.WHITE, Peg.RED, Peg.RED));

        assertEquals(new Indicator[]{Indicator.CORRECT_COLOR_AND_PLACEMENT, Indicator.CORRECT_COLOR_AND_PLACEMENT,
                Indicator.CORRECT_COLOR}, correctIndicators);
    }

    @Test
    public void test_real2() {
        Board board = new Board                                (Peg.WHITE, Peg.BLUE, Peg.WHITE, Peg.RED);
        Indicator[] correctIndicators = board.guess(Arrays.asList(Peg.RED, Peg.BLUE, Peg.WHITE, Peg.RED));

        assertEquals(new Indicator[]{Indicator.CORRECT_COLOR_AND_PLACEMENT, Indicator.CORRECT_COLOR_AND_PLACEMENT,
                Indicator.CORRECT_COLOR_AND_PLACEMENT}, correctIndicators);
    }

    public static void assertEquals(Indicator[] expected, Indicator[] actual) {
        Assertions.assertEquals(Arrays.toString(expected), Arrays.toString(actual));
    }
}