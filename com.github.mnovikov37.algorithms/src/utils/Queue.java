package utils;

/**
 * Очередь FIFO
 * @param <T> Тип объектов, которыми будет заполняться очередь
 */
public interface Queue<T> {
    boolean add(T object);
    T remove();
    T peek();
    int size();
    boolean isEmpty();
    boolean isFull();
}
