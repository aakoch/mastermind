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

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;

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