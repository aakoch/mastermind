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

//        final Stream<List<T>> lists;
        if (size == list.size()) {
//            return list.stream().map(item -> {
//                List<T> newList = new ArrayList<>();
//                newList.add(item);
//                return newList;
//            });
            return Stream.of(list);
        }
        else {
            Stream<List<T>> lists = null;

            Stream<List<T>> sublist = initialCombosStream(list, size - 1);

//            return sublist.map(list1 -> {
//                List<T> newList = new ArrayList<>();
//                for (T item : list) {
//                    newList.add(item);
//                    newList.addAll(list1);
//                }
//                return newList;
//            });

            return sublist.map(list1 -> {
                List<T> newList = new ArrayList<>();
                for (T item : list) {
                    list1.add(0, item);
                    newList.addAll(list1);
                }
                return newList;
            });


//                lists = Streams.concat(Stream.of(Arrays.asList(item)), sublist);

//            list.forEach(item -> {
//                sublist.forEach(sublistList -> {
//                    lists = Stream.concat();
//                });
//            });

//            return lists;

//            sublist.stream().flatMap(list1 -> list1.stream()).map(a -> {
//
//                List<T> newList = new ArrayList<>();
//                newList.add(item);
//                newList.addAll(sublistList);
//                return newList;
//            });
//
//            final Stream<Stream<List<T>>> streamStream1 = list.stream().map(item -> {
//
//                return sublist.stream().map(sublistList -> {
//
//                    List<T> newList = new ArrayList<>();
//                    newList.add(item);
//                    newList.addAll(sublistList);
//                    return newList;
//                });
//
//            });
//
//            final Stream<Stream<List<T>>> streamStream = list.stream().map(item -> {
//
//                final Stream<List<T>> listStream = sublist.stream().map(sublistList -> {
//                    List<T> newList = new ArrayList<>();
//                    newList.add(item);
//                    newList.addAll(sublistList);
//                    return newList;
//                });
//                return listStream;
//            });
//
//
//
//            list.stream().forEach(item -> {
//                sublist.stream().forEach(sublistList -> {
//                    lists.add(Streams.concat(Stream.of(item), sublistList.stream()));
//                });
//            });

        }

//        return lists;
    }

    public static <T> List<List<T>> initialCombos(List<T> list, int size) {

        if (size == 1) {
//            list.forEach(item -> {
////                List<T> newList = new ArrayList<>();
////                newList.add(item);
////                lists.add(newList);
//                lists.add(Arrays.asList(item));
//            });
            return list.stream().map(Arrays::asList).collect(Collectors.toList());
        }
        else {

            List<List<T>> lists = new ArrayList<>();

            List<List<T>> sublist = initialCombos(list, size - 1);
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
