package domain;

/**
 * Производители ноутбуков. Порядок перечисления соответствует порядку сортировки
 */
public enum Manufacturer {
    XAMIOU("Xamiou"),
    ESER("Eser"),
    MACNOTE("MacNote"),
    ASOS("Asos"),
    LENUVO("Lenuvo");

    public String getName() {
        return name;
    }

    private String name;

    Manufacturer(String name) {
        this.name = name;
    }
}
