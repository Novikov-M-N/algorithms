package domain;

/**
 * Собственно, ноутбук
 */
public class Notebook implements Comparable<Notebook> {
    // Параметры, по которым будем сортировать
    private int price;
    private int memorySize;
    private Manufacturer manufacturer;

    public Notebook(int price, int memorySize, Manufacturer manufacturer) {
        this.price = price;
        this.memorySize = memorySize;
        this.manufacturer = manufacturer;
    }

    @Override
    public int compareTo(Notebook o) {
        int result = price - o.getPrice();
        if (result == 0) {
            result = memorySize - o.getMemorySize();
            if (result == 0) {
                result = manufacturer.compareTo(o.getManufacturer());
            }
        }

        return result;
    }

    public int getPrice() {
        return price;
    }

    public int getMemorySize() {
        return memorySize;
    }

    public Manufacturer getManufacturer() {
        return manufacturer;
    }

    public String toString() {
        return "Notebook{price=" + price + ", memory=" + memorySize + ", manufacturer=" + manufacturer.getName() + "}";
    }
}
