public interface DoubleLinkedList<E>{
    void addFirst(E value);
    void addLast(E value);
    E removeFirst();
    E removeLast();
    E getFirst();
    E getLast();
    boolean remove(E value);
    boolean contains(E value);
    int size();
    boolean isEmpty();

    class Node<E> {
        E item;
        Node<E> next;
        Node<E> prev;

        public Node(E item, Node<E> next, Node<E> prev) {
            this.item = item;
            this.next = next;
            this.prev = prev;
        }
    }
}
