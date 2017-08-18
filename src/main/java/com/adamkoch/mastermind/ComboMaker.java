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
