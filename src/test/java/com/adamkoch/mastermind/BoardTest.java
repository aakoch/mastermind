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

    public static void assertEquals(Indicator[] expected, Indicator[] actual) {
        Assertions.assertEquals(expected.length, actual.length);

        for (int index = 0; index < expected.length; index++) {
            Assertions.assertEquals(expected[index], actual[index],
                    "Expected " + expected[index] + " in place " + index + " but got " + actual[index]);
        }
    }
}