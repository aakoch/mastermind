package com.adamkoch.mastermind;

import java.util.List;
import java.util.Map;

/**
 * <p>Created by aakoch on 2017-08-09.</p>
 *
 * @author aakoch
 * @since 1.0.0
 */
public class Player {
    public void removeCombinations(List<List<Peg>> combinations, Map<List<Peg>, Indicator[]> previousGuessAndResult) {
        combinations.removeIf(combo -> !Deductions.matchesPreviousResult(combo, previousGuessAndResult));
    }
}
