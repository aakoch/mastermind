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
//        for each white indicator, there should be a matching color
//        for each red indicator, there should be a same color in same place

        for (Map.Entry<List<Peg>, Indicator[]> previousGuessAndAnswer : map.entrySet()) {
            final Indicator[] previousAnswer = previousGuessAndAnswer.getValue();

            int numberOfWhitePegs = (int) Arrays.stream(previousAnswer).filter(isEqual(Indicator.CORRECT_COLOR)).count();
            int numberOfRedPegs = (int) Arrays.stream(previousAnswer).filter(isEqual(Indicator.CORRECT_COLOR_AND_PLACEMENT)).count();

            final List<Peg> previousGuess = previousGuessAndAnswer.getKey();
            int numberSameColor = PegCalculator.calculateSameColor(guess, previousGuess);
            int numberSameColorAndPlace = PegCalculator.calculateNumberOfEqualObjectsInSamePlace(guess, previousGuess);

            if (numberOfRedPegs == numberSameColorAndPlace && Math.abs(numberSameColorAndPlace - numberSameColor) ==
                    numberOfWhitePegs && guess.size() == previousGuess.size()) {
                // nothing
            }
//            else if (guess.size() < previousGuess.size()) {
//                final boolean pegsUnderPreviousGuessSize = numberOfWhitePegs + numberOfRedPegs < previousGuess.size();
//                final boolean sameColorUnderNumberOfWhitePegs = numberSameColor <= Math.max(numberOfWhitePegs,
//                        numberOfRedPegs);
//                final boolean sameColorAndPlaceUnderNumberOfRedPegs = numberSameColorAndPlace <= numberOfRedPegs;
//
//                LOGGER.debug("pegsUnderPreviousGuessSize = " + pegsUnderPreviousGuessSize);
//                LOGGER.debug("sameColorUnderNumberOfWhitePegs = " + sameColorUnderNumberOfWhitePegs);
//                LOGGER.debug("sameColorAndPlaceUnderNumberOfRedPegs = " + sameColorAndPlaceUnderNumberOfRedPegs);
//
//                if (pegsUnderPreviousGuessSize && sameColorUnderNumberOfWhitePegs &&
//                        sameColorAndPlaceUnderNumberOfRedPegs) {
//                    // nothing
//                }
//                else {
//                    LOGGER.debug("Removing " + guess + " because of " + previousGuess + "=" + Arrays.toString(
//                            previousAnswer));
//
//                    return false;
//                }
//            }
            else {
                LOGGER.trace("Removing " + guess + " because of " + previousGuess + "=" + Arrays.toString(
                        previousAnswer));
                return false;
            }
        }

        return true;
    }

    public static int determineNumberOfNonNullIndicators(Map.Entry<List<Peg>, Indicator[]> listEntry) {
        return (int) Arrays.stream(listEntry.getValue()).filter(Objects::nonNull).count();
    }

}
