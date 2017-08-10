package com.adamkoch.mastermind;

/**
 * <p>Created by aakoch on 2017-08-09.</p>
 *
 * @author aakoch
 * @since 1.0.0
 */
public class Board {
    private final Peg peg1;
    private final Peg peg2;
    private final Peg peg3;

    public Board(Peg peg1, Peg peg2, Peg peg3) {
        this.peg1 = peg1;
        this.peg2 = peg2;
        this.peg3 = peg3;
    }

    public boolean guess(Peg peg1, Peg peg2, Peg peg3) {
        return true;
    }
}
