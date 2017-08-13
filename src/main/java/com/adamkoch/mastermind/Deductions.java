package com.adamkoch.mastermind;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * <p>Created by aakoch on 2017-08-12.</p>
 *Peg.WHITE, Peg.BLUE, Peg.WHITE, Peg.RED
 * @author aakoch
 * @since 1.0.0
 */
public class Deductions {
    private static final Logger LOGGER = LogManager.getLogger(Deductions.class);

    public static boolean matchesPreviousResult(List<Peg> pegs, Map<List<Peg>, Indicator[]> map) {
//        for each white indicator, there should be a matching color
//        for each red indicator, there should be a same color in same place

        for (Map.Entry<List<Peg>, Indicator[]> listEntry : map.entrySet()) {
            int numberOfWhitePegs = (int) Arrays.stream(listEntry.getValue()).filter(indicator -> indicator == Indicator
                    .CORRECT_COLOR).count();
            int numberOfRedPegs = (int) Arrays.stream(listEntry.getValue()).filter(indicator -> indicator == Indicator
                    .CORRECT_COLOR_AND_PLACEMENT).count();

            int numberSameColor = PegCalculator.calculateSameColor(pegs, listEntry.getKey());
            int numberSameColorAndPlace = PegCalculator.calculateSameColorAndSamePlace(pegs, listEntry.getKey());

            if (numberOfRedPegs == numberSameColorAndPlace && Math.abs(numberSameColorAndPlace - numberSameColor) ==
                    numberOfWhitePegs) {
                // nothing
            }
            else {
                LOGGER.debug("Removing " + pegs + " because of " + listEntry.getKey() + "=" + Arrays.toString(
                        listEntry.getValue()));
                return false;
            }
        }

        return true;
    }

}
