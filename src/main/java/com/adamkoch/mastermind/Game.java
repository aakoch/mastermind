package com.adamkoch.mastermind;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;

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
        Board board = new Board(Peg.WHITE, Peg.BLUE, Peg.RED, Peg.WHITE);
        Player player = new Player();
        Game game = new Game(board, player);
        int numberOfTurns = game.play();
        LOGGER.debug("numberOfTurns = " + numberOfTurns);
    }

    private int play() {
        int numberOfTurns = 0;
        Indicator[] results = new Indicator[4];
        while (!Arrays.stream(results).allMatch(indicator -> indicator == Indicator.CORRECT_COLOR_AND_PLACEMENT)) {
            Peg peg1 = Peg.WHITE;
            Peg peg2 = Peg.BLUE;
            Peg peg3 = Peg.RED;
            Peg peg4 = Peg.WHITE;
            results = board.guess(peg1, peg2, peg3, peg4);
            numberOfTurns++;
            if (numberOfTurns > 100) {
                throw new RuntimeException("Number of turns > 100");
            }
        }
        return numberOfTurns;
    }
}
