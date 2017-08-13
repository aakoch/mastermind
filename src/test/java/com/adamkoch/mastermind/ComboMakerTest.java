package com.adamkoch.mastermind;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * <p>Created by aakoch on 2017-08-10.</p>
 *
 * @author aakoch
 * @since 1.0.0
 */
class ComboMakerTest {

    @Test
    public void test1() {
        List<List<Peg>> combinations = ComboMaker.combosOf(Peg.BLUE);
        assertEquals(Peg.BLUE, combinations.get(0).get(0));
    }

    @Test
    public void test2() {
        List<List<Peg>> combinations2 = ComboMaker.combosOf(Peg.BLUE, Peg.WHITE);
        assertEquals(Peg.BLUE, combinations2.get(0).get(0));
        assertEquals(Peg.WHITE, combinations2.get(0).get(1));
        assertEquals(Peg.WHITE, combinations2.get(1).get(0));
        assertEquals(Peg.BLUE, combinations2.get(1).get(1));
    }

    @Test
    public void test3() {

        List<List<Peg>> combinations3 = ComboMaker.combosOf(Peg.BLUE, Peg.WHITE, Peg.WHITE);
        assertEquals(Peg.BLUE, combinations3.get(0).get(0));
        assertEquals(Peg.WHITE, combinations3.get(0).get(1));
        assertEquals(Peg.WHITE, combinations3.get(0).get(2));

        assertEquals(Peg.WHITE, combinations3.get(1).get(0));
        assertEquals(Peg.BLUE, combinations3.get(1).get(1));
        assertEquals(Peg.WHITE, combinations3.get(1).get(2));

        assertEquals(Peg.WHITE, combinations3.get(2).get(0));
        assertEquals(Peg.WHITE, combinations3.get(2).get(1));
        assertEquals(Peg.BLUE, combinations3.get(2).get(2));
    }

    @Test
    public void test_initial_1() {

        List<List<Peg>> combinations = ComboMaker.initialCombos(Arrays.asList(Peg.WHITE), 3);
        assertEquals(Peg.WHITE, combinations.get(0).get(0));
        assertEquals(Peg.WHITE, combinations.get(0).get(1));
        assertEquals(Peg.WHITE, combinations.get(0).get(2));
    }

    @Test
    public void test_initial_2() {

        List<List<Peg>> combinations = ComboMaker.initialCombos(Arrays.asList(Peg.WHITE, Peg.BLUE), 3);
        assertEquals(Peg.WHITE, combinations.get(0).get(0));
        assertEquals(Peg.WHITE, combinations.get(0).get(1));
        assertEquals(Peg.WHITE, combinations.get(0).get(2));

        assertEquals(Peg.WHITE, combinations.get(1).get(0));
        assertEquals(Peg.WHITE, combinations.get(1).get(1));
        assertEquals(Peg.BLUE, combinations.get(1).get(2));

        assertEquals(Peg.WHITE, combinations.get(2).get(0));
        assertEquals(Peg.BLUE, combinations.get(2).get(1));
        assertEquals(Peg.WHITE, combinations.get(2).get(2));

        assertEquals(Peg.WHITE, combinations.get(3).get(0));
        assertEquals(Peg.BLUE, combinations.get(3).get(1));
        assertEquals(Peg.BLUE, combinations.get(3).get(2));

        assertEquals(Peg.BLUE, combinations.get(4).get(0));
        assertEquals(Peg.WHITE, combinations.get(4).get(1));
        assertEquals(Peg.WHITE, combinations.get(4).get(2));

        assertEquals(Peg.BLUE, combinations.get(5).get(0));
        assertEquals(Peg.WHITE, combinations.get(5).get(1));
        assertEquals(Peg.BLUE, combinations.get(5).get(2));

        assertEquals(Peg.BLUE, combinations.get(6).get(0));
        assertEquals(Peg.BLUE, combinations.get(6).get(1));
        assertEquals(Peg.WHITE, combinations.get(6).get(2));

        assertEquals(Peg.BLUE, combinations.get(7).get(0));
        assertEquals(Peg.BLUE, combinations.get(7).get(1));
        assertEquals(Peg.BLUE, combinations.get(7).get(2));
    }

    @Test
    public void test_initial_3() {

        List<List<Peg>> combinations = ComboMaker.initialCombos(Arrays.asList(Peg.WHITE, Peg.BLUE, Peg.RED), 4);
    }

}