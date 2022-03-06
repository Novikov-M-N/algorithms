import java.util.Random;

public class Item {
    private String name;
    private int weight;
    private int price;

    public Item(String name, int weight, int price) {
        this.name = name;
        this.weight = weight;
        this.price = price;
    }

    public Item(String name) {
        this.name = name;
        Random rnd = new Random();
        this.weight = rnd.nextInt(15) + 1;
        this.price = rnd.nextInt(19) * 5 + 10;
    }

    public String getName() { return name; }
    public int getPrice() { return price; }
    public int getWeight() { return weight; }

    public String toString() {
        return "Item:{name=" + name + ", weight=" + weight + ", price=" + price + "}";
    }
}
