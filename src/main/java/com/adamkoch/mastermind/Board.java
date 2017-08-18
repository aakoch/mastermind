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
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <p>Created by aakoch on 2017-08-09.</p>
 *
 * @author aakoch
 * @since 1.0.0
 */
public class Board {
    private static final Logger LOGGER = LogManager.getLogger(Board.class);

    private final List<Peg> list;
    private ConcurrentHashMap<List<Peg>, Indicator[]> previousGuessAndResult;

    @Deprecated
    public Board(Peg peg1, Peg peg2, Peg peg3, Peg peg4) {
        this(Arrays.asList(peg1, peg2, peg3, peg4));
    }

    public Board(List<Peg> list) {
        this.list = Collections.unmodifiableList(list);
        LOGGER.debug("Creating board with " + list);
        previousGuessAndResult = new ConcurrentHashMap<>();
    }

    public Indicator[] guess(List<Peg> pegs) {
        int numberOfPegsWithSameColor = calculateNumberOfPegsWithSameColor(pegs);
        int numberOfPegsInCorrectPlace = calculateNumberOfPegsInCorrectPlace(pegs);
        int numberOfRedPegs = numberOfPegsInCorrectPlace;
        int numberOfWhitePegs = numberOfPegsWithSameColor - numberOfPegsInCorrectPlace;
        List<Indicator> results = new ArrayList<Indicator>();
        for (int i = 0; i < numberOfRedPegs; i++) {
            results.add(Indicator.CORRECT_COLOR_AND_PLACEMENT);
        }
        for (int i = 0; i < numberOfWhitePegs; i++) {
            results.add(Indicator.CORRECT_COLOR);
        }
        Indicator[] arr = new Indicator[results.size()];
        previousGuessAndResult.put(pegs, arr);
        final Indicator[] indicators = results.toArray(arr);
        return indicators;
    }

    private int calculateNumberOfPegsInCorrectPlace(List<Peg> pegs) {
        return PegCalculator.calculateSameColorAndSamePlace(pegs, list);
    }

    private int calculateNumberOfPegsWithSameColor(List<Peg> pegs) {
        return PegCalculator.calculateSameColor(pegs, list);
    }

    public boolean matchesPreviousResult(List<Peg> combination) {
        return Deductions.matchesPreviousResult(combination, previousGuessAndResult);
    }
}
