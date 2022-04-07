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
    public void displayBestWay(V startVertex, V endVertex, int metricIndex) {
        if (startVertex.equals(endVertex)) {
            System.out.println("Приехали");
        } else {
            unlockAllEdges();

            List<Route> optimalRoutes = new ArrayList<>();
            for (int i = 0; i < vertexList.size(); i++) {
                optimalRoutes.add(null);
            }

            int start = getVertexIndex(startVertex);
            int end = getVertexIndex(endVertex);

            resetEdgeVisited();
            List<Route> routeList = initRoutes(start, metricIndex, optimalRoutes);

            for (Route route : routeList) {
                System.out.println(route);
            }
            System.out.println("-----");

            boolean routesWasUpdated = true;

            while (routesWasUpdated) {
                routesWasUpdated = false;
                boolean optimalRoutesWasUpdated = false;
                List<Route> nextRouteList = new LinkedList<>();
                for (Route route : routeList) {
                    System.out.println("# " + route);
                    int vertex = route.lastVertex;
                    if (vertex != end) {
                        for (Edge edge : edgeList) {
                            if (edge.canAddToRoute(vertex)) {
                                routesWasUpdated = true;
                                Route newRoute = new Route(route, edge);
                                System.out.println("    -> " + newRoute);
                                int finish = newRoute.lastVertex;
                                Route optimalRoute = optimalRoutes.get(finish);
                                System.out.println("    OPTIMAL: " + optimalRoute);
                                if (optimalRoute == null) {
                                    optimalRoutes.set(finish, newRoute);
                                    nextRouteList.add(newRoute);
                                } else {
                                    if (newRoute.summaryMetric < optimalRoute.summaryMetric) {
                                        optimalRoute.lock();
                                        optimalRoutes.set(finish, newRoute);
                                    } else {
                                        newRoute.lock();
                                    }
                                    optimalRoutesWasUpdated = true;
                                    break;
                                }
                            }
                        }
                    }
                    if (optimalRoutesWasUpdated) {
                        break;
                    }
                }

                if (optimalRoutesWasUpdated) {
                    resetEdgeVisited();
                    nextRouteList = initRoutes(start, metricIndex, optimalRoutes);
                }

                routeList = nextRouteList;

                for (Route route : routeList) {
                    System.out.println(route);
                }
                System.out.println("-----");
            }
        }
    }

    @Override
    public List<E> getBestWay(V startVertex, V endVertex, int metricIndex) {
        List<E> result = new ArrayList<>();

        AdjMatrix adjMatrix = new AdjMatrix(metricIndex);

        for (int i = 0; i < vertexList.size(); i++) {
            for (int j = 0; j < vertexList.size(); j++) {
                System.out.print(adjMatrix.get(i, j) + " ");
            }
            System.out.println();
        }

        return result;
    }

    private void unlockAllEdges() {
        edgeList.forEach(e -> e.locked = false);
    }

    private List<Route> initRoutes(int vertex, int metricIndex, List<Route> optimalRoutes) {
        List<Route> result = new ArrayList<>();
        boolean optimalRoutesWasUpdated = true;
        while (optimalRoutesWasUpdated) {
            optimalRoutesWasUpdated = false;
            for (Edge edge : edgeList) {
                if (edge.canAddToRoute(vertex)) {
                    Route newRoute = new Route(edge, vertex, metricIndex);
                    int finish = newRoute.lastVertex;
                    Route optimalRoute = optimalRoutes.get(finish);
                    if (optimalRoute == null) {
                        optimalRoutes.set(finish, newRoute);
                        result.add(newRoute);
                    } else {
                        if (newRoute.summaryMetric < optimalRoute.summaryMetric) {
                            optimalRoute.lock();
                        } else {
                            newRoute.lock();
                        }
                        optimalRoutes.clear();
                        optimalRoutesWasUpdated = true;
                    }
                }
            }
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
        int lastVertex;
        int metricIndex;

        private Route(Route route, Edge edge) {
            this.edgeList = new ArrayList<>(route.edgeList);
            this.metricIndex = route.metricIndex;
            this.summaryMetric = route.summaryMetric;
            this.lastVertex = route.lastVertex;
            addEdge(edge);
        }

        private Route(Edge edge, int firstVertex, int metricIndex) {
            this.edgeList = new ArrayList<>();
            this.metricIndex = metricIndex;
            summaryMetric = 0;
            this.lastVertex = firstVertex;
            addEdge(edge);
        }

        private void addEdge(Edge edge) {
            int newLastVertex = edge.getOppositeVertex(lastVertex);
            if (newLastVertex == -1) {
                throw new IllegalArgumentException(edge + " not connected with " + this);
            } else {
                edgeList.add(edge);
                edge.visited = true;
                lastVertex = newLastVertex;
                summaryMetric += edge.payload.getMetric(metricIndex);
            }
        }

        private void lock() {
            edgeList.forEach(e -> e.locked = true);
        }

        private boolean interchangeable(Route route) {
            return route.lastVertex == this.lastVertex;
        }

        public String toString() {
            return "Route:{edgeList=" + edgeList
                    + ", summaryMetric=" + summaryMetric
                    + ", lastVertex=" + vertexList.get(lastVertex) + "}";
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
