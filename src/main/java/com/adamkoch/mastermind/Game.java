package com.adamkoch.mastermind;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.List;

/**
 * <p>Created by aakoch on 2017-08-09.</p>
 *
 * @author aakoch
 * @since 1.0.0
 */
public class Game {

    private static final Logger LOGGER = LogManager.getLogger(Game.class);
    private final Board board;
    private final Player player;

    public Game(Board board, Player player) {
        this.board = board;
        this.player = player;
    }

    public static void main(String[] args) {
        Board board = new Board(Peg.WHITE, Peg.BLUE, Peg.WHITE, Peg.RED);
        Player player = new Player();
        Game game = new Game(board, player);
        int numberOfTurns = game.play();
        LOGGER.debug("numberOfTurns = " + numberOfTurns);
    }

    private int play() {
        int numberOfTurns = 0;
        Indicator[] results;
        List<List<Peg>> combinations = ComboMaker.initialCombos(Arrays.asList(Peg.WHITE, Peg.RED, Peg.BLUE), 4);
        List<Peg> guess = combinations.get(numberOfTurns);

        do {
            results = board.guess(guess);
            numberOfTurns++;
            if (numberOfTurns > combinations.size()) {
                throw new RuntimeException("Ran out of guesses");
            }
            guess = combinations.get(numberOfTurns);
        }
        while (!Arrays.stream(results).allMatch(indicator -> indicator == Indicator.CORRECT_COLOR_AND_PLACEMENT));

        return numberOfTurns;
    }
}
