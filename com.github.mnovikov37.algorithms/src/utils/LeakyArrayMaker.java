package utils;

import java.util.Random;

/**
 * Класс создаёт массив последовательных данных заданной длины
 * с необязательным пропуском одного случайного элемента
 */
public class LeakyArrayMaker {
    public static Integer[] make(int length) {
        Integer[] result = new Integer[length];
        Random rnd = new Random();
        int holeIndex = rnd.nextInt(length + 1);
        for (int i = 0, value = 1; i < holeIndex; i++, value++) {
            result[i] = value;
        }
        for (int i = holeIndex, value = holeIndex + 2; i < length; i++, value++) {
            result[i] = value;
        }
        return result;
    }
}
