package com.adamkoch.mastermind;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * <p>Created by aakoch on 2017-07-31.</p>
 *
 * @author aakoch
 * @since 1.0.0
 */
public class RandomUtils {
    private static Random RANDOM = new Random();

    public static <T> T removeRandom(List<T> list) {
        return list.remove(RANDOM.nextInt(list.size()));
    }

    public static <T> T getRandom(List<T> list) {
        return list.get(RANDOM.nextInt(list.size()));
    }

    public static <T> List<T> getRandom(List<T> list, int numberOfElements) {
        List<T> randomizedList = new ArrayList<>(list);
        Collections.shuffle(randomizedList);
        return randomizedList.subList(0, numberOfElements);
    }
}
