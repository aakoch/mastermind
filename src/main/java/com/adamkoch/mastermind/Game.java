package com.adamkoch.mastermind;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.NumberFormat;
import java.util.Arrays;
import java.util.List;

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
    public static final int BOARD_SIZE = 8;
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
        final int runs = 2;
        final int numberOfCombinations = (int) Math.pow(AVAILABLE_COLORS.size(), BOARD_SIZE);
        final NumberFormat numberInstance = NumberFormat.getNumberInstance();
        numberInstance.setGroupingUsed(true);
        LOGGER.info("combinations = " + numberInstance.format(numberOfCombinations));
        for (int i = 0; i < runs; i++) {
            long startBoardCreationTime = System.currentTimeMillis();
            Board board = new Board(RandomUtils.getRandom(ComboMaker.initialCombosStream(AVAILABLE_COLORS, BOARD_SIZE),
                    numberOfCombinations));
            LOGGER.debug("It took " + ((double) (System.currentTimeMillis() - startBoardCreationTime) / 1000d) +
                            " seconds just to pick the board colors");
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
        final int[] numberOfGuesses = {0};

        ComboMaker.initialCombosStream(AVAILABLE_COLORS, BOARD_SIZE)
                .filter(board::matchesPreviousResult)
                .map(winningGuess -> {
                    LOGGER.debug("Guessing " + winningGuess);
                    final Indicator[] indicators = board.guess(winningGuess);
                    LOGGER.debug("Resulted in " + Arrays.toString(indicators));
                    numberOfGuesses[0]++;
                    return indicators;
                })
                .filter(indicators -> indicators.length == BOARD_SIZE
                        && Arrays.stream(indicators).allMatch(indicator -> indicator == Indicator.CORRECT_COLOR_AND_PLACEMENT))
                .count();

        LOGGER.info("Won after " + numberOfGuesses[0] + " guesses!");

        return numberOfGuesses[0];
    }

}
