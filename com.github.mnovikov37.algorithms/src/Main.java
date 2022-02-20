import domain.Notebook;
import utils.*;

import java.util.Arrays;

public class Main {
    private final static int NOTEBOOK_COUNT = 10000;

    public static void main(String[] args) {
        // Забиваем в памяти массив под наши ноутбуки
        Notebook[] notebooks = new Notebook[NOTEBOOK_COUNT];

        // Заполняем массив случайными ноутбуками с параметрами в заданных пределах
        // с помощью мейкера
        DomainRandomMaker<Notebook> notebookMaker = new NotebookMaker();
        for (int i = 0; i < NOTEBOOK_COUNT; i++) {
            notebooks[i] = notebookMaker.make();
        }

        // Сортируем массив ноутбуков сортировщиком
        Sorter sorter = new ComboSorter();
        sorter.sort(notebooks);

        // Вывод результата в консоль
        for (Notebook notebook: notebooks) {
            System.out.println(notebook);
        }

        // Дополнительное задание - оптимизированная сортировка выбором: поиск максимального и минимального значений
        // на одном проходе
        Integer[] arr = new Integer[] {9, 10, 2, 4, 1, 7, 6, 3, 8, 5};
        Sorter adv = new AdvancedSelectionSorter();
        adv.sort(arr);
        System.out.println(Arrays.toString(arr));
    }
}
