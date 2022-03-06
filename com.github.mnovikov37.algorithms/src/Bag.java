import java.util.ArrayList;
import java.util.List;

/**
 * Рюкзак. Хранит вещи
 * Имеет ограничение по весу содержимого, умеет считать стоимость содержимого
 */
public class Bag {
    private final int capacity;
    private List<Item> storage;
    private int load;
    private int cost;

    public Bag(int capacity) {
        this.capacity = capacity;
        this.storage = new ArrayList<>();
    }

    /**
     * Помещает вещь в рюкзак
     * @param item Вещь, которую требуется поместить в рюкзак
     * @throws IllegalArgumentException В случае, если в результате помещения вещи в рюкзак будет превышена
     * максимальная масса содержимого для данного рюкзака
     */
    public void add(Item item) throws IllegalArgumentException{
        if (load + item.getWeight() > capacity) {
            throw new IllegalArgumentException("Cannot add item " + item + " to bag - overload");
        } else {
            storage.add(item);
            load += item.getWeight();
            cost += item.getPrice();
        }
    }

    /**
     * Опустошить рюкзак
     */
    public void clear() {
        storage.clear();
        load = 0;
        cost = 0;
    }

    public int getCapacity() { return capacity; }

    public String toString() {
        return "Bag:{capacity=" + capacity + ", load=" + load + ", cost=" + cost + ", storage=" + storage.toString() + "}";
    }
}
