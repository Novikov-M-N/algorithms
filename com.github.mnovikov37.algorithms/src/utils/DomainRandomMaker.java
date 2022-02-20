package utils;

/**
 * Интерфейс создания экземпляра класса
 * @param <T> Класс создаваемого объёкта
 */
public interface DomainRandomMaker<T> {
    T make();
}
