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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

import static java.util.function.Predicate.isEqual;

/**
 * <p>Created by aakoch on 2017-08-12.</p>
 *
 * @author aakoch
 * @since 1.0.0
 */
public class Deductions {
    private static final Logger LOGGER = LogManager.getLogger(Deductions.class);

    public static boolean matchesPreviousResult(List<Peg> guess, Map<List<Peg>, Indicator[]> map) {
        PegCalculator calculator = new PegCalculator();

//      for each white indicator, there should be a matching color
//      for each red indicator, there should be a same color in same place

        return map.entrySet().stream().noneMatch(entry -> {
            final List<Peg> previousGuess = entry.getKey();
            final Indicator[] previousAnswer = entry.getValue();

            int numberOfWhitePegs = (int) Arrays.stream(previousAnswer)
                                                .filter(isEqual(Indicator.CORRECT_COLOR))
                                                .count();
            int numberOfRedPegs = (int) Arrays.stream(previousAnswer)
                                              .filter(isEqual(Indicator.CORRECT_COLOR_AND_PLACEMENT))
                                              .count();

            int numberSameColor = calculator.calculateSameColor(guess, previousGuess);
            int numberSameColorAndPlace = calculator.calculateNumberOfEqualObjectsInSamePlace(guess, previousGuess);

            if (numberOfRedPegs == numberSameColorAndPlace && Math.abs(numberSameColorAndPlace - numberSameColor) ==
                    numberOfWhitePegs && guess.size() == previousGuess.size()) {
                return false;
            }
            else {
                LOGGER.trace("Removing {} because of {}={}", guess, previousGuess, Arrays.toString(previousAnswer));
                return true;
            }
        });
    }

}
