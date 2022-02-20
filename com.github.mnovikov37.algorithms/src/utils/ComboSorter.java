package utils;

/**
 * Класс-сортировщик, использующий алгоритм сортировки расчёской
 */
public class ComboSorter implements Sorter{
    private static final double FACTOR = 1.247;

    @Override
    public <T extends Comparable<T>> void sort(T[] arr) {
        int jump = arr.length - 1;
        while (jump > 0) {
            for (int i = 0, j = jump; j < arr.length; i++, j++) {
                if (arr[i].compareTo(arr[j]) > 0) {
                    T tmp = arr[i];
                    arr[i] = arr[j];
                    arr[j] = tmp;
                }
            }
            jump = (int) (jump / FACTOR);
        }
    }
}
