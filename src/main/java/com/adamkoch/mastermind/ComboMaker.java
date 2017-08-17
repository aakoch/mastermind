package com.adamkoch.mastermind;

import com.google.common.collect.Streams;
import sun.management.resources.agent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;
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
            List<List<T>> sublist = initialCombos(list, size - 1);

            List<List<T>> lists = new ArrayList<>();

            list.forEach(item -> {
                sublist.forEach(sublistList -> {
                    List<T> newList = new ArrayList<>();
                    newList.add(item);
                    newList.addAll(sublistList);

                    lists.add(newList);
                });
            });

            return lists.stream();
        }
    }

    public static <T> List<List<T>> initialCombos(List<T> list, int size) {

        if (size == 1) {
            return list.stream().map(Arrays::asList).collect(Collectors.toList());
        }
        else {
            List<List<T>> sublist = initialCombos(list, size - 1);

            List<List<T>> lists = new ArrayList<>();

            list.forEach(item -> {
                sublist.forEach(sublistList -> {
                    List<T> newList = new ArrayList<>();
                    newList.add(item);
                    newList.addAll(sublistList);

                    lists.add(newList);
                });
            });

            return lists;
        }

    }

    private static <T> List<List<T>> combos(List<T> list) {
        List<List<T>> lists = new ArrayList<>();

        if (list.size() == 1) {
            lists.add(list);
        }
        else {
            for (int i = 0; i < list.size(); i++) {
                List<T> listMinusElement = new ArrayList<>(list);
                listMinusElement.remove(i);
                List<List<T>> combos = combos(listMinusElement);

                for (List<T> comboList : combos) {
                    List<T> temporaryList = new ArrayList<>();
                    temporaryList.add(list.get(i));
                    temporaryList.addAll(comboList);
                    if (!lists.contains(temporaryList)) {
                        lists.add(temporaryList);
                    }
                }
            }
        }

        return lists;
    }

    public static List<List<Peg>> combosOf(Peg... peg) {
        return combos(Arrays.asList(peg));
    }

}
