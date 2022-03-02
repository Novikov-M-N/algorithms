import java.util.ListIterator;

public class Main {
    public static void main(String[] args) {
        DoubleLinkedListImpl<Integer> list = new DoubleLinkedListImpl<>();

//        System.out.println(list.size());
//        System.out.println(list.isEmpty());

        ListIterator<Integer> iterator = list.iterator();
//        iterator.add(11);

        list.addFirst(1);
        list.addFirst(2);
        list.addFirst(3);
        list.addFirst(4);
        list.addFirst(5);

//        System.out.println(list.size());
//        System.out.println(list.isEmpty());

        list.addLast(6);
        list.addLast(7);
        list.addLast(8);
        list.addLast(9);
        list.addLast(10);

//        System.out.println(list.size());

        System.out.println(list);

//        System.out.println(list.getFirst());
//        System.out.println(list.getLast());
//
//        System.out.println(list.remove(4) + " " + list);
//        System.out.println(list.remove(1) + " " + list);
//        System.out.println(list.remove(21) + " " + list);
//        System.out.println(list.remove(4) + " " + list);
//        System.out.println(list.remove(10) + " " + list);
//
//        System.out.println(list.contains(5));
//        System.out.println(list.contains(2));
//        System.out.println(list.contains(4));
//        System.out.println(list.contains(1));
//        System.out.println(list.contains(21));
//        System.out.println(list.contains(10));

//        System.out.println(list.removeFirst());
//        System.out.println(list.removeFirst());
//        System.out.println(list.removeFirst());
//        System.out.println(list.removeFirst());
//        System.out.println(list.removeFirst());
//
//        System.out.println(list.removeLast());
//        System.out.println(list.removeLast());
//        System.out.println(list.removeLast());

//        for (Integer integer: list) {
//            System.out.println(integer);
//        }

//        System.out.println(list.contains(2));
//        System.out.println(list.contains(22));

//        ListIterator<Integer> iterator = list.iterator();


        iterator.add(11);
        for (Integer value : list) {
                System.out.println(value + " next: " + iterator.hasNext() + " " + iterator.nextIndex() + " " + iterator.previousIndex() + " " + iterator.next());
        }

//        iterator.remove();
        iterator.add(22);

//        for (Integer value : list) {
//            System.out.println(value + " prev: " + iterator.hasPrevious() + " " + iterator.nextIndex() + " " + iterator.previousIndex() + " " + iterator.previous());
//        }
//
//        iterator.remove();
//
//        for (Integer value : list) {
//            System.out.println(value + " next: " + iterator.hasNext() + " " + iterator.nextIndex() + " " + iterator.previousIndex() + " " + iterator.next());
//        }
//
//        iterator.remove();
//
//        for (Integer value : list) {
//            System.out.println(value + " prev: " + iterator.hasPrevious() + " " + iterator.nextIndex() + " " + iterator.previousIndex() + " " + iterator.previous());
//        }
//
//        iterator.remove();
//
//        for (Integer value : list) {
//            System.out.println(value + " next: " + iterator.hasNext() + " " + iterator.nextIndex() + " " + iterator.previousIndex() + " " + iterator.next());
//        }
//
//        iterator.remove();


        System.out.println(list);
    }
}
