public class DoubleLinkedListDequeue<E> implements Dequeue<E> {

    private final DoubleLinkedList<E> storage;

    public DoubleLinkedListDequeue() {
        this.storage = new DoubleLinkedListImpl<>();
    }

    @Override
    public boolean addFirst(E value) {
        storage.addFirst(value);
        return true;
    }

    @Override
    public boolean addLast(E value) {
        storage.addLast(value);
        return true;
    }

    @Override
    public E removeFirst() {
        return storage.removeFirst();
    }

    @Override
    public E removeLast() {
        return storage.removeLast();
    }

    @Override
    public boolean add(E object) {
        return addFirst(object);
    }

    @Override
    public E remove() {
        return removeFirst();
    }

    @Override
    public E peek() {
        return storage.getFirst();
    }

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    public boolean isEmpty() {
        return storage.isEmpty();
    }

    @Override
    public boolean isFull() {
        return false;
    }

    public String toString() {
        return storage.toString();
    }
}
