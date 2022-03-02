public interface Dequeue<E> extends Queue<E>{
    boolean addFirst(E value);
    boolean addLast(E value);

    E removeFirst();
    E removeLast();
}
