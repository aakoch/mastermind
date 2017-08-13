package com.adamkoch.mastermind;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

/**
 * <p>Created by aakoch on 2017-08-09.</p>
 *
 * @author aakoch
 * @since 1.0.0
 */
public class Game {

    private static final Logger LOGGER = LogManager.getLogger(Game.class);
    public static final List<Peg> AVAILABLE_COLORS = Arrays.asList(Peg.WHITE, Peg.RED, Peg.BLUE, Peg.ORANGE, Peg
            .YELLOW, Peg.BLACK);//, Peg.A, Peg.B, Peg.C);
    private final Board board;
    private final Player player;
    public static final int BOARD_SIZE = 5;
    public static final List<List<Peg>> COMBINATIONS = ComboMaker.initialCombos(AVAILABLE_COLORS, BOARD_SIZE);

    public Game(Board board, Player player) {
        this.board = board;
        this.player = player;
    }

    public static void main(String[] args) {

        long startTime = System.currentTimeMillis();

        int totalNumberOfTurns = 0;
        int maxNumberOfTurns = 0;
        int minNumberOfTurns = Integer.MAX_VALUE;
        final int runs = 100;
        for (int i = 0; i < runs; i++) {
            Board board = new Board(RandomUtils.getRandom(COMBINATIONS));
            Player player = new Player();
            Game game = new Game(board, player);
            final int numberOfTurns = game.play();
            maxNumberOfTurns = Math.max(maxNumberOfTurns, numberOfTurns);
            minNumberOfTurns = Math.min(minNumberOfTurns, numberOfTurns);
            totalNumberOfTurns += numberOfTurns;

        }
        LOGGER.debug(String.format("Average number of turns = %.2f", ((double) totalNumberOfTurns / (double) runs)));
        LOGGER.debug("maxNumberOfTurns = " + maxNumberOfTurns);
        LOGGER.debug("minNumberOfTurns = " + minNumberOfTurns);

        LOGGER.debug(runs + " games with " + AVAILABLE_COLORS.size() + " colors on a board of size " + BOARD_SIZE +
                " took " + ((double) (System.currentTimeMillis() - startTime) / 1000d) + " seconds");
    }

    private int play() {
        int numberOfTurns = 0;
        Indicator[] results;
        List<List<Peg>> combinations = new ArrayList<>(COMBINATIONS);
        Map<List<Peg>, Indicator[]> previousGuessAndResult = new HashMap<>();

        do {
            if (combinations.size() <= 0) {
                throw new RuntimeException("Ran out of guesses");
            }
            Collections.shuffle(combinations);
            LOGGER.debug("combinations.size() = " + combinations.size());
            final List<Peg> guess = combinations.remove(0);
            LOGGER.debug("guess = " + guess);
            results = board.guess(guess);
            previousGuessAndResult.put(guess, results);

            LOGGER.debug("results = " + Arrays.toString(results));
            numberOfTurns++;
            combinations.removeIf(combo -> !Deductions.matchesPreviousResult(combo, previousGuessAndResult));
        }
        while (results.length != BOARD_SIZE || !Arrays.stream(results).allMatch(indicator -> indicator == Indicator
                .CORRECT_COLOR_AND_PLACEMENT));
        LOGGER.info("Won after " + numberOfTurns + " guesses!");

        return numberOfTurns;
    }

}
