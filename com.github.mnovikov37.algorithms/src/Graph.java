import java.util.List;

public interface Graph<V, E extends Measurable> {
    void addVertex(V vertex);
    void addEdge(V startVertex, V endVertex, E edge);
    int getSize();
    void display();
    List<V> getBestWay(V startVertex, V endVertex, int metricIndex);
}
