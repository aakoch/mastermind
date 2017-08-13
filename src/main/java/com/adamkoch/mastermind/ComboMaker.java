package com.adamkoch.mastermind;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <p>Created by aakoch on 2017-08-10.</p>
 *
 * @author aakoch
 * @since 1.0.0
 */
public class ComboMaker {

    public static <T> List<List<T>> initialCombos(List<T> list, int size) {

        List<List<T>> lists = new ArrayList<>();
        if (size == 1) {
            for (T item : list) {
                List<T> newList = new ArrayList<>();
                newList.add(item);
                lists.add(newList);
            }
        }
        else {

            List<List<T>> sublist = initialCombos(list, size - 1);

            for (T item : list) {

                for (List<T> sublistList : sublist) {
                    List<T> newList = new ArrayList<>();
                    newList.add(item);
                    newList.addAll(sublistList);

                    lists.add(newList);
                }
            }
        }

        return lists;
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
