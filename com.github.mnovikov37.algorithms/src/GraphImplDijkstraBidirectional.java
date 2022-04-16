import java.util.ArrayList;
import java.util.List;

/**
 * Реализация графа с двунаправленными рёбрами и расчётом оптимального пути по алгоритму Дейкстры
 * @param <V> Класс вершины
 * @param <E> Класс ребра
 */
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

        // Матрица смежности строится для каждого случая вычисления лучшего пути,
        // т.к. для её построения нужно знать, по какой метрике выбирается оптимальный путь.
        AdjMatrix adjMatrix = new AdjMatrix(metricIndex);

        // Результатом работы алгоритма будет список оптимальных путей до каждой вершины.
        List<Route> routes = generateRoutesList(size, start, metricIndex);

        // В ходе расчёта нужно запоминать, какие из вершин уже обработаны
        boolean[] visited = new boolean[size];

        for (int currentVertex = start; currentVertex != -1; currentVertex = getCurrentVertex(routes, visited)) {
            // На каждом шаге выбираем вершину графа с минимальной метрикой. Стартовая вершина имеет метрику ноль.
            visited[currentVertex] = true;
            int currentMetric = routes.get(currentVertex).summaryMetric;

            // Копируем массив флагов посещения вершин на каждом шаге и работаем с этой копией. Изменения в ходе
            // очередного шага алгоритма не должны коснуться исходного массива посещения вершин.
            boolean[] visitedNext = new boolean[size];
            System.arraycopy(visited, 0, visitedNext, 0, size);

            for (int nextVertex = getNextVertex(currentVertex, adjMatrix, visitedNext, metricIndex);
                 nextVertex != -1; nextVertex = getNextVertex(currentVertex, adjMatrix, visitedNext, metricIndex)) {

                // Обходм все не посещённые смежные вершины в порядке возрастания метрики и для каждой из них
                // находим новую метку, равную сумме метки текущей вершины (см. внешний цикл) и ребра,
                // соединяющего текущую и следующую вершину. Если полученная метка меньше существующей метки вершины,
                // то заменяем для этой вершины путь на путь до текущей вершины + соединяющее их ребро.
                visitedNext[nextVertex] = true;
                E edge = adjMatrix.get(currentVertex, nextVertex);
                int newMetric = currentMetric + edge.getMetric(metricIndex);
                Route route = routes.get(nextVertex);
                if (route == null || newMetric < route.summaryMetric) {
                    routes.set(nextVertex, new Route(routes.get(currentVertex), edge));
                }
            }
        }

        // Результатом будет список рёбер, составляющих оптимальный путь до конечной вершины, если таковой существует.
        if (routes.get(end) != null) {
            result = routes.get(end).edgeList;
        }

        return result;
    }

    /**
     * Создаёт список путей до каждой вершины и заполняет значениями null. Для стартовой вершины создаётся путь
     * нулевой длины.
     * @param size Количество вершин в графе
     * @param start Номер стартовой вершины
     * @param metricIndex Номер метрики, по которой считается оптимальный путь
     * @return Список не существующих путей для всех вершин, кроме стартовой. Для стартовой путь нулевой длины.
     */
    private List<Route> generateRoutesList(int size, int start, int metricIndex) {
        List<Route> result = new ArrayList<>(size);

        for (int i = 0; i < size; i++) {
            result.add(null);
        }
        result.set(start, new Route(metricIndex));

        return result;
    }

    /**
     * Определяет следующую для рассмотрения вершину от текущей. Выбирается по минимальной метрике ребра до неё.
     * @param currentVertex Номер текущей вершины
     * @param adjMatrix Матрица смежности графа
     * @param visited Массив флагов посещения вершин
     * @param metricIndex Номер метрики, по которой определяется оптимальный путь
     * @return Номер следующей вершины для рассмотрения варианта пути, либо -1, если таковая вершина не найдена
     */
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

    /**
     * Определяет следующую вершину по минимальной метрике найденного ранее пути до неё.
     * @param routes Список оптимальных путей до каждой вершины
     * @param visited Массив флагов посещения вершин
     * @return Номер следующей вершины, либо -1, если таковая вершина не найдена
     */
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

    /**
     * Ребро графа. Состоит из номеров вершин начала и конца и собственно ребра - объёкта класса E,
     * имеющего метрику.
     */
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

        /**
         * Нахождение номера противоположной вершины ребра
         * @param vertexIndex Номер известной вершины ребра
         * @return Номер противоположной вершины ребра, либо -1, если ребро не содержит известной вершины
         */
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

    /**
     * Путь. Состоит из рёбер графа. Содержит список рёбер и сумму их метрик.
     */
    private class Route {
        List<E> edgeList;
        int summaryMetric;
        int metricIndex;

        /**
         * Создаёт новый путь на основе существующего пути и добавления к нему нового ребра
         * @param route Прежний путь
         * @param edge Новое ребро
         */
        private Route(Route route, E edge) {
            this.edgeList = new ArrayList<>(route.edgeList);
            this.edgeList.add(edge);
            this.metricIndex = route.metricIndex;
            this.summaryMetric = route.summaryMetric + edge.getMetric(metricIndex);
        }

        /**
         * Создаёт пустой путь
         * @param metricIndex Номер метрики, по которой будет производиться расчёт стоимости пути
         */
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

    /**
     * Матрица смежности графа. Содержит рёбра, соединяющие вершины i и j,
     * либо null, если между этими вершинами нет рёбер. Матрица симметрична, т.к. граф не направленный.
     */
    private class AdjMatrix {
        Object[][] payload;

        /**
         * Создаёт матрицу смежности на основе списка рёбер графа. Если между вершинами есть несколько рёбер,
         * то в матрицу вносится ребро с минимальной метрикой.
         * @param metricIndex Номер метрики, по которой считается стоимость ребра.
         */
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
