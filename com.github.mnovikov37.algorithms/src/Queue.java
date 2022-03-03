/**
 * Очередь FIFO
 * @param <E> Тип объектов, которыми будет заполняться очередь
 */
public interface Queue<E> {
    boolean add(E object);
    E remove();
    E peek();
    int size();
    boolean isEmpty();
    boolean isFull();
}