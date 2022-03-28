public class City {
    private final String name;

    public City(String name) {
        this.name = name;
    }

    public boolean equals(Object o) {
        if (o == this) { return true; }
        if (o == null || o.getClass() != City.class) { return false; }
        City city = (City) o;
        return city.name.equals(this.name);
    }

    public String toString() {
        return "City: {name=" + name + "}";
    }
}
