package com.adamkoch.mastermind;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>Created by aakoch on 2017-08-12.</p>
 *
 * @author aakoch
 * @since 1.0.0
 */
class DeductionsTest {

    @Test
    void matchesPreviousResult_2red() {
        List<Peg> pegs = Arrays.asList(Peg.WHITE, Peg.WHITE, Peg.BLUE, Peg.BLUE);
        Map<List<Peg>, Indicator[]> map = new HashMap<>();
        map.put(Arrays.asList(Peg.WHITE, Peg.WHITE, Peg.WHITE, Peg.WHITE), new Indicator[]{
                Indicator.CORRECT_COLOR_AND_PLACEMENT, Indicator.CORRECT_COLOR_AND_PLACEMENT
        });
        assertTrue(Deductions.matchesPreviousResult(pegs, map));
    }

    @Test
    void matchesPreviousResult_2white() {
        List<Peg> pegs = Arrays.asList(Peg.WHITE, Peg.WHITE, Peg.BLUE, Peg.BLUE);
        Map<List<Peg>, Indicator[]> map = new HashMap<>();
        map.put(Arrays.asList(Peg.RED, Peg.RED, Peg.WHITE, Peg.WHITE), new Indicator[]{
                Indicator.CORRECT_COLOR, Indicator.CORRECT_COLOR
        });
        assertTrue(Deductions.matchesPreviousResult(pegs, map));
    }

}