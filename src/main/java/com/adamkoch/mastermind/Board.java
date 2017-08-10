package com.adamkoch.mastermind;

import java.util.*;

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
    private final Peg peg4;

    public Board(Peg peg1, Peg peg2, Peg peg3, Peg peg4) {
        this.peg1 = peg1;
        this.peg2 = peg2;
        this.peg3 = peg3;
        this.peg4 = peg4;
    }

    public Indicator[] guess(Peg peg1, Peg peg2, Peg peg3, Peg peg4) {
        int numberOfPegsWithSameColor = calculateNumberOfPegsWithSameColor(peg1, peg2, peg3, peg4);
        int numberOfPegsInCorrectPlace = calculateNumberOfPegsInCorrectPlace(peg1, peg2, peg3, peg4);
        int numberOfRedPegs = numberOfPegsInCorrectPlace;
        int numberOfWhitePegs = numberOfPegsWithSameColor - numberOfPegsInCorrectPlace;
        List<Indicator> results = new ArrayList<Indicator>();
        for (int i = 0; i < numberOfRedPegs; i++) {
            results.add(Indicator.CORRECT_COLOR_AND_PLACEMENT);
        }
        for (int i = 0; i < numberOfWhitePegs; i++) {
            results.add(Indicator.CORRECT_COLOR);
        }
        Indicator[] arr = new Indicator[4];
        return results.toArray(arr);
    }

    private int calculateNumberOfPegsInCorrectPlace(Peg peg1, Peg peg2, Peg peg3, Peg peg4) {
        int count = 0;
        if (this.peg1 == peg1)
            count++;
        if (this.peg2 == peg2)
            count++;
        if (this.peg3 == peg3)
            count++;
        if (this.peg4 == peg4)
            count++;
        return count;
    }

    private int calculateNumberOfPegsWithSameColor(Peg peg1, Peg peg2, Peg peg3, Peg peg4) {
        int count = 0;

        for (Peg peg : Peg.values()) {
            int codeCount = 0;
            int guessCount = 0;
            if (this.peg1 == peg) {
                codeCount++;
            }
            if (this.peg2 == peg) {
                codeCount++;
            }
            if (this.peg3 == peg) {
                codeCount++;
            }
            if (this.peg4 == peg) {
                codeCount++;
            }
            if (peg1 == peg) {
                guessCount++;
            }
            if (peg2 == peg) {
                guessCount++;
            }
            if (peg3 == peg) {
                guessCount++;
            }
            if (peg4 == peg) {
                guessCount++;
            }
            if (codeCount == guessCount) {
                count += codeCount;
            }
        }

        return count;
    }
}
