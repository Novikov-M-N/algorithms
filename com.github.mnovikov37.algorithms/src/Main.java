import java.util.ListIterator;

public class Main {
    public static void main(String[] args) {

        // 1. Реализовать Deque
        Dequeue<Integer> dequeue = new DoubleLinkedListDequeue<>();

        for (int i = 0; i < 5; i++) {
            dequeue.addFirst(4 - i);
        }

        for (int i = 0; i < 5; i++) {
            dequeue.addLast(5 + i);
        }

        System.out.println(dequeue);

        System.out.println(dequeue.removeFirst() + " " + dequeue.removeLast());

        System.out.println(dequeue);

        // 2. Реализовать итератор

        DoubleLinkedListImpl<Integer> list = new DoubleLinkedListImpl<>();

        for (int i = 0; i < 10; i++) {
            list.addLast(i);
        }

        ListIterator<Integer> iterator = list.iterator();

        System.out.println(list);

        // Проверка работы итератора опосредованно через цикл foreach
        System.out.println("Foreach:");
        for (Integer integer: list) {
            System.out.println(integer);
        }

        // Проверка работы итератора напрямую
        iterator.add(11);
        System.out.println(list);

        // Попытка вызвать remove() после add() без вызова next() или previous() - исключение
        try {
            iterator.remove();
        } catch (IllegalStateException e) {
            System.out.println(e.getMessage());
        }
        System.out.println(list);

        System.out.println("next=" + iterator.next() + ", next index=" + iterator.nextIndex()
                + ", previous index =" + iterator.previousIndex());
        iterator.add(22);
        System.out.println(list);

        System.out.println("previous=" + iterator.previous() + ", next index=" + iterator.nextIndex()
                + ", previous index =" + iterator.previousIndex());
        iterator.remove();
        System.out.println(list);

        iterator.set(33);
        System.out.println(list);

        // 3. Реализовать метод insert в классе списка
        list.insert(1, 66);
        System.out.println(list);
    }
}
