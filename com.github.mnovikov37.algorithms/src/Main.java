import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // 1. Написать программу по возведению числа в степень с помощью рекурсии
        System.out.println(ToPower.toPower(2, 8));
        System.out.println(ToPower.toPower(10, -8));

        // 2. Написать программу «Задача о рюкзаке» с помощью рекурсии
        final int ITEM_COUNT = 10;
        final int BAG_CAPACITY = 20;

        Bag bag = new Bag(BAG_CAPACITY);
        List<Item> items = new ArrayList<>();
        for (int i = 0; i < ITEM_COUNT; i++) {
            items.add(new Item("Item" + i));
            System.out.println(items.get(i));
        }

        BagCollector collector = new BagCollector(bag, items);
        collector.loadToBag();

        System.out.println(bag);
    }
}
