package utils;

/**
 * Реализация закольцованной очереди
 * @param <T> Тип объектов, которыми будет заполняться очередь
 */
public class LoopQueue<T> implements Queue<T> {

    private T[] storage;
    private final int capacity; // Максимальная ёмкость очереди
    private int first;
    private int last;
    private int size;           // Фактическое количество элементов в очереди

    public LoopQueue(int capacity) {
        this.capacity = capacity;
        storage = (T[]) new Object[capacity];
        first = 0;
        last = capacity - 1;
        size = 0;
    }

    @Override
    public boolean add(T object) {
        boolean result = ! isFull();
        if (result) {
            last = increment(last);
            storage[last] = object;
            size++;
        }
        return result;
    }

    @Override
    public T remove() {
        T result = null;
        if (! isEmpty()) {
            result = storage[first];
            first = increment(first);
            size--;
        }
        return result;
    }

    @Override
    public T peek() {
        return isEmpty() ? null : storage[first];
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean isFull() {
        return size == capacity;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        int index = first;
        for (int i = 0; i < size; i++) {
            sb.append(storage[index].toString());
            if (i != size - 1) {
                sb.append(", ");
            }
            index = increment(index);
        }
        sb.append("]");
        return sb.toString();
    }

    /**
     * Приращение индекса внутреннего массива. Обеспечивает логику закольцовки очереди
     * @param i Текущее значение индекса
     * @return Новое значение индекса с учётом закольцовки
     */
    private int increment(int i) {
        return ++i == capacity ? 0 : i;
    }
}
