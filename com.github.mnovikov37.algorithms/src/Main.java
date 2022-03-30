public class Main {
    private static final int RANGE = 1;
    private static final int COST = 2;

    public static void main(String[] args) {

        Graph<City, Road> roadAtlas = new GraphImpl<>();

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

        roadAtlas.addEdge(moscow, tula, new Road(184, 100));
        roadAtlas.addEdge(moscow, ryazan, new Road(201, 200));
        roadAtlas.addEdge(moscow, kaluga, new Road(190, 100));
        roadAtlas.addEdge(tula, lipetsk, new Road(278, 100));
        roadAtlas.addEdge(ryazan, tambov, new Road(291, 100));
        roadAtlas.addEdge(kaluga, orel, new Road(217, 100));
        roadAtlas.addEdge(lipetsk, voronezh, new Road(135, 100));
        roadAtlas.addEdge(tambov, saratov, new Road(381, 100));
        roadAtlas.addEdge(orel, kursk, new Road(172, 100));
        roadAtlas.addEdge(saratov, voronezh, new Road(507, 100));
        roadAtlas.addEdge(kursk, voronezh, new Road(230, 100));

        roadAtlas.display();

//        roadAtlas.getBestWay(moscow, voronezh, RANGE);

    }
}
