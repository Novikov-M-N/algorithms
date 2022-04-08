import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class GraphImplDijkstraBidirectional<V, E extends Measurable> implements Graph<V, E>{
    private List<V> vertexList;
    private List<Edge> edgeList;

    public GraphImplDijkstraBidirectional() {
        vertexList = new ArrayList<>();
        edgeList = new ArrayList<>();
    }

    @Override
    public void addVertex(V vertex) {
        if (vertexList.contains(vertex)) {
            throw new IllegalArgumentException("Vertex " + vertex.toString() + " already included in graph.");
        } else {
            vertexList.add(vertex);
        }
    }
    
    private int getVertexIndex(V vertex) {
        int result = vertexList.indexOf(vertex);
        if (result == -1) {
            throw new IllegalArgumentException("Graph not contains vertex " + vertex.toString());
        }
        return result;
    }

    @Override
    public void addEdge(V startVertex, V endVertex, E payload) {
        int start = getVertexIndex(startVertex);
        int end = getVertexIndex(endVertex);
        Edge edge = new Edge(start, end, payload);
        if (edgeList.contains(edge)) {
            throw new IllegalArgumentException("Graph already contains edge " + edge.toString());
        }
        edgeList.add(new Edge(start, end, payload));
    }

    @Override
    public int getSize() {
        return vertexList.size();
    }

    @Override
    public void display() {
        for (int i = 0; i < vertexList.size(); i++) {
            System.out.println(vertexList.get(i));
            for (Edge edge : edgeList) {
                int oppositeVertexIndex = edge.getOppositeVertex(i);
                if (oppositeVertexIndex != -1) {
                    System.out.println("    <-> " + vertexList.get(oppositeVertexIndex) + " : " + edge.payload);
                }
            }
        }
    }

    @Override
    public List<E> getBestWay(V startVertex, V endVertex, int metricIndex) {
        List<E> result = new ArrayList<>();
        int size = vertexList.size();

        AdjMatrix adjMatrix = new AdjMatrix(metricIndex);

//        for (int i = 0; i < vertexList.size(); i++) {
//            for (int j = 0; j < vertexList.size(); j++) {
//                System.out.printf("%30s", adjMatrix.get(i, j));
//            }
//            System.out.println();
//        }

        List<Route> routes = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            routes.add(null);
        }

        return result;
    }

    private void resetEdgeVisited() {
        edgeList.forEach(e -> e.visited = false);
    }

    private class Edge {
        private int startVertex;
        private int endVertex;
        private E payload;
        private boolean visited;
        private boolean locked;

        private Edge(int startVertex, int endVertex, E edge) {
            this.startVertex = startVertex;
            this.endVertex = endVertex;
            this.payload = edge;
        }

        private int getOppositeVertex(int vertexIndex) {
            if (startVertex == vertexIndex) {
                return endVertex;
            }
            if (endVertex == vertexIndex) {
                return startVertex;
            }
            return -1;
        }

        private boolean canAddToRoute(int vertex) {
            return !locked && !visited && (startVertex == vertex || endVertex == vertex);
        }

        public boolean equals(Object o) {
            if (o == this) { return true; }
            if (o == null || o.getClass() != Edge.class) { return false; }
            Edge edge = (Edge) o;
            return edge.startVertex == this.startVertex
                    && edge.endVertex == this.endVertex
                    && edge.payload.equals(this.payload);
        }

        public String toString() {
            return "Edge:{startVertex=" + vertexList.get(startVertex).toString()
                    + ", endVertex=" + vertexList.get(endVertex).toString()
                    + ", payload=" + payload.toString() + "}";
        }
    }

    private class Route {
        List<Edge> edgeList;
        int summaryMetric;
        int metricIndex;

        private Route(Route route, Edge edge) {
            this.edgeList = new ArrayList<>(route.edgeList);
            this.edgeList.add(edge);
            this.metricIndex = route.metricIndex;
            this.summaryMetric = route.summaryMetric + edge.payload.getMetric(metricIndex);
        }

        private Route(Edge edge, int metricIndex) {
            this.edgeList = new ArrayList<>();
            this.edgeList.add(edge);
            this.metricIndex = metricIndex;
            summaryMetric = edge.payload.getMetric(metricIndex);
        }

        public String toString() {
            return "Route:{edgeList=" + edgeList
                    + ", summaryMetric=" + summaryMetric
                    + "}";
        }
    }

    private class AdjMatrix {
        Object[][] payload;

        private AdjMatrix(int metricIndex) {
            int size = vertexList.size();
            payload = new Object[size][size];
            for (Edge edge : edgeList) {
                Object cell = payload[edge.startVertex][edge.endVertex];
                if (cell == null || ((E) cell).getMetric(metricIndex) > edge.payload.getMetric(metricIndex)) {
                    payload[edge.startVertex][edge.endVertex] = edge.payload;
                    payload[edge.endVertex][edge.startVertex] = edge.payload;
                }
            }
        }

        E get(int i, int j) {
            return (E) payload[i][j];
        }
    }
}
