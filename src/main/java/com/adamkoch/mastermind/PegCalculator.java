package com.adamkoch.mastermind;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * <p>Created by aakoch on 2017-08-12.</p>
 *
 * @author aakoch
 * @since 1.0.0
 */
public class PegCalculator {
    public static int calculateSameColor(List<Peg> pegs1, List<Peg> pegs2) {
        int count = 0;
        Map<Peg, Long> map1 = pegs1.stream()
                                   .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        Map<Peg, Long> map2 = pegs2.stream()
                                   .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        for (Map.Entry<Peg, Long> pegLongEntry : map1.entrySet()) {
            if (map2.containsKey(pegLongEntry.getKey())) {
                count += Math.min(pegLongEntry.getValue().intValue(), map2.get(pegLongEntry.getKey()).intValue());
            }
        }

        return count;
    }

    public static int calculateSameColorAndSamePlace(List<Peg> pegs1, List<Peg> pegs2) {
        int count = 0;

        for (int i = 0; i < pegs1.size(); i++) {
            if (pegs1.get(i) == pegs2.get(i)) {
                count++;
            }
        }

        return count;
    }
}
