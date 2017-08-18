package com.adamkoch.mastermind;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;
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
    private List<Peg> previousCombination;

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
//        previousCombination = pegs;
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
