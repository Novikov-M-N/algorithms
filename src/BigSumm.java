import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Реализовать функцию a+b, где каждое из чисел а и b имеет не менее 1000 разрядов.
 * Числа хранятся в файле chisla.txt на двух строчках. Ответ вписать на третью строчку.
 * Использовать BigInteger не допускается.
 */
public class BigSumm {
    // Объявляем файл в соответствиии с заданием
    private final static File FILE = new File("chisla.txt");

    // Функция для генерации длинной последовательности цифр
    // Генерирует исходные данные
    private static List<Integer> getBigNumber(int capacity) {
        List<Integer> result = new ArrayList<>(capacity);
        result.add(1 + (int)(Math.random() * 9));
        for (int i = 1; i < capacity; i++) {
            result.add((int)(Math.random() * 10));
        }
        return result;
    }

    // Функция записывает в файл последовательность чисел в виде строки
    private static void printlnListToFile(List<Integer> list) {
        try(FileWriter writer = new FileWriter(FILE, true)) {
            for (Integer digit : list) {
                writer.append(String.valueOf(digit));
            }
            writer.append('\n');
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        // Генерируем исходные данные в соответствии с заданием и записываем в файл
        // Предполагается, что изначально файл пустой.
        printlnListToFile(getBigNumber(1100));
        printlnListToFile(getBigNumber(1150));

        // Считываем исходные данные из файла поразрядно, как две последовательности цифр
        // Так как мы не знаем изначально разрядность чисел в исходных данных, то используем ArrayList,
        // но заранее задаём его ёмкость большей, чем минимальное требование к разрядности числа в задании.
        // Так избавимся от заведомо лишних манипуляций с размером массива.
        // Если потребуется массив больше, ArrayList разберётся сам.
        List<Integer> number1 = new ArrayList<>(2000);
        List<Integer> number2 = new ArrayList<>(2000);
        try (FileReader reader = new FileReader(FILE)) {
            int c;
            while ((c = reader.read()) != '\n') {
                number1.add(Character.getNumericValue((char)c));
            }
            while ((c = reader.read()) != '\n') {
                number2.add(Character.getNumericValue((char)c));
            }

        } catch(IOException e) {
            e.printStackTrace();
        }

        // Для дальнейших вычислений потребуется знать, какое из входных чисел длиннее,
        // а так же длину длинного и короткого чисел.
        List<Integer> shortInputNumber;
        List<Integer> longInputNumber;

        if (number1.size() > number2.size()) {
            shortInputNumber = number2;
            longInputNumber = number1;
        } else {
            shortInputNumber = number1;
            longInputNumber = number2;
        }
        int minInputLength = shortInputNumber.size();
        int maxInputLength = longInputNumber.size();

        // Разрядность результата в худшем случае будет равна разрядности длинного числа плюс 1
        int resultCapacity = maxInputLength + 1;

        // Так как заранее знаем разрядность результата, используем простой массив.
        int[] result = new int[resultCapacity];

        // Дальнейший ход алгоритма представляет из себя простое поразрядное сложение двух чисел в столбик
        // Начиная со второго разряда, заполняем результирующий массив цифрами более длинного числа,
        // пока не пройдём всю разницу длины между входными числами
        // Первый разряд оставляем нулевым на случай, если разрядность результата превысит разрядность исходных данных
        for (int i = 1; i <= maxInputLength - minInputLength; i++) {
            result[i] = longInputNumber.get(i-1);
        }

        // После прохождения разницы в длине исходных данных складываем их поразрядно
        for (int i = maxInputLength - minInputLength + 1; i < resultCapacity; i++) {
            int sumDigit = longInputNumber.get(i - 1) + shortInputNumber.get(i - maxInputLength + minInputLength - 1);

            // Если результат поразрядного сложения превысит один разряд, раскладываем его на десятки и единицы
            // Единицы записываем в текущий разряд, а десятки прибавляем к предыдущему разряду
            // Т.к. число десятков в таком случае не может превысить одного, то используем инкремент
            if (sumDigit > 9) {
                result[i] = sumDigit % 10;
                int j = i - 1;
                result[j]++;

                // После инкремента предыдущего разряда, его разрядность может так же увеличиться,
                // поэтому идём по разрядам в обратную сторону и повторяем инкремент до тех пор,
                // пока разрядность не перестанет увеличиваться, т.е. пока в результате сложения у нас не получится
                // число, меньшее десяти.
                while (result[j] > 9){
                    result[j] = result[j] % 10;
                    j--;
                    result[j]++;
                }
            } else { result[i] = sumDigit; }
        }

        // Записываем результирующее число в файл
        try(FileWriter writer = new FileWriter(FILE, true)) {
            int iStart = 0;
            if (result[0] == 0) {
                iStart = 1;
            }
            for (int i = iStart; i < result.length; i++) {
                writer.append(String.valueOf(result[i]));
            }
            writer.append('\n');
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
