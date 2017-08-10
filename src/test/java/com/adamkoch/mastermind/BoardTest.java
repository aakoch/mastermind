package com.adamkoch.mastermind;

import com.adamkoch.mastermind.Peg;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>Created by aakoch on 2017-08-09.</p>
 *
 * @author aakoch
 * @since 1.0.0
 */
class BoardTest {

    @Test
    public void test() {
        Board board = new Board(Peg.WHITE, Peg.WHITE, Peg.WHITE);
        boolean correct = board.guess(Peg.WHITE, Peg.WHITE, Peg.WHITE);

        assertTrue(correct);
    }
}