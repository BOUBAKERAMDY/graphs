package graphs.graphcore;

import graphs.tools.GraphReader;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


/**
 * A class to demonstrate the use of the graph methods
 * @author Marc Gaetano
 * @author Mireille Blay
 *
 */
class TestGraph {


	/**
	 * Displays all information about the graph 'graph'
	 * (directed or undirected) named 'name'
	 */
	private static void visualizeGraphMethods(String name, Graph graph) {
		System.out.println(name);
		System.out.println(graph);

		System.out.println("\nVertices enumeration:");
		for ( Vertex vertex :  graph.vertices() )
			System.out.print(vertex.getTag() + " ");
		System.out.println();

		System.out.println("\nEdges enumeration:");
		System.out.print("Number of Edges: " + graph.nbEdges() + " ");
		for ( Edge edge :  graph.edges() )
			System.out.print("(" + edge + ":" + edge.origin() + "," + edge.destination() + ") ");
		System.out.println();

		System.out.println("\nAdjacents enumeration:");
		for ( Vertex vertex :  graph.vertices() ) {
			System.out.print("Adjacents of vertex " + vertex + ": ");
			for ( Vertex adjacent : graph.adjacents(vertex) )
				System.out.print(adjacent + " ");
			System.out.println();
		}

		System.out.println("\nIncidents enumeration:");
		for ( Vertex vertex :  graph.vertices() ) {
			System.out.println("Incident edges of vertex " + vertex + ":");
			for ( Edge edge : graph.incidents(vertex) )
				System.out.println("   " + edge + ", origin = " + edge.origin() + ", destination = " + edge.destination());
			System.out.println();
		}

		System.out.println("\nIn-degree of vertices:");
		for ( Vertex vertex :  graph.vertices() )
			System.out.println("in-degree(" + vertex + ") = " + graph.inDegree(vertex));
		System.out.println();

		System.out.println("\nOut-degree of vertices:");
		for ( Vertex vertex :  graph.vertices() )
			System.out.println("out-degree(" + vertex + ") = " + graph.outDegree(vertex));
		System.out.println();

		System.out.println("\n(total) degree of vertices:");
		for ( Vertex vertex :  graph.vertices() )
			System.out.println("degree(" + vertex + ") = " + graph.degree(vertex));
		System.out.println();

	}

	@Test
	void testMultiEdges() {
		DiGraph u1 = GraphReader.diGraph("A B 1.0 A B 2.0");
		visualizeGraphMethods("U1:",u1);
		assertEquals(2,u1.nbEdges());
		assertEquals(2,u1.nbVertices());
		assertEquals(2,u1.incidents(u1.getVertex("A")).size());
		assertEquals(0,u1.incidents(u1.getVertex("B")).size());
		assertEquals(2,u1.degree(u1.getVertex("A")));
		assertEquals(2,u1.degree(u1.getVertex("B")));
	}


	@Test
	void testReadWeightedGraph() {
		GraphReader.diGraph("A B 2.5");
		DiGraph d2 = GraphReader.diGraph("A B 2.5 A C 5.2");
		assertEquals(2,d2.incidents(d2.getVertex("A")).size());
		assertEquals(0,d2.incidents(d2.getVertex("B")).size());
		assertEquals(0,d2.incidents(d2.getVertex("C")).size());

		System.out.println("-------------------------------\n");
		DiGraph wg = GraphReader.diGraph("A B 2.5 A C 5.2 B C 1.0 B D 3.0 C D 2.0 C E 4.0 D E 1.0");
		visualizeGraphMethods("Weighted graph:", wg );
		assertEquals(5,wg.nbVertices());
		assertEquals(7,wg.nbEdges());
		assertEquals(2,wg.incidents(wg.getVertex("A")).size());
		assertEquals(2,wg.incidents(wg.getVertex("B")).size());
		assertEquals(2,wg.incidents(wg.getVertex("C")).size());
		System.out.println("-------------------------------\n");
		Edge e = wg.findEdge(wg.getVertex("A"),wg.getVertex("B"));
		assertEquals(2.5, e.weight());
		e = wg.findEdge(wg.getVertex("B"),wg.getVertex("D"));
		assertEquals(3.0, e.weight());
	}

	@Test
	void testIncidentWeightedGraphWithLoopAndDouble() {
		GraphReader.diGraph("A B 2.5");
		DiGraph d2 = GraphReader.diGraph("A B 2.5 A C 5.2 C A 1.0 C A 3.0");
		assertEquals(2, d2.incidents(d2.getVertex("A")).size());
		assertEquals(0, d2.incidents(d2.getVertex("B")).size());
		assertEquals(2, d2.incidents(d2.getVertex("C")).size());

	}

		@Test
	void testWeighted(){
		assertTrue(GraphReader.weighted("A B 2.5"));
		System.out.println("-------------------------------\n");
		assertTrue(GraphReader.weighted("A B 2.5 A C 5.2 B C 1.0 B D 3.0 C D 2.0 C E 4.0 D E 1.0"));
	}

	@Test
	void testUnDiGraph() {
		UnDiGraph d1 = GraphReader.unDiGraph("A B 2.5 A C 5.2");
		Edge e = d1.findEdge(d1.getVertex("A"), d1.getVertex("B"));
		assertEquals(2.5, e.weight());
		assertEquals(2, d1.incidents(d1.getVertex("A")).size());
		assertEquals(1, d1.incidents(d1.getVertex("B")).size());
		assertEquals(1, d1.incidents(d1.getVertex("C")).size());
		e= d1.findEdge(d1.getVertex("B"), d1.getVertex("C"));
		assertNull(e);
	}

	@Test
	void testDiGraph() {
		DiGraph d1 = GraphReader.diGraph("A B 2.5 A C 5.2");
		Edge e = d1.findEdge(d1.getVertex("A"), d1.getVertex("B"));
		assertEquals(2.5, e.weight());
		assertEquals(2, d1.incidents(d1.getVertex("A")).size());
		assertEquals(0, d1.incidents(d1.getVertex("B")).size());
		assertEquals(0, d1.incidents(d1.getVertex("C")).size());
		e= d1.findEdge(d1.getVertex("B"), d1.getVertex("C"));
		assertNull(e);
		System.out.println("-------------------------------\n");
	}

}
