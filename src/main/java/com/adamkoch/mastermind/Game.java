package com.adamkoch.mastermind;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;
import java.util.stream.Stream;

/**
 * <p>Created by aakoch on 2017-08-09.</p>
 *
 * @author aakoch
 * @since 1.0.0
 */
public class Game {

    public static final List<Peg> AVAILABLE_COLORS = //RandomUtils.getRandom(
            Arrays.asList(Peg.WHITE, Peg.RED, Peg.BLUE, Peg.ORANGE, Peg.YELLOW, Peg.BLACK, Peg.PINK, Peg.GRAY,
                    Peg.TEAL);//, 3);
    public static final int BOARD_SIZE = 7;
    public static final int MAX_NUMBER_OF_TURNS = 10;
    private static final Logger LOGGER = LogManager.getLogger(Game.class);
    private final Board board;
    private final Player player;

    public Game(Board board, Player player) {
        this.board = board;
        this.player = player;
    }

    public static void main(String[] args) {

        long startTime = System.currentTimeMillis();

        int totalNumberOfTurns = 0;
        int maxNumberOfTurns = 0;
        int minNumberOfTurns = Integer.MAX_VALUE;
        final int runs = 1;
        final int count = (int) Math.pow(AVAILABLE_COLORS.size(), BOARD_SIZE);
        LOGGER.info("combinations = " + count);
        for (int i = 0; i < runs; i++) {
            Board board = new Board(RandomUtils.getRandom(ComboMaker.initialCombosStream(AVAILABLE_COLORS, BOARD_SIZE),
                    count));
            Player player = new Player();
            Game game = new Game(board, player);
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

    private int play() {
        int numberOfTurns = 0;
        int skipping = 0;

        Stream<List<Peg>> combinations = ComboMaker.initialCombosStream(AVAILABLE_COLORS, BOARD_SIZE).parallel();


        List<Peg> winningGuess = null;
        final Iterator<List<Peg>> iterator = combinations.iterator();
        while (iterator.hasNext()) {
            winningGuess = iterator.next();
            if (board.matchesPreviousResult(winningGuess)) {
                LOGGER.debug("skipping = " + skipping);
                skipping = 0;
                numberOfTurns++;
                LOGGER.debug("guessing = " + winningGuess);
                final Indicator[] indicators = board.guess(winningGuess);
                LOGGER.debug("indicators = " + Arrays.toString(indicators));
                if (indicators.length == BOARD_SIZE && Arrays.stream(indicators).allMatch(indicator -> indicator ==
                        Indicator.CORRECT_COLOR_AND_PLACEMENT)) {
                    break;
                }
            }
            else {
                skipping++;
            }
        }


        LOGGER.debug("winningGuess = " + winningGuess);

        LOGGER.info("Won after " + numberOfTurns + " guesses!");

        return numberOfTurns;
    }

}
