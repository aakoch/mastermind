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

/**
 * <p>Created by aakoch on 2017-08-18.</p>
 *
 * @author aakoch
 * @since 1.0.0
 */

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import static com.adamkoch.mastermind.Game.AVAILABLE_COLORS;
import static com.adamkoch.mastermind.Game.BOARD_SIZE;

public class MastermindVisual extends Application {

    private static final Logger LOGGER = LogManager.getLogger(MastermindVisual.class);

    public static void main(String[] args) {


        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        //init2();


        final Game[] game = new Game[1];


        ChoiceBox<Integer> numberOfHoles = new ChoiceBox<>();
        numberOfHoles.getItems().addAll(1, 2, 3, 4, 5, 6, 7, 8);

        TextField textField = new TextField();
        textField.setPrefColumnCount(2);
        TextField textField2 = new TextField();
        Button btn = new Button();
        btn.setText("Set pegs");
        final List<Peg> random = new ArrayList<>();

        btn.setOnAction(event -> {
            primaryStage.getScene().setCursor(Cursor.WAIT);
            random.clear();
            final Integer numberOfHolesValue = numberOfHoles.getValue();
            random.addAll(createAnswer(numberOfHolesValue));
            textField.setText(random.toString());
            primaryStage.getScene().setCursor(Cursor.DEFAULT);
        });

        Button btn2 = new Button();
        btn2.setText("Start");
        btn2.setOnAction(event -> {
            primaryStage.getScene().setCursor(Cursor.WAIT);
            long startBoardCreationTime = System.currentTimeMillis();
            Board board = new Board(random);
            LOGGER.debug("It took " + ((double) (System.currentTimeMillis() - startBoardCreationTime) / 1000d) +
                    " seconds just to pick the board colors");

            game[0] = new Game(board);
            final int numberOfTurns = game[0].play();
            textField2.setText("Won after " + numberOfTurns + " guesses!");
            primaryStage.getScene().setCursor(Cursor.DEFAULT);
        });

        GridPane root = new GridPane();

        root.addRow(0, btn, textField, numberOfHoles);
        root.addRow(1, btn2, textField2);

        Scene scene = new Scene(root, 400, 250);

        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static List<Peg> createAnswer(Integer numberOfHolesValue) {
        List<Peg> list = new ArrayList<>();
        for (int i = 0; i < numberOfHolesValue; i++) {
            list.add(RandomUtils.getRandom(AVAILABLE_COLORS));
        }
        return list;
    }

    public void init2() {

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
}
