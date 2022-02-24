import utils.HoleFinder;
import utils.LeakyArrayMaker;
import utils.LoopQueue;
import utils.Queue;

public class Main {
    public static void main(String[] args) {

        // Задание 1 - нахождение пропущенного элемента в последовательности.
        // Алгоритм по типу бинарного поиска, сложность O(log n)
        for (int i = 0; i < 1; i++) {
            Integer[] array = LeakyArrayMaker.make(50000);
            System.out.println("->" + HoleFinder.find(array));
        }

        // Задание 2 - закольцованная очередь
        Queue<Integer> queue = new LoopQueue<>(10);
        for (int i = 0; i < 30; i++) {
            queue.add(i);
            System.out.println(queue);
            if (i % 2 == 0) {
                System.out.println(queue.remove());
                System.out.println(queue.peek());
            }
        }
    }
}
