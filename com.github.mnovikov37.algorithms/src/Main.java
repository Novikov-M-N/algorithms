import java.util.List;

public class Main {
    // Константы для метрик оптимального пути: либо по расстоянию, либо по стоимости.
    private static final int RANGE = 1;
    private static final int COST = 2;

    public static void main(String[] args) {

        // Создание экземпляра дорожной карты и наполнение данными
        Graph<City, Road> roadAtlas = new GraphImplDijkstraBidirectional<>();

        City moscow = new City("Москва");
        City tula = new City("Тула");
        City ryazan = new City("Рязань");
        City kaluga = new City("Калуга");
        City lipetsk = new City("Липецк");
        City tambov = new City("Тамбов");
        City orel = new City("Орёл");
        City saratov = new City("Саратов");
        City kursk = new City("Курск");
        City voronezh = new City("Воронеж");

        roadAtlas.addVertex(moscow);
        roadAtlas.addVertex(tula);
        roadAtlas.addVertex(ryazan);
        roadAtlas.addVertex(kaluga);
        roadAtlas.addVertex(lipetsk);
        roadAtlas.addVertex(tambov);
        roadAtlas.addVertex(orel);
        roadAtlas.addVertex(saratov);
        roadAtlas.addVertex(kursk);
        roadAtlas.addVertex(voronezh);

        roadAtlas.addEdge(moscow, tula, new Road("001",184, 300));
        roadAtlas.addEdge(moscow, ryazan, new Road("002", 201, 200));
        roadAtlas.addEdge(moscow, kaluga, new Road("003", 190, 100));
        roadAtlas.addEdge(tula, lipetsk, new Road("004", 278, 300));
        roadAtlas.addEdge(ryazan, tambov, new Road("005", 291, 100));
        roadAtlas.addEdge(kaluga, orel, new Road("006", 217, 100));
        roadAtlas.addEdge(lipetsk, voronezh, new Road("007", 135, 300));
        roadAtlas.addEdge(tambov, saratov, new Road("008", 381, 100));
        roadAtlas.addEdge(orel, kursk, new Road("009", 172, 100));
        roadAtlas.addEdge(saratov, voronezh, new Road("010", 507, 100));
        roadAtlas.addEdge(kursk, voronezh, new Road("011", 230, 100));

        roadAtlas.display();

        // Вычисление оптимального пути по расстоянию либо по стоимости.
        int metricIndex = COST;

        List<Road> bestWay = roadAtlas.getBestWay(moscow, voronezh, metricIndex);
        int metric = 0;
        for (Road road : bestWay) {
            System.out.println(road);
            metric += road.getMetric(metricIndex);
        }
        System.out.println("Summary: " + metric);

    }
}
