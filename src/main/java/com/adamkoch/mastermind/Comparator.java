package com.adamkoch.mastermind;

/**
 * <p>Created by aakoch on 2017-08-10.</p>
 *
 * @author aakoch
 * @since 1.0.0
 */
public class Comparator {

    public static boolean equals(Indicator[] expected, Indicator[] actual) {
        if (expected.length != actual.length) {
            return false;
        }

        for (int index = 0; index < expected.length; index++) {
            if (expected[index] != actual[index]) {
                return false;
            }
        }

        return true;
    }

}
