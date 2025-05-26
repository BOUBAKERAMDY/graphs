package graphs.tools;

import graphs.graphcore.*;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import graphs.tools.solutions.GraphHelper;

import static org.junit.jupiter.api.Assertions.*;

class GraphHelperTest {


    @Test
    void isInvolvedInACycleTest() throws DuplicateTagException {
        GraphHelper gh = new GraphHelper();
        DiGraph g = new DiGraph();
        Vertex a = g.addVertex("A");
        Vertex b = g.addVertex("B");
        g.addEdge(a, b);
        g.addEdge(b, a);
        assertTrue(gh.isInvolvedInACycle(g, a));
        g = new DiGraph();
        a = g.addVertex("A");
        b = g.addVertex("B");
        Vertex c = g.addVertex("C");
        g.addEdge(a, b);
        g.addEdge(b, c);
        assertFalse(gh.isInvolvedInACycle(g, a));
        assertFalse(gh.isInvolvedInACycle(g,b));
        assertFalse(gh.isInvolvedInACycle(g,c));
        g.addEdge(c,a);
        assertTrue(gh.isInvolvedInACycle(g,a));
        assertTrue(gh.isInvolvedInACycle(g,b));
        assertTrue(gh.isInvolvedInACycle(g,c));
        g = new DiGraph();
        a = g.addVertex("A");
        b = g.addVertex("B");
        c = g.addVertex("C");
        g.addEdge(a, b);
        g.addEdge(b, c);
        g.addEdge(c, b);
        assertFalse(gh.isInvolvedInACycle(g,a));
        assertTrue(gh.isInvolvedInACycle(g,b));
        assertTrue(gh.isInvolvedInACycle(g,c));
        g = new DiGraph();
        a = g.addVertex("A");
        b = g.addVertex("B");
        c = g.addVertex("C");
        Vertex d = g.addVertex("D");
        g.addEdge(a, b);
        g.addEdge(b, c);
        g.addEdge(c, d);
        g.addEdge(d, b);
        assertFalse(gh.isInvolvedInACycle(g,a));
        assertTrue(gh.isInvolvedInACycle(g,b));
    }

    /***
     * Test the method hasCycle in undirected and directed graphs
     * @throws DuplicateTagException
     */

    @Test
    void hasCycleInGraphsOfOneNode() throws DuplicateTagException {
        UnDiGraph g = new UnDiGraph();
        g.addVertex("A");
        GraphHelper gh = new GraphHelper();
        assertFalse(gh.hasCycle(g));

        DiGraph dg = new DiGraph();
        dg.addVertex("A");
        assertFalse(gh.hasCycle(dg));
    }
    @Test
    void hasCycleInUndirectedGraphsOfTwoNodes() throws DuplicateTagException {
        UnDiGraph g = new UnDiGraph();
        Vertex a = g.addVertex("A");
        Vertex b = g.addVertex("B");
        g.addEdge(a, b);
        GraphHelper gh = new GraphHelper();
        assertFalse(gh.hasCycle(g));
    }
    @Test
    void hasCycleInDirectedGraphsOfTwoNodes() throws DuplicateTagException {
        DiGraph g = new DiGraph();
        Vertex a = g.addVertex("A");
        Vertex b = g.addVertex("B");
        g.addEdge(a, b);
        GraphHelper gh = new GraphHelper();
        assertFalse(gh.hasCycle(g));
        g.addEdge(b, a);
        assertTrue(gh.hasCycle(g));
    }

    @Test
    void hasCycleInUndirectedGraphsOfTreeNodes() throws DuplicateTagException {
        UnDiGraph g = new UnDiGraph();
        Vertex a = g.addVertex("A");
        Vertex b = g.addVertex("B");
        g.addEdge(a, b);
        Vertex c = g.addVertex("C");
        GraphHelper gh = new GraphHelper();
        assertFalse(gh.hasCycle(g));
        g.addEdge(b, c);
        assertFalse(gh.hasCycle(g));
        g.addEdge(c, a);
        assertTrue(gh.hasCycle(g));
    }

    @Test
    void hasCycleInUndirectedGraphsOfFourNodes() throws DuplicateTagException {
        UnDiGraph g = new UnDiGraph();
        Vertex h = g.addVertex("H");
        Vertex b = g.addVertex("B");
        Vertex f = g.addVertex("F");
        Vertex d = g.addVertex("D");
        g.addEdge(h, b);
        g.addEdge(b, f);
        g.addEdge(b, d);
        GraphHelper gh = new GraphHelper();
        assertFalse(gh.hasCycle(g));
        g.addEdge(f, d);
        assertTrue(gh.hasCycle(g));
    }
    @Test
    void hasCycleInUndirectedGraphsOfU3() {
        UnDiGraph u3 = GraphReader.unDiGraph("A E B D B F B H C G G I G J");
        GraphHelper gh = new GraphHelper();
        assertFalse(gh.hasCycle(u3));
    }

    @Test
    void hasCycleInUndirectedGraphsOfU2() {
        UnDiGraph u2 = GraphReader.unDiGraph("A D A E A J B C B F B G B I C F C G C H D E D F G H");
        GraphHelper gh = new GraphHelper();
        assertTrue(gh.hasCycle(u2));
    }
    @Test
    void hasCycleInDirectedGraphsOfD2() {
        DiGraph d2 = GraphReader.diGraph("A C A E B D D F D G E C F B");
        GraphHelper gh = new GraphHelper();
        assertTrue(gh.hasCycle(d2));
    }

    @Test
    void hasCycleInDirectedGraphsOfThreeNodes() throws DuplicateTagException {
        DiGraph g = new DiGraph();
        Vertex a = g.addVertex("A");
        Vertex b = g.addVertex("B");
        g.addEdge(a, b);
        Vertex c = g.addVertex("C");
        GraphHelper gh = new GraphHelper();
        assertFalse(gh.hasCycle(g));
        g.addEdge(b, c);
        assertFalse(gh.hasCycle(g));
        g.addEdge(a, c);
        assertFalse(gh.hasCycle(g));
        g.addEdge(c, a);
        assertTrue(gh.hasCycle(g));
    }

    @Test
    void hasCycleInDirectedGraphsOfFourNodes() throws DuplicateTagException {
        DiGraph g = new DiGraph();
        Vertex a = g.addVertex("A");
        Vertex b = g.addVertex("B");
        g.addEdge(a, b);
        Vertex c = g.addVertex("C");
        GraphHelper gh = new GraphHelper();
        assertFalse(gh.hasCycle(g));
        g.addEdge(b, c);
        assertFalse(gh.hasCycle(g));
        g.addEdge(a, c);
        //a->b->c   a-> c
        assertFalse(gh.hasCycle(g));
        Vertex d = g.addVertex("D");
        g.addEdge(c, d);
        assertFalse(gh.hasCycle(g));
        g.addEdge(d, a);
        //a->b->c->d->a  a-> c
        assertTrue(gh.hasCycle(g));
    }



    /****
     * Test the method findVertexPath in undirected and directed graphs
     * @throws DuplicateTagException
     */

    @Test
    void findPathInGraphOfOneNode() throws DuplicateTagException {
        UnDiGraph g = new UnDiGraph();
        Vertex a = g.addVertex("A");
        GraphHelper gh = new GraphHelper();
        List<Vertex> path = gh.findPath(g,a,a);
        assertEquals(1,path.size());
        assertEquals(a,path.get(0));

        DiGraph dg = new DiGraph();
        a = dg.addVertex("A");
        gh = new GraphHelper();
        path = gh.findPath(dg,a,a);
        assertEquals(1,path.size());
        assertEquals(a,path.get(0));
    }


    @Test
    void findPathInUnDiGraphOfTwoNodes() throws DuplicateTagException {
        UnDiGraph g = new UnDiGraph();
        Vertex a = g.addVertex("A");
        Vertex b = g.addVertex("B");
        GraphHelper gh = new GraphHelper();
        List<Vertex> path = gh.findPath(g,a,b);
        assertTrue(path.isEmpty());
        g.addEdge(a,b);
        path = gh.findPath(g,a,b);
        assertEquals(2,path.size());
        assertEquals(a,path.get(0));
        assertEquals(b,path.get(1));
        path = gh.findPath(g,b,a);
        assertEquals(2,path.size());
        assertEquals(b,path.get(0));
        assertEquals(a,path.get(1));

    }

    @Test
    void findPathInDiGraphOfTwoNodes() throws DuplicateTagException {
        DiGraph g = new DiGraph();
        Vertex a = g.addVertex("A");
        Vertex b = g.addVertex("B");
        GraphHelper gh = new GraphHelper();
        List<Vertex> path = gh.findPath(g,a,b);
        assertTrue(path.isEmpty());
        g.addEdge(a,b);
        path = gh.findPath(g,a,b);
        assertEquals(2,path.size());
        assertEquals(a,path.get(0));
        assertEquals(b,path.get(1));
        path = gh.findPath(g,b,a);
        assertTrue(path.isEmpty());
    }

    @Test
    void checkMultipleHelperConcurrency() throws DuplicateTagException {
        UnDiGraph g = GraphReader.unDiGraph("A C A D B E B K C E C J D F D H E G E I");
        GraphHelper gh = new GraphHelper();
        GraphHelper gh2 = new GraphHelper();
        Stream<GraphHelper> stream = Stream.of(gh, gh2);
        stream.parallel().forEach(helper -> {
            List<Vertex> path = helper.findPath(g, g.getVertex("H"), g.getVertex("K"));
            assertEquals(7, path.size());
            assertEquals(6, helper.computeDistanceOfPath(path, g));
        });
    }
    @Test
    void findPathInUnDiGraphOfU4(){
        UnDiGraph g = GraphReader.unDiGraph("A C A D B E B K C E C J D F D H E G E I");
        GraphHelper gh = new GraphHelper();
        List<Vertex> path = gh.findPath(g,g.getVertex("H"),g.getVertex("K"));
        assertEquals(7,path.size());
        assertEquals(g.getVertex("H"),path.get(0));
        assertEquals(g.getVertex("D"),path.get(1));
        assertEquals(g.getVertex("A"),path.get(2));
        assertEquals(g.getVertex("C"),path.get(3));
        assertEquals(g.getVertex("E"),path.get(4));
        assertEquals(g.getVertex("B"),path.get(5));
        assertEquals(g.getVertex("K"),path.get(6));
    }

    @Test
    void findPathInDiGraphOfD3(){
        DiGraph g = GraphReader.diGraph("A C B D C E C G D A D F E A F B");
        GraphHelper gh = new GraphHelper();
        List<Vertex> path = gh.findPath(g,g.getVertex("F"),g.getVertex("E"));
        assertEquals(6,path.size());
        assertEquals(g.getVertex("F"),path.get(0));
        assertEquals(g.getVertex("B"),path.get(1));
        assertEquals(g.getVertex("D"),path.get(2));
        assertEquals(g.getVertex("A"),path.get(3));
        assertEquals(g.getVertex("C"),path.get(4));
        assertEquals(g.getVertex("E"),path.get(5));
    }

    /***
     * Test the method findRoot in undirected and directed graphs
     * @throws DuplicateTagException
     */

    @Test
    void findRootInDirectedGraph() throws DuplicateTagException {
        DiGraph g = new DiGraph();
        Vertex a = g.addVertex("A");
        Vertex b = g.addVertex("B");
        g.addEdge(a, b);
        GraphHelper gh = new GraphHelper();
        Optional<Vertex> root = gh.findRoot(g);
        assertEquals(a, root.get());
        Vertex c = g.addVertex("C");
        g.addEdge(a, c);
        root = gh.findRoot(g);
        assertEquals(a, root.get());
        Vertex d = g.addVertex("D");
        g.addEdge(c, d);
        root = gh.findRoot(g);
        assertEquals(a, root.get());
        Vertex e = g.addVertex("E");
        root = gh.findRoot(g);
        assertTrue(root.isEmpty());
        Vertex f = g.addVertex("F");
        g.addEdge(e, f);
        root = gh.findRoot(g);
        assertTrue(root.isEmpty());
    }

    @Test
    void findRoot4TreeD4() {
        DiGraph g = GraphReaderTestExamples.D4;
        GraphHelper gh = new GraphHelper();
        Optional<Vertex> root = gh.findRoot(g);
        assertFalse(gh.hasCycle(g));
        Vertex a = g.getVertex("A");
        assertEquals(a, root.get());
    }

    @Test
    void findRootsD4() {
        DiGraph g = GraphReaderTestExamples.D4;
        GraphHelper gh = new GraphHelper();
        List<Vertex> roots = gh.findRoots(g);
        assertEquals(1, roots.size());
        Vertex a = g.getVertex("A");
        assertEquals(a, roots.get(0));
    }

    @Test
    void findRoot4TreeFromLesson() {
        DiGraph g = GraphReader.diGraph("B D B E A B A C C F F G F H");
        GraphHelper gh = new GraphHelper();
        assertFalse(gh.hasCycle(g));
        Optional<Vertex> root = gh.findRoot(g);
        assertTrue(root.isPresent());
        Vertex a = g.getVertex("A");
        assertEquals(a, root.get());
    }

    /*

     */
    @Test
    public void testMULTIPLERoots() {
        DiGraph g = GraphReader.diGraph("A C B C C D D E D F F B B A ");
        GraphHelper gh = new GraphHelper();
        List<Vertex> roots = gh.findRoots(g);
        assertEquals(5, roots.size());
        Vertex A = g.getVertex("A");
        Vertex B = g.getVertex("B");
        assertTrue(roots.contains(A));
        assertTrue(roots.contains(B));
        assertTrue(roots.contains(g.getVertex("C")));
        assertTrue(roots.contains(g.getVertex("D")));
        assertFalse(roots.contains(g.getVertex("E")));
        assertTrue(roots.contains(g.getVertex("F")));
    }

    @Test
    public void testNOTreeRoot() {
        DiGraph g = GraphReader.diGraph("A C B C C D D E D F F B B A ");
        GraphHelper gh = new GraphHelper();
        Optional<Vertex> root = gh.findRoot(g);
        assertTrue(root.isEmpty());
    }

    @Test
    public void testOneRootsAndTreeRoot() {
        DiGraph g = GraphReader.diGraph("A C B C C D D E D F F B ");
        GraphHelper gh = new GraphHelper();
        List<Vertex> roots = gh.findRoots(g);
        assertEquals(1, roots.size());
        Vertex A = g.getVertex("A");
        assertTrue(roots.contains(A));

        Optional<Vertex> root = gh.findRoot(g);
        System.out.println(root);
        assertTrue(root.isPresent());
        assertEquals(A, root.get());


    }

    @Test
    public void testNoRoots() {
        DiGraph g = GraphReader.diGraph("A C B C C D D E D F ");
        GraphHelper gh = new GraphHelper();
        List<Vertex> roots = gh.findRoots(g);
        assertEquals(0, roots.size());
    }

    @Test
    void findRootsFromLesson() {
        DiGraph g = GraphReader.diGraph("B D B E A B A C C F F G F H");
        GraphHelper gh = new GraphHelper();
        List<Vertex> roots = gh.findRoots(g);
        System.out.println(roots);
        assertEquals(1, roots.size());
        assertTrue(roots.contains(g.getVertex("A")));
    }

    @Test
    void findRootsInACycle() {
        DiGraph g = GraphReader.diGraph("A B B C C A");
        GraphHelper gh = new GraphHelper();
        assertTrue(gh.hasCycle(g));
        List<Vertex> roots = gh.findRoots(g);
        System.out.println(roots);
        assertEquals(3, roots.size());
        Vertex a = g.getVertex("A");
    }

    //On devrait etre en erreur si on cherche la racine de l'arbre
    @Test
    void findRootInACycle() {
        DiGraph g = GraphReader.diGraph("A B B C C A");
        GraphHelper gh = new GraphHelper();
        assertTrue(gh.hasCycle(g));
        Optional<Vertex> root = gh.findRoot(g);
        System.out.println(root);
        assertTrue(root.isEmpty());
    }

    /***
     * Test the method computeDistanceOfPath in undirected and directed graphs
     * @throws DuplicateTagException
     */
    @Test
    void testDistanceForAPathOfTwoNodes() throws DuplicateTagException {
        UnDiGraph g = new UnDiGraph();
        Vertex a = g.addVertex("A");
        Vertex b = g.addVertex("B");
        g.addEdge(a,b);
        GraphHelper gh = new GraphHelper();
        List<Vertex> path = gh.findPath(g,a,b);
        assertEquals(1,gh.computeDistanceOfPath(path,g));
        path = gh.findPath(g,b,a);
        assertEquals(1,gh.computeDistanceOfPath(path,g));
        DiGraph dg = new DiGraph();
        a = dg.addVertex("A");
        b = dg.addVertex("B");
        dg.addEdge(a,b);
        gh = new GraphHelper();
        path = gh.findPath(dg,a,b);
        assertEquals(1,gh.computeDistanceOfPath(path,dg));
        UnDiGraph wg = new UnDiGraph();
        a = wg.addVertex("A");
        b = wg.addVertex("B");
        wg.addEdge(a,b,2);
        gh = new GraphHelper();
        path = gh.findPath(wg,a,b);
        assertEquals(2,gh.computeDistanceOfPath(path,wg));
    }



    @Test
    void testDistanceForAPathOfTreeNodes() throws DuplicateTagException {
        UnDiGraph g = new UnDiGraph();
        Vertex a = g.addVertex("A");
        Vertex b = g.addVertex("B");
        g.addEdge(a,b);
        Vertex c = g.addVertex("C");
        g.addEdge(b,c);
        GraphHelper gh = new GraphHelper();
        List<Vertex> path = gh.findPath(g,a,c);
        assertEquals(2,gh.computeDistanceOfPath(path,g));
        UnDiGraph wg = new UnDiGraph();
        a = wg.addVertex("A");
        b = wg.addVertex("B");
        wg.addEdge(a,b,2);
        c = wg.addVertex("C");
        wg.addEdge(b,c,3);
        gh = new GraphHelper();
        path = gh.findPath(wg,a,c);
        assertEquals(5,gh.computeDistanceOfPath(path,wg));
    }

    @Test
    void testDistanceForAPath() {
        UnDiGraph g = GraphReader.unDiGraph("A C A D B E B K C E C J D F D H E G E I");
        GraphHelper gh = new GraphHelper();
        List<Vertex> path = gh.findPath(g,g.getVertex("H"),g.getVertex("K"));
        assertEquals(7,path.size());
        assertEquals(6,gh.computeDistanceOfPath(path,g));

        UnDiGraph gw = GraphReader.unDiGraph("A C 5.2 B C 1.0 C D 2.0 D E 1.0");
        path = gh.findPath(gw,gw.getVertex("A"),gw.getVertex("E"));
        assertEquals(4,path.size());
        assertEquals(8.2,gh.computeDistanceOfPath(path,gw));

    }


}