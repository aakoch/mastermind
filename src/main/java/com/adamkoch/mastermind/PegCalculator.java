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

import com.google.common.collect.Streams;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * <p>Created by aakoch on 2017-08-12.</p>
 *
 * @author aakoch
 * @since 1.0.0
 */
public class PegCalculator {

    public int calculateSameColor(List<Peg> pegs1, List<Peg> pegs2) {

        final Map<Peg, Integer> map1 = countByPeg(pegs1);
        final Map<Peg, Integer> map2 = countByPeg(pegs2);

        return map1.keySet()
                   .stream()
                   .filter(map2::containsKey)
                   .mapToInt(peg -> Math.min(map1.get(peg), map2.get(peg)))
                   .sum();
    }

    public Map<Peg, Integer> countByPeg(List<Peg> pegs) {
        return pegs.stream()
                    .collect(Collectors.groupingBy(Function.identity(),
                            Collectors.reducing(0, e -> 1, Integer::sum)));
    }

    public <T> int calculateNumberOfEqualObjectsInSamePlace(List<T> list1, List<T> list2) {
        return (int) Streams.zip(list1.stream(), list2.stream(), Objects::equals).filter(Boolean::booleanValue).count();
    }
}
