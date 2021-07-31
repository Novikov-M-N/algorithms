import java.util.Scanner;

/**
 * Требуется написать метод, принимающий на вход размеры двумерного массива
 * и выводящий массив в виде инкременированной цепочки чисел, идущих по спирали.
 */
public class Spiral {
    private static int horizontalSize;
    private static int verticalSize;

    // Функцию ввода исходных данных делаем без защиты от дурака - суть задания не в этом.
    private static void inputDimensions() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите размер матрицы по горизонтали:");
        horizontalSize = scanner.nextInt();
        System.out.println("Введите размер матрицы по вертикали:");
        verticalSize = scanner.nextInt();
    }
    
    public static void main(String[] args) {
        inputDimensions();
        System.out.println(horizontalSize + "x" + verticalSize);
        if(horizontalSize < 1 || verticalSize < 1) {
            System.out.println("Ошибка!");
            return;
        }

        // Определяем, сколько чисел всего нам нужно будет вывести на экран
        // и находим их максимальную разрядность для заполнения разрядов нулями
        // в соответствии с заданием
        int countOfNumbers = horizontalSize * verticalSize;
        int numberCapacity = 0;
        for (int i = countOfNumbers; i > 0; i /= 10) {
            numberCapacity++;
        }
        
        // Итоговая матрица, которую будем заполнять и выводить на экран
        int[][] A = new int[horizontalSize][verticalSize];

        // Суть алгоритма в том, чтобы расставить по порядку числа от 1 до countOfNumbers,
        // перемещаясь внутри двумерного массива по спирали. Для этого нужно определённым образом
        // менять счётчики двумерного массива. Для этого нам понадобятся следующие значения:

        // Начальная позиция. Начинаем всегда с A[0][0]
        int verticalPosition = 0;
        int horizontalPosition = 0;

        // Границы, вунтри которых будут меняться счётчики. Границы не включительные.
        int leftBoard = -2; // Такое значение объяснено ниже
        int topBoard = -1;
        int rightBoard = horizontalSize;
        int bottomBoard = verticalSize;

        // Первоначальные направления, в которых будем двигаться по массиву.
        // Они есть приращения счётчиков на каждом шаге: инкремент, декремент, либо без изменений.
        // Изначально двигаемся вправо по горизонтали, оставаясь на месте по вертикали.
        int horizontalStep = 1;
        int verticalStep = 0;

        // Спираль в прямоугольной дискретной системе координат есть движение с изменением
        // направления на 90 градусов при достижении границы и сужением этих границ
        // при каждом повороте.
        for (int k = 1; k <= countOfNumbers; k++) {

            // Заносим число в матрицу
            A[horizontalPosition][verticalPosition] = k;

            // Прибавляем к координатам их приращения - перемещаемся к следующей ячейке спирали
            horizontalPosition += horizontalStep;
            verticalPosition += verticalStep;

            // Проверяем условие достижения границы. Если залезли на границу, возвращаемся назад
            // и делаем шаг уже в новом направлении. Изменяем приращения для счётчиков
            // в соответствии с новым направлением. Для каждой границы известно направление
            // дальнейшего движения.
            // Так же смещаем противоположную границу. На первом повороте левая граница
            // встаёт на своё место (-1), именно поэтому изначально ей задано
            // значение -2.
            if (horizontalPosition == leftBoard) {
                // Отскок назад и шаг в новом направлении
                horizontalPosition++;
                verticalPosition--;

                // Новые шаги приращения
                horizontalStep = 0;
                verticalStep = -1;

                // Сужение границ
                rightBoard--;
            }
            if(verticalPosition == topBoard) {
                verticalPosition++;
                horizontalPosition++;
                horizontalStep = 1;
                verticalStep = 0;
                bottomBoard--;
            }
            if (horizontalPosition == rightBoard) {
                horizontalPosition--;
                verticalPosition++;
                horizontalStep = 0;
                verticalStep = 1;
                leftBoard++;
            }
            if(verticalPosition == bottomBoard) {
                verticalPosition--;
                horizontalPosition--;
                horizontalStep = -1;
                verticalStep = 0;
                topBoard++;
            }
        }

        // Вывод на экран итоговой матрицы
        for (int i = 0; i <verticalSize ; i++) {
            for (int j = 0; j < horizontalSize; j++) {
                System.out.printf("%0" + numberCapacity + "d", A[j][i]);
                if (j == horizontalSize -1) {
                    System.out.println();
                } else {
                    System.out.print(" ");
                }
            }
        }
        
    }
}
