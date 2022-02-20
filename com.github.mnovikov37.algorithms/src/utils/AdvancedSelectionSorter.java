package utils;

/**
 * Класс-сортировщик, использующий оптимизированный алгоритм сортировки выбором
 */
public class AdvancedSelectionSorter implements Sorter{
    @Override
    public <T extends Comparable<T>> void sort(T[] arr) {
        int low = 0;
        int high = arr.length - 1;
        while (high - low > 0) {
            int min = low;
            int max = low;
            for (int i = low; i <= high; i++) {
                if (arr[i].compareTo(arr[min]) < 0) {
                    min = i;
                }
                if (arr[i].compareTo(arr[max]) > 0) {
                    max = i;
                }
            }
            // В случае, если максимальный элемент находится на нижней границе сортируемого диапазона,
            // а минимальный на верхней, обмен будет один - меняем местами крайние элементы сорируемого диапазона
            if (min == high && max == low) {
                T tmp = arr[low];
                arr[low] = arr[high];
                arr[high] = tmp;
            } else { // Во всех остальных случаях обменов будет два
                if (min != low) {
                    T tmp = arr[low];
                    arr[low] = arr[min];
                    arr[min] = tmp;
                }
                if (max != high) {
                    T tmp = arr[high];
                    arr[high] = arr[max];
                    arr[max] = tmp;
                }
            }
            low++;
            high--;
        }
    }
}
