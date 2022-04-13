import java.util.ArrayList;
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
            throw new IllegalArgumentException("Graph already contains edge " + edge);
        }
        edgeList.add(edge);
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
        int start = getVertexIndex(startVertex);
        int end = getVertexIndex(endVertex);

        AdjMatrix adjMatrix = new AdjMatrix(metricIndex);

        List<Route> routes = generateRoutesList(size, start, metricIndex);

        boolean[] visited = new boolean[size];

        int currentVertex = getCurrentVertex(routes, visited);
        for (; currentVertex != -1; currentVertex = getCurrentVertex(routes, visited)) {
            visited[currentVertex] = true;
            int currentMetric = routes.get(currentVertex).summaryMetric;
            boolean[] visitedNext = new boolean[size];
            System.arraycopy(visited, 0, visitedNext, 0, size);
            int nextVertex = getNextVertex(currentVertex, adjMatrix, visitedNext, metricIndex);
            for (; nextVertex != -1; nextVertex = getNextVertex(currentVertex, adjMatrix, visitedNext, metricIndex)) {
                visitedNext[nextVertex] = true;
                E edge = adjMatrix.get(currentVertex, nextVertex);
                int newMetric = currentMetric + edge.getMetric(metricIndex);
                Route route = routes.get(nextVertex);
                if (route == null || newMetric < route.summaryMetric) {
                    routes.set(nextVertex, new Route(routes.get(currentVertex), edge));
                }
            }
        }

        if (routes.get(end) != null) {
            result = routes.get(end).edgeList;
        }

        return result;
    }

    private List<Route> generateRoutesList(int size, int start, int metricIndex) {
        List<Route> result = new ArrayList<>(size);

        for (int i = 0; i < size; i++) {
            result.add(null);
        }
        result.set(start, new Route(metricIndex));

        return result;
    }

    private int getNextVertex(int currentVertex, AdjMatrix adjMatrix, boolean[] visited, int metricIndex) {
        int result = -1;

        int min = Integer.MAX_VALUE;
        for (int i = 0; i < visited.length; i++) {
            if (!visited[i]) {
                E edge = adjMatrix.get(currentVertex, i);
                if (edge != null && edge.getMetric(metricIndex) < min) {
                    result = i;
                    min = edge.getMetric(metricIndex);
                }
            }
        }

        return result;
    }

    private int getCurrentVertex(List<Route> routes, boolean[] visited) {
        int result = -1;

        int min = Integer.MAX_VALUE;
        for (int i = 0; i < visited.length; i++) {
            if (!visited[i]) {
                Route route = routes.get(i);
                if (route != null && route.summaryMetric < min) {
                    result = i;
                    min = route.summaryMetric;
                }
            }
        }

        return result;
    }

    private class Edge {
        private int startVertex;
        private int endVertex;
        private E payload;
        private int hashCode = 0;

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

        public boolean equals(Object o) {
            if (o == this) { return true; }
            if (o == null || o.getClass() != Edge.class) { return false; }
            Edge edge = (Edge) o;
            return edge.startVertex == this.startVertex
                    && edge.endVertex == this.endVertex
                    && edge.payload.equals(this.payload);
        }

        public int hashCode() {
            if (hashCode == 0) {
                hashCode = 31 * startVertex + endVertex;
                hashCode = 31 * hashCode + payload.hashCode();
            }
            return hashCode;
        }

        public String toString() {
            return "Edge:{startVertex=" + vertexList.get(startVertex).toString()
                    + ", endVertex=" + vertexList.get(endVertex).toString()
                    + ", payload=" + payload.toString() + "}";
        }
    }

    private class Route {
        List<E> edgeList;
        int summaryMetric;
        int metricIndex;

        private Route(Route route, E edge) {
            this.edgeList = new ArrayList<>(route.edgeList);
            this.edgeList.add(edge);
            this.metricIndex = route.metricIndex;
            this.summaryMetric = route.summaryMetric + edge.getMetric(metricIndex);
        }

        private Route(int metricIndex) {
            this.edgeList = new ArrayList<>();
            this.metricIndex = metricIndex;
            this.summaryMetric = 0;
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
