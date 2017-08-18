package com.adamkoch.mastermind;

import java.util.List; /**
 * <p>Created by aakoch on 2017-08-18.</p>
 *
 * @author aakoch
 * @since 1.0.0
 */
public class GuessAndIndicators {
    private final Indicator[] indicators;
    private final List<Peg> guess;

    public GuessAndIndicators(List<Peg> guess, Indicator[] indicators) {

        this.guess = guess;
        this.indicators = indicators;
    }

    public List<Peg> getGuess() {
        return guess;
    }

    public Indicator[] getIndicators() {
        return indicators;
    }
}
