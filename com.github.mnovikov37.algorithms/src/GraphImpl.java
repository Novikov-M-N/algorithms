import java.util.List;

public class GraphImpl<V, E extends Measurable> implements Graph<V, E>{
    private List<V> vertexList;
    private List<Edge> edgeList;

    @Override
    public void addVertex(V vertex) {
        if (vertexList.contains(vertex)) {
            throw new IllegalArgumentException("Vertex " + vertex.toString() + " already included in graph.");
        } else {
            vertexList.add(vertex);
        }
    }

    @Override
    public void addEdge(V startVertex, V endVertex, E edge) {
        int start = vertexList.indexOf(startVertex);
        if (start == -1) {
            throw new IllegalArgumentException("Graph not contains vertex " + startVertex.toString());
        }
        int end = vertexList.indexOf(endVertex);
        if (end == -1) {
            throw new IllegalArgumentException("Graph not contains vertex " + endVertex.toString());
        }
        edgeList.add(new Edge(start, end, edge));
    }

    @Override
    public int getSize() {
        return vertexList.size();
    }

    @Override
    public void display() {

    }

    @Override
    public List<V> getBestWay(V startVertex, V endVertex, int metricIndex) {
        return null;
    }

    private class Edge {
        private int startVertex;
        private int endVertex;
        private E edge;
        private Edge(int startVertex, int endVertex, E edge) {
            this.startVertex = startVertex;
            this.endVertex = endVertex;
            this.edge = edge;
        }
    }
}
