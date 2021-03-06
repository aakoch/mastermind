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
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * <p>Created by aakoch on 2017-08-10.</p>
 *
 * @author aakoch
 * @since 1.0.0
 */
class ComboMakerTest {

    @Test
    public void test_initial_1_1_stream() {

        List<List<Peg>> combinations = ComboMaker.initialCombosStream(Arrays.asList(Peg.WHITE), 1).collect
                (Collectors.toList());
        assertEquals(1, combinations.size());
        assertEquals(1, combinations.get(0).size());
        assertEquals(Peg.WHITE, combinations.get(0).get(0));
    }

    @Test
    public void test_initial_1_2_stream() {

        List<List<Peg>> combinations = ComboMaker.initialCombosStream(Arrays.asList(Peg.WHITE), 2).collect
                (Collectors.toList());
        assertEquals(1, combinations.size());
        assertEquals(2, combinations.get(0).size());
        assertEquals(Peg.WHITE, combinations.get(0).get(0));
        assertEquals(Peg.WHITE, combinations.get(0).get(1));
    }

    @Test
    public void test_initial_1_3_stream() {

        List<List<Peg>> combinations = ComboMaker.initialCombosStream(Arrays.asList(Peg.WHITE), 3).collect
                (Collectors.toList());
        assertEquals(1, combinations.size());
        assertEquals(3, combinations.get(0).size());
        assertEquals(Peg.WHITE, combinations.get(0).get(0));
        assertEquals(Peg.WHITE, combinations.get(0).get(1));
        assertEquals(Peg.WHITE, combinations.get(0).get(2));
    }

    @Test
    public void test_initial_2_1_stream() {

        List<List<Peg>> combinations = ComboMaker.initialCombosStream(Arrays.asList(Peg.WHITE, Peg.BLUE), 1).collect(
                Collectors.toList());
        assertEquals(2, combinations.size());
        assertEquals(1, combinations.get(0).size());
        assertEquals(Peg.WHITE, combinations.get(0).get(0));
        assertEquals(Peg.BLUE, combinations.get(1).get(0));
    }

    @Test
    public void test_initial_2_2_stream() {

        List<List<Peg>> combinations = ComboMaker.initialCombosStream(Arrays.asList(Peg.WHITE, Peg.BLUE), 2).collect(
                Collectors.toList());
        assertEquals(4, combinations.size());
        assertEquals(2, combinations.get(0).size());

        final List<Peg> pegs1 = combinations.get(0);
        assertEquals(Peg.WHITE, pegs1.get(0));
        assertEquals(Peg.WHITE, pegs1.get(1));

        assertEquals(Peg.WHITE, combinations.get(1).get(0));
        assertEquals(Peg.BLUE, combinations.get(1).get(1));

        assertEquals(Peg.BLUE, combinations.get(2).get(0));
        assertEquals(Peg.WHITE, combinations.get(2).get(1));

        assertEquals(Peg.BLUE, combinations.get(3).get(0));
        assertEquals(Peg.BLUE, combinations.get(3).get(1));
    }

}