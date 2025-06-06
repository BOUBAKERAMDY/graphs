package graphs.tools;

import graphs.graphcore.DuplicateTagException;
import graphs.graphcore.UnDiGraph;
import graphs.graphcore.Vertex;
import graphs.tools.solutions.ConnectedComponents;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ConnectedComponentsTest {

    @Test
    void find4AGraphOfOneNode() throws DuplicateTagException {
        UnDiGraph g = new UnDiGraph();
        g.addVertex("A");
        ConnectedComponents ccFinder = new ConnectedComponents(g);
        assertEquals(-1,ccFinder.getComponentNumber(g.getVertex("A")));
        Map<Vertex,Integer> cc = ccFinder.find();
        assertEquals(1,ccFinder.getMaxConnectedComponentNumber());
        assertEquals(1,cc.size());
        assertEquals(1,cc.get(g.getVertex("A")));
        assertEquals(1,ccFinder.getComponentNumber(g.getVertex("A")));
    }

    @Test
    void find4AGraphOfTwoNodes() throws DuplicateTagException {
        UnDiGraph g = new UnDiGraph();
        Vertex a = g.addVertex("A");
        Vertex b = g.addVertex("B");
        ConnectedComponents ccFinder = new ConnectedComponents(g);
        Map<Vertex,Integer> cc = ccFinder.find();
        assertEquals(2,ccFinder.getMaxConnectedComponentNumber());
        assertEquals(2,cc.size());
        assertNotEquals(cc.get(g.getVertex("B")),cc.get(g.getVertex("A")));
        //We add an edge between A and B
        g.addEdge(a,b);
        ccFinder = new ConnectedComponents(g);
        cc = ccFinder.find();
        assertEquals(1,ccFinder.getMaxConnectedComponentNumber());
        assertEquals(2,cc.size());
        assertEquals(1,cc.get(g.getVertex("A")));
        assertEquals(1,cc.get(g.getVertex("B")));
    }


    @Test
    void find4AGraphU1() {
        //UnDiGraph g = GraphReaderTestExamples.U1;
        UnDiGraph g = GraphReader.unDiGraph("A B C D C E D E E F G H G K H I H J H K I J");
        ConnectedComponents ccFinder = new ConnectedComponents(g);
        Map<Vertex,Integer> cc = ccFinder.find();
        assertEquals(3,ccFinder.getMaxConnectedComponentNumber());
        assertEquals(11,cc.size());
        assertEquals(cc.get(g.getVertex("A")),cc.get(g.getVertex("B")));
        List<Vertex> vertices = new ArrayList<>();
            vertices.add(g.getVertex("D"));
            vertices.add(g.getVertex("E"));
            vertices.add(g.getVertex("F"));
        vertices.parallelStream().forEach(v -> assertEquals(cc.get(g.getVertex("C")),cc.get(v)));
        vertices = new ArrayList<>() {{
            add(g.getVertex("H"));
            add(g.getVertex("I"));
            add(g.getVertex("J"));
            add(g.getVertex("K"));
        }};
        //vertices.parallelStream().forEach(v -> assertEquals(cc.get(g.getVertex("G")),cc.get(v)));
        int componentNumber = cc.get(g.getVertex("G"));
        assertTrue(vertices.parallelStream().anyMatch(v -> cc.get(v) == componentNumber));
    }

    @Test
    void find4AGraphU2() {
        UnDiGraph g = GraphReader.unDiGraph("A D A E A J B C B F B G B I C F C G C H D E D F G H");
        ConnectedComponents ccFinder = new ConnectedComponents(g);
        Map<Vertex,Integer> cc = ccFinder.find();
        assertEquals(1,ccFinder.getMaxConnectedComponentNumber());
        List<Vertex> vertices= new ArrayList<>() {{
            add(g.getVertex("A"));
            add(g.getVertex("B"));
            add(g.getVertex("C"));
            add(g.getVertex("D"));
            add(g.getVertex("E"));
            add(g.getVertex("F"));
            add(g.getVertex("G"));
            add(g.getVertex("H"));
            add(g.getVertex("I"));
        }};
        //vertices.parallelStream().forEach(v -> assertEquals(1,cc.get(v)));
        assertTrue(vertices.parallelStream().anyMatch(v -> cc.get(v) == 1));

    }
}