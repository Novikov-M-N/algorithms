package utils;

import java.util.Arrays;

/**
 * Интерфейс сортировщика.
 */
public interface Sorter {

    // По умолчанию в сортировщик заряжаем самую примитивную сортировку - пузырёк
    default <T extends Comparable<T>> void sort(T[] arr) {
        int limit = arr.length;
        boolean wasSwap = true;
        while (limit > 1 && wasSwap) {
            wasSwap = false;
            for (int i = 0, j = 1; j < limit; i++, j++) {
                if (arr[i].compareTo(arr[j]) > 0) {
                    T tmp = arr[i];
                    arr[i] = arr[j];
                    arr[j] = tmp;
                    wasSwap = true;
                }
                System.out.println(limit + ":" + Arrays.toString(arr));
            }
            limit--;
        }
    }
}
