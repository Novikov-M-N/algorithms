/**
 * Город - содержит только название населённого пункта.
 */
public class City {
    private final String name;
    private int hashCode = 0;

    public City(String name) {
        this.name = name;
    }

    public boolean equals(Object o) {
        if (o == this) { return true; }
        if (o == null || o.getClass() != City.class) { return false; }
        City city = (City) o;
        return city.name.equals(this.name);
    }

    public int hashCode() {
        if (hashCode == 0) {
            hashCode = name.hashCode();
        }
        return hashCode;
    }

    public String toString() {
        return "City:{name=\"" + name + "\"}";
    }
}
