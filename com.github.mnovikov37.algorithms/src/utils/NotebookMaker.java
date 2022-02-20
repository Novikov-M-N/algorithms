package utils;

import domain.Manufacturer;
import domain.Notebook;

import java.util.Random;

/**
 * Класс-создатель экземпляров класса Notebook
 */
public class NotebookMaker implements DomainRandomMaker<Notebook>{

    private Random rnd;

    // Допустимые параметры создаваемых ноутбуков - задаются по условию задачи
    private int minPrice = 500;
    private int maxPrice = 2000;
    private int priceStepInterval = 50;
    private int minMemorySize = 4;
    private int maxMemorySize = 24;
    private int memorySizeStepInterval = 4;

    public NotebookMaker() {
        rnd = new Random();
    }

    @Override
    public Notebook make() {
        int price = getNextRandomInt(minPrice, maxPrice, priceStepInterval);
        int memorySize = getNextRandomInt(minMemorySize, maxMemorySize, memorySizeStepInterval);
        Manufacturer manufacturer = Manufacturer.values()[rnd.nextInt(Manufacturer.values().length)];
        return new Notebook(price, memorySize, manufacturer);
    }

    public void setMinPrice(int minPrice) {
        this.minPrice = minPrice;
    }

    public void setMaxPrice(int maxPrice) {
        this.maxPrice = maxPrice;
    }

    public void setPriceStepInterval(int priceStepInterval) {
        this.priceStepInterval = priceStepInterval;
    }

    public void setMinMemorySize(int minMemorySize) {
        this.minMemorySize = minMemorySize;
    }

    public void setMaxMemorySize(int maxMemorySize) {
        this.maxMemorySize = maxMemorySize;
    }

    public void  setMemorySizeStepInterval(int memorySizeStepInterval) {
        this.memorySizeStepInterval = memorySizeStepInterval;
    }

    // Получение случайного значения в заданных пределах и с заданным интервалом
    private int getNextRandomInt(int min, int max, int stepInterval) {
        int stepsCount = (max - min) / stepInterval;
        if (min + stepsCount * stepInterval < max) {
            stepsCount+=2;
        } else {
            stepsCount++;
        }
        int result = rnd.nextInt(stepsCount) * stepInterval + min;
        if (result > max) {
            result = max;
        }
        return result;
    }
}
