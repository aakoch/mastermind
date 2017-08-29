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

import java.text.NumberFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * <p>Created by aakoch on 2017-08-09.</p>
 *
 * @author aakoch
 * @since 1.0.0
 */
public class Game {

    public static final List<Peg> AVAILABLE_COLORS = Arrays.asList(Peg.values());

    public static final int BOARD_SIZE = 7;
    public static final int MAX_NUMBER_OF_TURNS = 10;
    private static final Logger LOGGER = LogManager.getLogger(Game.class);
    private final Board board;

    public Game(Board board) {
        this.board = board;
    }

    public static void main(String[] args) {

        long startTime = System.currentTimeMillis();

        int totalNumberOfTurns = 0;
        int maxNumberOfTurns = 0;
        int minNumberOfTurns = Integer.MAX_VALUE;
        final int runs = 100;
        final int numberOfCombinations = (int) Math.pow(AVAILABLE_COLORS.size(), BOARD_SIZE);
        final NumberFormat numberInstance = NumberFormat.getNumberInstance();
        numberInstance.setGroupingUsed(true);
        LOGGER.info("combinations = " + numberInstance.format(numberOfCombinations));
        for (int i = 0; i < runs; i++) {
            Board board = new Board(IntStream.range(0, BOARD_SIZE)
                                             .mapToObj(i1 -> RandomUtils.getRandom(AVAILABLE_COLORS))
                                             .collect(Collectors.toList()));
            Game game = new Game(board);
            final int numberOfTurns = game.play();
            maxNumberOfTurns = Math.max(maxNumberOfTurns, numberOfTurns);
            minNumberOfTurns = Math.min(minNumberOfTurns, numberOfTurns);
            totalNumberOfTurns += numberOfTurns;
        }

        LOGGER.info(String.format("Average number of turns = %.2f", ((double) totalNumberOfTurns / (double) runs)));
        LOGGER.info("maxNumberOfTurns = " + maxNumberOfTurns);
        LOGGER.info("minNumberOfTurns = " + minNumberOfTurns);

        LOGGER.info(runs + " games with " + AVAILABLE_COLORS.size() + " colors on a board of size " + BOARD_SIZE +
                " took " + ((double) (System.currentTimeMillis() - startTime) / 1000d) + " seconds");
    }

    public int play() {
        final int[] numberOfGuesses = {0};

        final Optional<Indicator[]> first = ComboMaker.initialCombosStream(AVAILABLE_COLORS, BOARD_SIZE)
                                                      .filter(board::matchesPreviousResult)
                                                      .map(guess -> {
                                                          LOGGER.debug("Guessing " + guess);
                                                          final Indicator[] indicators = board.guess(guess);
                                                          LOGGER.debug("Resulted in " + Arrays.toString(indicators));
                                                          numberOfGuesses[0]++;
                                                          return indicators;
                                                      })
                                                      .filter(indicators -> indicators.length == BOARD_SIZE
                                                              && Arrays.stream(indicators)
                                                                       .allMatch(
                                                                               indicator -> indicator == Indicator.CORRECT_COLOR_AND_PLACEMENT))
                                                      .findFirst();

        if (first.isPresent() && numberOfGuesses[0] <= MAX_NUMBER_OF_TURNS) {
            LOGGER.info("Won after " + numberOfGuesses[0] + " guesses!");
        }
        else {

            LOGGER.info("Lost after " + numberOfGuesses[0] + " guesses!");
        }

        return numberOfGuesses[0];
    }

}
