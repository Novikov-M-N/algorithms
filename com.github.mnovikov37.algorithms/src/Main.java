import java.util.List;

public class Main {
    private static final int RANGE = 1;
    private static final int COST = 2;

    public static void main(String[] args) {

        Graph<City, Road> roadAtlas = new GraphImplDijkstraBidirectional<>();

        /*
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

         */

        City A = new City("A");
        City B = new City("B");
        City C = new City("C");
        City D = new City("D");
        City E = new City("E");
        City F = new City("F");
        City G = new City("G");
        City H = new City("H");

        roadAtlas.addVertex(A);
        roadAtlas.addVertex(B);
        roadAtlas.addVertex(C);
        roadAtlas.addVertex(D);
        roadAtlas.addVertex(E);
        roadAtlas.addVertex(F);
        roadAtlas.addVertex(G);
        roadAtlas.addVertex(H);

        roadAtlas.addEdge(A, B, new Road("a",100, 1));
        roadAtlas.addEdge(B, D, new Road("b", 100, 1));
        roadAtlas.addEdge(D, H, new Road("c", 100, 1));
        roadAtlas.addEdge(C, D, new Road("d", 1, 100));
        roadAtlas.addEdge(D, F, new Road("e", 100, 1));
        roadAtlas.addEdge(A, C, new Road("f", 1, 100));
        roadAtlas.addEdge(C, E, new Road("g", 1, 100));
        roadAtlas.addEdge(E, F, new Road("h", 1, 100));
        roadAtlas.addEdge(F, G, new Road("i", 1, 100));
        roadAtlas.addEdge(G, H, new Road("j", 1, 100));

        roadAtlas.display();

        List<Road> bestWay = roadAtlas.getBestWay(A, H, RANGE);
        for (Road road : bestWay) {
            System.out.println(road);
        }

    }
}
