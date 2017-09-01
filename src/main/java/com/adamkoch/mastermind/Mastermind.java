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

import com.google.common.collect.Streams;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.NumberFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.function.Predicate.isEqual;

/**
 * <p>Created by aakoch on 2017-08-31.</p>
 *
 * @author aakoch
 * @since 1.0.0
 */
public class Mastermind {

    private static final List<Peg> AVAILABLE_COLORS = Arrays.asList(Peg.values());
    private static final int BOARD_SIZE = 5;
    private static final Logger LOGGER = LogManager.getLogger(Mastermind.class);
    private static Random RANDOM = new Random();

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();

        logCombinations();

        final IntSummaryStatistics intSummaryStatistics = IntStream.range(0, 10).map(i -> {
            Board board = new Board(IntStream.range(0, BOARD_SIZE)
                                             .mapToObj(i1 -> getRandom(AVAILABLE_COLORS))
                                             .collect(Collectors.toList()));
            Game game = new Game(board);
            return game.play();
        }).summaryStatistics();

        LOGGER.info(String.format("Average number of turns = %.2f", intSummaryStatistics.getAverage()));
        LOGGER.info("maxNumberOfTurns = " + intSummaryStatistics.getMax());
        LOGGER.info("minNumberOfTurns = " + intSummaryStatistics.getMin());

        LOGGER.info(
                intSummaryStatistics.getCount() + " games with " + AVAILABLE_COLORS.size() + " colors on a board of size "
                        + BOARD_SIZE + " took " + ((double) (System.currentTimeMillis() - startTime) / 1000d) + " seconds");
    }

    private static void logCombinations() {
        final int numberOfCombinations = (int) Math.pow(AVAILABLE_COLORS.size(), BOARD_SIZE);
        final NumberFormat numberInstance = NumberFormat.getNumberInstance();
        numberInstance.setGroupingUsed(true);
        LOGGER.info("combinations = " + numberInstance.format(numberOfCombinations));
    }

    static <T> Stream<List<T>> createCombinationStream(List<T> list, int size) {
        final Stream<T> listStream = list.stream();
        final Stream<List<T>> returnStream;
        if (size == 1) {
            returnStream = listStream.map(Arrays::asList);
        }
        else {
            returnStream = listStream.flatMap(item -> createCombinationStream(list, size - 1).map(sublistList -> {
                List<T> newList = new ArrayList<>();
                newList.add(item);
                newList.addAll(sublistList);
                return newList;
            }));
        }
        return returnStream;
    }

    static int calculateSameColor(List<Peg> pegs1, List<Peg> pegs2) {

        final Map<Peg, Integer> map1 = countByPeg(pegs1);
        final Map<Peg, Integer> map2 = countByPeg(pegs2);

        return map1.keySet()
                   .stream()
                   .filter(map2::containsKey)
                   .mapToInt(peg -> Math.min(map1.get(peg), map2.get(peg)))
                   .sum();
    }

    private static Map<Peg, Integer> countByPeg(List<Peg> pegs) {
        return pegs.stream()
                   .collect(Collectors.groupingBy(Function.identity(),
                           Collectors.reducing(0, e -> 1, Integer::sum)));
    }

    static <T> int calculateNumberOfEqualObjectsInSamePlace(List<T> list1, List<T> list2) {
        return (int) Streams.zip(list1.stream(), list2.stream(), Objects::equals).filter(Boolean::booleanValue).count();
    }

    private static <T> T getRandom(List<T> list) {
        return list.get(RANDOM.nextInt(list.size()));
    }

    static boolean matchesPreviousResult2(List<Peg> guess, Map<List<Peg>, Indicator[]> map) {

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

            int numberSameColor = calculateSameColor(guess, previousGuess);
            int numberSameColorAndPlace = calculateNumberOfEqualObjectsInSamePlace(guess, previousGuess);

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

    enum Peg {
        WHITE, BLUE, RED, ORANGE, BLACK//, PINK//, YELLOW, GRAY, PEACH, TEAL
    }

    enum Indicator {
        CORRECT_COLOR("white"),
        CORRECT_COLOR_AND_PLACEMENT("red");

        private final String description;

        Indicator(String description) {
            this.description = description;
        }

        @Override
        public String toString() {
            return description;
        }
    }

    static class Board {

        private final List<Peg> list;
        private ConcurrentHashMap<List<Peg>, Indicator[]> previousGuessAndResult;

        @Deprecated
        public Board(Peg peg1, Peg peg2, Peg peg3, Peg peg4) {
            this(Arrays.asList(peg1, peg2, peg3, peg4));
        }

        Board(List<Peg> list) {
            this.list = Collections.unmodifiableList(list);
            LOGGER.debug("Creating board with " + list);
            previousGuessAndResult = new ConcurrentHashMap<>();
        }

        Indicator[] guess(List<Peg> pegs) {
            int numberOfPegsWithSameColor = calculateNumberOfPegsWithSameColor(pegs);
            int numberOfPegsInCorrectPlace = calculateNumberOfPegsInCorrectPlace(pegs);
            int numberOfRedPegs = numberOfPegsInCorrectPlace;
            int numberOfWhitePegs = numberOfPegsWithSameColor - numberOfPegsInCorrectPlace;

            List<Indicator> results = new ArrayList<>();
            results.addAll(Collections.nCopies(numberOfRedPegs, Indicator.CORRECT_COLOR_AND_PLACEMENT));
            results.addAll(Collections.nCopies(numberOfWhitePegs, Indicator.CORRECT_COLOR));

            Indicator[] arr = new Indicator[results.size()];
            previousGuessAndResult.put(pegs, arr);
            return results.toArray(arr);
        }

        private int calculateNumberOfPegsInCorrectPlace(List<Peg> pegs) {
            return calculateNumberOfEqualObjectsInSamePlace(pegs, list);
        }

        private int calculateNumberOfPegsWithSameColor(List<Peg> pegs) {
            return calculateSameColor(pegs, list);
        }

        boolean matchesPreviousResult(List<Peg> combination) {
            return matchesPreviousResult2(combination, previousGuessAndResult);
        }

    }

    static class Game {

        static final int MAX_NUMBER_OF_TURNS = 10;
        private final Board board;

        public Game(Board board) {
            this.board = board;
        }

        int play() {
            final int[] numberOfGuesses = {0};

            final Optional<Indicator[]> first = createCombinationStream(AVAILABLE_COLORS, BOARD_SIZE)
                    .filter(board::matchesPreviousResult)
                    .map(guess -> {
                        LOGGER.debug("Guessing " + guess);
                        final Indicator[] indicators = board.guess(guess);
                        LOGGER.debug("Resulted in " + Arrays.toString(indicators));
                        numberOfGuesses[0]++;
                        return indicators;
                    })
                    .filter(indicators -> indicators.length == BOARD_SIZE
                            && Arrays.stream(indicators).allMatch(isEqual(Indicator.CORRECT_COLOR_AND_PLACEMENT)))
                    .findFirst();

            final String outcome;
            if (first.isPresent() && numberOfGuesses[0] <= MAX_NUMBER_OF_TURNS) {
                outcome = "Won";
            }
            else {
                outcome = "Lost";
            }

            LOGGER.info("{} after {} guesses!", outcome, numberOfGuesses[0]);

            return numberOfGuesses[0];
        }

    }
}