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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * <p>Created by aakoch on 2017-08-12.</p>
 *
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

            int numberSameColor = PegCalculator.calculateSameColor(pegs, listEntry
                    .getKey() == null ? new ArrayList<>() : listEntry.getKey());
            int numberSameColorAndPlace = PegCalculator.calculateSameColorAndSamePlace(pegs, listEntry
                    .getKey() == null ? new ArrayList<>() : listEntry.getKey());

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
