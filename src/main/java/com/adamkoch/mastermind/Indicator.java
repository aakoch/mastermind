package com.adamkoch.mastermind;

/**
 * <p>Created by aakoch on 2017-08-09.</p>
 *
 * @author aakoch
 * @since 1.0.0
 */
public enum Indicator {
    CORRECT_COLOR("white"),
    CORRECT_COLOR_AND_PLACEMENT("red");

    private final String description;

    Indicator(String description) {

        this.description = description;
    }

    @Override
    public String toString() {
        return description;
    }
}
