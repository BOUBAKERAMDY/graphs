package graphs.tools;

import graphs.graphcore.DuplicateTagException;
import graphs.graphcore.UnDiGraph;
import graphs.graphcore.Vertex;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PathTest {

    @Test
    void testPathSimple() throws DuplicateTagException {
        UnDiGraph graph = new UnDiGraph();
        Vertex v1 = graph.addVertex("A");
        Vertex v2 = graph.addVertex("B");
        graph.addEdge(v1, v2);
        Path p = new Path(v1);
        p.add(graph.getEdges(v1, v2).get(0));
        assertEquals("A -> B : 1.0", p.toString());
        assertEquals(List.of(v1,v2), p.vertices());

        p = new Path(graph, List.of(v1, v2));
        assertEquals("A -> B : 1.0", p.toString());
        p = new Path(List.of(graph.getEdges(v1, v2).get(0)), v2);
        assertEquals("B -> A : 1.0", p.toString());
        assertEquals(List.of(v2,v1), p.vertices());
    }

    @Test
    void testPath() throws DuplicateTagException {
        UnDiGraph graph = new UnDiGraph();
        Vertex v1 = graph.addVertex("A");
        Vertex v2 = graph.addVertex("B");
        Vertex v3 = graph.addVertex("C");
        Vertex v4 = graph.addVertex("D");
        graph.addEdge(v1,v2);
        graph.addEdge(v2,v3);
        graph.addEdge(v1,v3);
        graph.addEdge(v3,v4);

        // A -> B -> C
        Path p = new Path(v1);
        p.add(graph.getEdges(v1, v2).get(0));
        p.add(graph.getEdges(v2,v3).get(0));
        assertEquals("A -> B -> C : 2.0", p.toString());
        assertEquals(List.of(v1,v2,v3), p.vertices());

        //C -> B -> A
        p = new Path(graph, List.of(v3,v2,v1));
        assertEquals(List.of(v3,v2,v1), p.vertices());
        assertEquals("C -> B -> A : 2.0", p.toString());

        // A -> C -> D
        p = new Path(graph, List.of(v1,v3,v4));
        assertEquals(List.of(v1,v3,v4), p.vertices());
        assertEquals("A -> C -> D : 2.0", p.toString());

        // A -> B -> C -> D
        p = new Path(graph, List.of(v1,v2,v3,v4));
        assertEquals(List.of(v1,v2,v3,v4), p.vertices());
        assertEquals("A -> B -> C -> D : 3.0", p.toString());

        //D -> C -> B -> A
        p = new Path(graph, List.of(v4,v3,v2,v1));
        assertEquals(List.of(v4,v3,v2,v1), p.vertices());
        assertEquals("D -> C -> B -> A : 3.0", p.toString());



    }

}