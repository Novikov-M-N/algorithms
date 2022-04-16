/**
 * Дорога - содержит данные о длине и о стоимости проезда.
 * Оба показателя являются метриками при использовании
 * класса данных в составе графа.
 * Каждая дорога имеет своё наименование.
 */
public class Road implements Measurable{
    private String name;
    private final int length;
    private int fare;
    private int hashCode = 0;

    public Road(String name, int length, int fare) {
        this.name = name;
        this.length = length;
        this.fare = fare;
    }

    @Override
    public int getMetric(int i) {
        switch (i) {
            case 1 -> { return length; }
            case 2 -> { return fare; }
            default -> throw new IllegalStateException("Unexpected value: " + i);
        }
    }

    public boolean equals(Object o) {
        if (o == this) { return true; }
        if (o == null || o.getClass() != Road.class) { return false; }
        Road road = (Road) o;
        return road.length == this.length && road.fare == this.fare;
    }

    public int hashCode() {
        if (hashCode == 0) {
            hashCode = 31 * name.hashCode() + length;
            hashCode = 31 * hashCode + fare;
        }
        return hashCode;
    }

    public String toString() {
        return "Road:{name=" + name + ", length=" + length + ", fare=" + fare + "}";
    }
}
