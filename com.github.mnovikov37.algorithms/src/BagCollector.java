import java.util.ArrayList;
import java.util.List;

/**
 * Комплектовщик рюкзака. Загружает в рюкзак оптимальную выборку из исходного набора:
 * максимум цены при условии не превышения предельного веса содержимого конкретного рюкзака
 */
public class BagCollector {
    private int maxCost;            // Используется в качестве глобальной переменной при переборе вариантов загрузки
    private final Bag bag;
    private final List<Item> items;
    private List<Item> toLoad;      // Итоговый набор, который будет загружен в рюкзак

    public BagCollector(Bag bag, List<Item> items) {
        this.bag = bag;
        this.items = items;
        this.toLoad = new ArrayList<>();
    }

    public void loadToBag() {
        bag.clear();
        collect(items);
        load(toLoad);
    }

    private void collect(List<Item> items) {
        int cost = getCost(items);
        if (cost > maxCost) {   // Продолжать работу с наборами, стоимость которых меньше ранее найденного максимума, нет смысла
                                // т.к. при дальшейшем дроблении стоимость будет только уменьшаться
            if (getLoad(items) <= bag.getCapacity()) { // Если набор влезает в рюкзак, сразу принимаем его за результирующий
                maxCost = cost;
                toLoad = items;
            } else if (items.size() > 1) { // Дальнейшее дробление имеет смысл только для наборов более одного предмета
                for (int i = 0; i < items.size(); i++) {
                    // Рекурсивно повторяем поиск оптимального набора среди поднаборов, каждый раз исключая по одному элементу
                    collect(excludedList(items, i));
                }
            }
        }
    }

    /**
     * Загрузка набора предметов в рюкзак
     * @param items набор предметов, которые требуется загрузить в рюкзак
     */
    private void load(List<Item> items) {
        for (Item item : items) {
            bag.add(item);
        }
    }

    /**
     * Создаёт копию исходной коллекции, удаляя из неё элемент с заданным индексом
     * @param items Исходная коллекция
     * @param index Индекс элемента в исходной коллекции, который нужно исключить из результирующей коллекции
     * @return Результирующая коллекция
     */
    private List<Item> excludedList(List<Item> items, int index) {
        List<Item> result = new ArrayList<>(items);
        result.remove(index);
        return result;
    }

    /**
     * Подсчёт стоимости коллекции как суммарной цены всех входящих в неё предметов
     * @param items Коллекция, стоимость которой требуется посчитать
     * @return Суммарная стоимость всех предметов исходной коллекции
     */
    private int getCost(List<Item> items) {
        return items.stream().mapToInt(Item::getPrice).sum();
    }

    /**
     * Подсчёт суммарной массы коллекции как суммы масс всех входящих в неё предметов
     * @param items Коллекция, массу которой требуется посчитать
     * @return Суммарная масса всех предметов исходной коллекции
     */
    private int getLoad(List<Item> items) {
        return items.stream().mapToInt(Item::getWeight).sum();
    }
}
