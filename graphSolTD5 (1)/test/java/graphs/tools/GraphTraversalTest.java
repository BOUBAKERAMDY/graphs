package graphs.tools;

import graphs.graphcore.*;
import graphs.tools.solutions.GraphHelper;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class GraphTraversalTest {

    @Test
    void testDFSU1() {
        UnDiGraph graph = GraphReader.unDiGraph("A B C D C E D E E F G H G K H I H J H K I J");
        List<Vertex> path = GraphTraversal.dfs(graph, graph.getVertex("A"));
        System.out.println(path);
        assertEquals(2, path.size());
        path = GraphTraversal.dfs(graph, graph.getVertex("C"));
        System.out.println(path);
        assertEquals(4, path.size());
        path = GraphTraversal.dfs(graph, graph.getVertex("G"));
        System.out.println(path);
        assertEquals(5, path.size());
        assertEquals(graph.getVertex("G"), path.get(0));
        if (path.get(1).equals(graph.getVertex("H")))
            assertEquals(graph.getVertex("K"), path.get(4));
        else
            assertEquals(graph.getVertex("K"), path.get(1));
    }

    @Test
    void testBFSU1() {
        UnDiGraph graph = GraphReader.unDiGraph("A B C D C E D E E F G H G K H I H J H K I J");
        List<Vertex> path = GraphTraversal.bfs(graph, graph.getVertex("A"));
        System.out.println(path);
        assertEquals(2, path.size());
        path = GraphTraversal.bfs(graph, graph.getVertex("C"));
        System.out.println(path);
        assertEquals(4, path.size());
        path = GraphTraversal.bfs(graph, graph.getVertex("G"));
        System.out.println(path);
        assertEquals(5, path.size());
        assertEquals(graph.getVertex("G"), path.get(0));
        if (path.get(1).equals(graph.getVertex("H")))
            assertEquals(graph.getVertex("K"), path.get(2));
        else
            assertEquals(graph.getVertex("K"), path.get(1));
    }


    @Test
    void testDFSU2() {
        //GraphReader.unDiGraph("A D A E A J B C B F B G B I C F C G C H D E D F G H");
        UnDiGraph graph = GraphReaderTestExamples.U2;
        List<Vertex> path = GraphTraversal.dfs(graph, graph.getVertex("F"));
        System.out.println(path);
        assertEquals(10, path.size());
        assertEquals(graph.getVertex("F"), path.get(0));
    }
    @Test
    void testBFSU2() {
        //GraphReader.unDiGraph("A D A E A J B C B F B G B I C F C G C H D E D F G H");
        UnDiGraph graph = GraphReaderTestExamples.U2;
        List<Vertex> path = GraphTraversal.bfs(graph, graph.getVertex("F"));
        System.out.println(path);
        assertEquals(10, path.size());
        assertEquals(graph.getVertex("F"), path.get(0));
    }

    @Test
    void testBFSTree(){
        UnDiGraph graph = GraphReaderTestExamples.TREE;
        List<Vertex> path = GraphTraversal.bfs(graph, graph.getVertex("A"));
        System.out.println(path);
        assertEquals(26, path.size());
        assertEquals(graph.getVertex("A"), path.get(0));
        assertEquals(graph.getVertex("Z"), path.get(25));
    }

    @Test
    void testDFSTree(){
        UnDiGraph graph = GraphReaderTestExamples.TREE;
        List<Vertex> path = GraphTraversal.dfs(graph, graph.getVertex("A"));
        System.out.println(path);
        assertEquals(26, path.size());
        assertEquals(graph.getVertex("A"), path.get(0));
        assertEquals(graph.getVertex("Z"), path.get(25));
        if (path.get(1).equals(graph.getVertex("B")))
            assertEquals(graph.getVertex("D"), path.get(2));
    }

    @Test
    void testDijkstra43Nodes() throws DuplicateTagException {
        DiGraph graph = new DiGraph();
        Vertex a = graph.addVertex("A");
        Vertex b = graph.addVertex("B");
        Vertex c = graph.addVertex("C");
        Vertex d = graph.addVertex("D");
        graph.addEdge(a,b);
        graph.addEdge(a,c);
        graph.addEdge(b,d);
        graph.addEdge(c,b);
        graph.addEdge(b,a);

        assertEquals(4, graph.nbVertices());
        assertEquals(5, graph.nbEdges());
        assertEquals(1, graph.findEdges(a,b).size());
        assertEquals(1, graph.findEdges(a,c).size());
        assertEquals(1, graph.findEdges(b,a).size());
        assertEquals(0, graph.findEdges(d,b).size());

        Collection<Edge> incidents = graph.incidents(a);
        assertTrue(incidents.contains(graph.findEdge(a,b)));
        assertTrue(incidents.contains(graph.findEdge(a,c)));
        assertEquals(1, graph.findEdges(a,b).size());
        assertEquals(1, graph.findEdges(a,c).size());
        System.out.println("a->b : " + incidents);
        assertEquals(2, incidents.size());
        incidents = graph.incidents(b);
        assertEquals(2, incidents.size());
        incidents = graph.incidents(c);
        assertEquals(1, incidents.size());
        incidents = graph.incidents(d);
        assertEquals(0, incidents.size());

        List<Vertex> path = GraphTraversal.dijkstra(graph, graph.getVertex("A"), graph.getVertex("D"));
        System.out.println(" A to D : A-B-D =?" + path);
        assertEquals(3, path.size());
        path = GraphTraversal.dijkstra(graph, graph.getVertex("C"), graph.getVertex("A"));
        System.out.println("C to A :C-B-A =?"+ path);
        assertEquals(3, path.size());
        path = GraphTraversal.dijkstra(graph, graph.getVertex("C"), graph.getVertex("B"));
        System.out.println(path);
        assertEquals(2, path.size());
    }
    @Test
    void testDijkstra4Tree(){
        UnDiGraph graph = GraphReaderTestExamples.TREE;
        List<Vertex> path = GraphTraversal.dijkstra(graph, graph.getVertex("A"), graph.getVertex("Z"));
        System.out.println(path);
        assertEquals(4, path.size());
        checkPath(graph, path);
    }

    @Test
    void testDijkstra4WeightedGraph(){
        DiGraph graph = GraphReader.diGraph("A B 2.5 A C 5.2 B C 1.0 B D 3.0 C D 2.0 C E 4.0 D E 1.0");
        List<Vertex> path = GraphTraversal.dijkstra(graph, graph.getVertex("A"), graph.getVertex("E"));
        System.out.println(path);
        assertEquals(4, path.size());
        checkPath(graph, path);
        GraphHelper gh = new GraphHelper();
        assertEquals(6.5, gh.computeDistanceOfPath(path, graph), 0.0001);
        System.out.println("Distance for : " + path + " = " + gh.computeDistanceOfPath(path,graph));
        path= Arrays.asList(graph.getVertex("A"),graph.getVertex("B"),graph.getVertex("C"),graph.getVertex("D"),graph.getVertex("E"));
        System.out.println("Distance for : " + path + " = " + gh.computeDistanceOfPath(path,graph));
        path= Arrays.asList(graph.getVertex("A"),graph.getVertex("C"),graph.getVertex("E"));
        System.out.println("Distance for : " + path + " = " + gh.computeDistanceOfPath(path,graph));
        assertEquals(9.2, gh.computeDistanceOfPath(path, graph), 0.0001);

        path = GraphTraversal.dijkstra(graph, graph.getVertex("C"), graph.getVertex("E"));
        System.out.println(path);
        assertEquals(3, path.size());
        checkPath(graph, path);
        assertEquals(3.0, gh.computeDistanceOfPath(path, graph), 0.0001);
        path= Arrays.asList(graph.getVertex("C"),graph.getVertex("E"));
        assertEquals(4.0, gh.computeDistanceOfPath(path, graph), 0.0001);

    }


    @Test
    void testDij() throws DuplicateTagException {
        // Créer votre graphe de test
        AbstractGraph graph = new UnDiGraph();
        Vertex A = graph.addVertex("A");
        Vertex B = graph.addVertex("B");
        Vertex C = graph.addVertex("C");
        Vertex D = graph.addVertex("D");
        graph.addEdge(A, B, 1.0);
        graph.addEdge(B, C, 2.0);
        graph.addEdge(C, D, 3.0);
        graph.addEdge(A, D, 5.0);

        // Appeler la méthode shortestPath pour obtenir le chemin le plus court
        List<Vertex> shortestPath = GraphTraversal.dijkstra(graph, A, D);

        // Vérifier si le chemin obtenu est correct
        List<Vertex> expectedPath = List.of(A, D);
        assertEquals(expectedPath, shortestPath, "Le chemin le plus court est incorrect");
    }

    @Test
    void dijkstraFromTest() throws DuplicateTagException {
        // Créer votre graphe de test
        AbstractGraph graph = new UnDiGraph();
        Vertex A = graph.addVertex("A");
        Vertex B = graph.addVertex("B");
        Vertex C = graph.addVertex("C");
        Vertex D = graph.addVertex("D");
        Vertex E = graph.addVertex("E");
        Vertex F = graph.addVertex("F");
        Vertex G = graph.addVertex("G");
        /*
                      C -1----------------> F
        A -1-> B -2-> C -2-> D -2-> E -1-> F
        A -6----------------^
        A -3---------------------------------------G
         */
        graph.addEdge(A, B, 1.0);
        graph.addEdge(B, C, 2.0);
        graph.addEdge(C, D, 2.0);
        graph.addEdge(A, D, 6.0);
        graph.addEdge(D,E, 2.0);
        graph.addEdge(E,F, 1.0);
        graph.addEdge(C,F, 1.0);
        graph.addEdge(A,G, 3.0);
        GraphHelper gh = new GraphHelper();

        Map<Vertex, Vertex> predecessors = GraphTraversal.dijkstra(graph, A);
        System.out.println(predecessors);
        assertEquals(A,predecessors.get(B));
        List<Vertex> path = GraphTraversal.buildPath(predecessors, A, B);
        assertEquals(List.of(A,B),path);
        double distance = gh.computeDistanceOfPath(path,graph);
        assertEquals(1.0,distance);

        assertEquals(B,predecessors.get(C));
        path = GraphTraversal.buildPath(predecessors, A, C);
        assertEquals(List.of(A,B,C),path);
        distance = gh.computeDistanceOfPath(path,graph);
        assertEquals(3.0,distance);

        assertEquals(C,predecessors.get(D));
        path = GraphTraversal.buildPath(predecessors, A, D);
        assertEquals(List.of(A,B,C,D),path);
        distance = gh.computeDistanceOfPath(path,graph);
        assertEquals(5.0,distance);

        assertEquals(F,predecessors.get(E));
        path = GraphTraversal.buildPath(predecessors, A, E);
        assertEquals(List.of(A,B,C,F,E),path);
        distance = gh.computeDistanceOfPath(path,graph);
        System.out.println(distance + " -- " + gh.computeDistanceOfPath(List.of(A,B,C,D,E),graph));
        assertEquals(5.0,distance);

        assertEquals(C,predecessors.get(F));
        path = GraphTraversal.buildPath(predecessors, A, F);
        assertEquals(List.of(A,B,C,F),path);
        distance = gh.computeDistanceOfPath(path,graph);
        assertEquals(4.0,distance);

        assertEquals(A,predecessors.get(G));
        path = GraphTraversal.buildPath(predecessors, A, G);
        assertEquals(List.of(A,G),path);
        distance = gh.computeDistanceOfPath(path,graph);
        assertEquals(3.0,distance);
    }


    /************************ Test the findPaths method *********************************/

    private static void testPaths(AbstractGraph graph, Vertex A, Vertex C, int nbPaths) {
        List<Path> paths = GraphTraversal.findPaths(graph, A, C);
        System.out.println("Paths from " + A + " to " + C + " : " + paths);
        checkDirectEdges(graph, A, C, paths);
        for (Path path : paths) {
            checkPath(graph, path.vertices());
        }
        assertEquals(nbPaths, paths.size());

        System.out.println("----------- " );
    }

    private static void checkPath(AbstractGraph graph, List<Vertex> path) {
        System.out.println("Checking path : " + path);
        for (int i = 0; i < path.size()-1; i++) {
            System.out.println(path.get(i) + " -> " + path.get(i+1) + " : " + graph.findEdge(path.get(i), path.get(i+1)).weight());
            assertNotNull(graph.findEdge(path.get(i), path.get(i+1)));
        }
    }

    private static void checkDirectEdges(AbstractGraph graph, Vertex a, Vertex c, List<Path> paths) {
        List<Edge> edges = graph.getEdges(a, c);
        //At least one path should refer to the direct edges
        boolean found = false;
        for (Edge edge : edges) {
            found = false;
            for (Path path : paths) {
                if (path.size() == 1) {
                    if (path.contains(edge)) {
                        found = true;
                        break;
                    }
                }
            }
            assertTrue(found, "Direct edge not found : " + edge);
        }
    }

    @Test
    void testFindPathsDiGraph() {
        AbstractGraph graph = GraphReader.diGraph("A B 2.5 A C 5.2 B C 1.0 B D 3.0 C D 2.0 C E 4.0 D E 1.0");
        //DiGraph graph = GraphReader.diGraph("A B C D C E D E E F G H G K H I H J H K I J");
        //UnDiGraph graph = GraphReader.unDiGraph("A B C D C E D E E F G H G K H I H J H K I J");
        Vertex A = graph.getVertex("A");
        Vertex C = graph.getVertex("C");
        int nbPaths = 2;
        testPaths(graph, A, C, nbPaths);
    }

    @Test
    void testFindPathsUnDiGraphSamePoint() {
        AbstractGraph graph = GraphReader.unDiGraph("A B 2.5 A C 5.2 B C 1.0 B D 3.0 C D 2.0 "); //C E 4.0 D E 1.0");
        Vertex A = graph.getVertex("A");
        testPaths(graph, A, A, 0);
    }
    @Test
    void testFindPathsUnDiGraph() {
        AbstractGraph graph = GraphReader.unDiGraph("A B 2.5 A C 5.2 B C 1.0 B D 3.0 C D 2.0 "); //C E 4.0 D E 1.0");
        Vertex A = graph.getVertex("A");
        Vertex B = graph.getVertex("B");
        Vertex C = graph.getVertex("C");
        testPaths(graph, A, B, 3);
        testPaths(graph, B, A, 3);
        testPaths(graph, B, C, 3);
        testPaths(graph, C, B, 3);
        testPaths(graph, A, C, 3);
        testPaths(graph, C, A, 3);

        Vertex D = graph.getVertex("D");
        testPaths(graph, A, D, 4);
        testPaths(graph, D, A, 4);

        graph.addEdge(A,B);
        testPaths(graph, A, B, 4);
    }

    @Test
    void testFindPathsUnDiMultipleGraph() {
        AbstractGraph graph = GraphReader.unDiGraph("A B 2.5");//"A C 5.2 B C 1.0 B D 3.0 C D 2.0 "); //C E 4.0 D E 1.0");
        Vertex A = graph.getVertex("A");
        Vertex B = graph.getVertex("B");
        graph.addEdge(A,B);
        testPaths(graph, A, B, 2);
        graph.addEdge(A,B);
        testPaths(graph, A, B, 3);

        graph = GraphReader.unDiGraph("A B 2.5 A C 5.2 B C 1.0 B D 3.0 C D 2.0 ");
        A = graph.getVertex("A");
        B = graph.getVertex("B");
        graph.addEdge(A,B);
        testPaths(graph, A, B, 4);
    }






}
