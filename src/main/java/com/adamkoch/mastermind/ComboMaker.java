package com.adamkoch.mastermind;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 * <p>Created by aakoch on 2017-08-10.</p>
 *
 * @author aakoch
 * @since 1.0.0
 */
public class ComboMaker {

    public static <T> Stream<List<T>> initialCombosStream(List<T> list, int size) {

        if (size == 1) {
            return list.stream().map(Arrays::asList);
        }
        else {

            return list.stream().flatMap(item -> initialCombosStream(list, size - 1).map(sublistList -> {
                List<T> newList = new ArrayList<>();
                newList.add(item);
                newList.addAll(sublistList);

                return newList;
            }));
        }
    }

}
