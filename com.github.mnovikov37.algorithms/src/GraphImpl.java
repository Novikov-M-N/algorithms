import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class GraphImpl<V, E extends Measurable> implements Graph<V, E>{
    private List<V> vertexList;
    private List<Edge> edgeList;

    public GraphImpl() {
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
                if (edge.startVertex == i) {
                    System.out.println("    <-> " + vertexList.get(edge.endVertex) + " : " + edge.payload);
                }
            }
        }
    }

    @Override
    public void displayBestWay(V startVertex, V endVertex, int metricIndex) {
        resetEdgeVisited();
        int start = getVertexIndex(startVertex);
        int end = getVertexIndex(endVertex);
        Queue<Integer> vertexQueue = new LinkedList<>();
        vertexQueue.offer(start);
        List<Route> routeList = new LinkedList<>();
        updateRoutes(start, routeList, metricIndex);
        List<Route> nextRouteList = new LinkedList<>();

        for (Route route : routeList) {
            if (route.lastVertex != end) {
                updateRoutes(route.lastVertex, nextRouteList, metricIndex);
            }
        }

        routeList = nextRouteList;
        
        for (Route route : routeList) {
            System.out.println(route);
        }
    }

    private void updateRoutes(int vertex, List<Route> routeList, int metricIndex) {
        for (Edge edge : edgeList) {
            if (!edge.visited) {
                if (edge.startVertex == vertex) {
                    routeList.add(new Route(edge, edge.endVertex, metricIndex));
                    edge.visited = true;
                }
                if (edge.endVertex == vertex) {
                    routeList.add(new Route(edge, edge.startVertex, metricIndex));
                    edge.visited = true;
                }
            }
        }
    }

    private void resetEdgeVisited() {
        edgeList.forEach(e -> e.visited = false);
    }

    private class Edge {
        private int startVertex;
        private int endVertex;
        private E payload;
        private boolean visited;

        private Edge(int startVertex, int endVertex, E edge) {
            this.startVertex = startVertex;
            this.endVertex = endVertex;
            this.payload = edge;
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
        int lastVertex;

        private Route() {
            edgeList = new ArrayList<>();
            summaryMetric = 0;
            lastVertex = -1;
        }

        private Route(Route route, Edge edge, int lastVertex, int metricIndex) {
            this.edgeList = new ArrayList<>(route.edgeList);
            this.addEdge(edge, lastVertex, metricIndex);
        }

        private Route(Edge edge, int lastVertex, int metircIndex) {
            this.edgeList = new ArrayList<>();
            summaryMetric = 0;
            addEdge(edge, lastVertex, metircIndex);
        }

        private void addEdge(Edge edge, int lastVertex, int metricIndex) {
            edgeList.add(edge);
            this.lastVertex = lastVertex;
            summaryMetric += edge.payload.getMetric(metricIndex);
        }

        private boolean interchangeable(Route route) {
            return route.lastVertex == this.lastVertex;
        }

        public String toString() {
            return "Route:{edgeList=" + edgeList
                    + ", summaryMetric=" + summaryMetric
                    + ", lastVertex=" + lastVertex + "}";
        }
    }
}
