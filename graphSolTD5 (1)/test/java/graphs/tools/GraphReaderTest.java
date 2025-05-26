package graphs.tools;

import graphs.graphcore.DiGraph;
import graphs.graphcore.Edge;
import graphs.graphcore.UnDiGraph;
import org.junit.jupiter.api.Test;

import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * This is a convenience class to test and build a graph from an input String.
 * 
 * Non weighted graphs are given as a sequence of pairs of vertex tags like "A B A C ..."
 * which will add the vertices A, B, C, etc. and the edge (A,B), (A,C), etc. to the graph.
 * 
 * Weighted graphs are given as sequence of three items, two vertices followed by a weight
 * (a double value) like "A B 2.5 A C 5.2 ..." which will add vertices A, B, C, etc. and the
 * weighted edges (A,B,2.5), (A,C,5.2), etc. to the graph.
 * 
 * Additionally the class has the graphs mentioned in the labs already defined as public 
 * static items which can be used in lab code, like GraphReader.U1, GraphReader.D1, etc.
 * 
 */
public class GraphReaderTest {

	Logger logger = Logger.getLogger(GraphReaderTest.class.getName());
/*
	UnDiGraph U1 = GraphReaderTestExamples.U1;
			//GraphReader.unDiGraph("A B C D C E D E E F G H G K H I H J H K I J");
	UnDiGraph U2 = GraphReaderTestExamples.U2;
	//public static UnDiGraph U2 = GraphReader.unDiGraph("A D A E A J B C B F B G B I C F C G C H D E D F G H");
	public static UnDiGraph U3 = GraphReader.unDiGraph("A E B D B F B H C G G I G J");
	public static UnDiGraph U4 = GraphReader.unDiGraph("A C A D B E B K C E C J D F D H E G E I");
	public static DiGraph D1 = GraphReader.diGraph("A E B D B F C E D F F C F E G A G B G C");
	public static DiGraph D2 = GraphReader.diGraph("A C A E B D D F D G E C F B");
	public static DiGraph D3 = GraphReader.diGraph("A C B D C E C G D A D F E A F B");

	public static DiGraph D4 =
		GraphReader.diGraph("A B A C A D B D B E C F C D D F D G D E F I F G E G E H G I G J G H I J H J");

	public static UnDiGraph tree = GraphReader.unDiGraph("A B A C B D B E C F C G C H D I D J D K E L E M E N E O F P F Q F R F S G T G U G V H W H X H Y H Z");
*/

	//public static UnDiGraph weightedGraph = GraphReader.unDiGraph("A B 2.5 A C 5.2 B C 1.0 B D 3.0 C D 2.0 C E 4.0 D E 1.0");
	//public static DiGraph weightedDiGraph = GraphReader.diGraph("A B 2.5 A C 5.2 B C 1.0 B D 3.0 C D 2.0 C E 4.0 D E 1.0");

	@Test
	void testReadWeightedDIGraph() {
		DiGraph graph = GraphReader.diGraph("A B 2.5");
		assertEquals(2, graph.nbVertices());
		assertEquals(1, graph.nbEdges());
		Edge edge = graph.findEdge(graph.getVertex("A"), graph.getVertex("B"));
		assertEquals(2.5, edge.weight());

		logger.info("-------------------------------\n" + graph);

		graph = GraphReader.diGraph("A B 2.5 A C 5.2");
		assertEquals(3, graph.nbVertices());
		assertEquals(2, graph.nbEdges());
		edge = graph.findEdge(graph.getVertex("A"), graph.getVertex("C"));
		assertEquals(5.2, edge.weight());
		logger.info("-------------------------------\n" + graph);

		DiGraph wg = GraphReader.diGraph("A B 2.5 A C 5.2 B C 1.0 B D 3.0 C D 2.0 C E 4.0 D E 1.0");
		assertEquals(5, wg.nbVertices());
		assertEquals(7, wg.nbEdges());
		logger.info("-------------------------------\n" + wg);
	}

	@Test
	void testReadWeightedUnDiGraph() {
		UnDiGraph graph = GraphReader.unDiGraph("A B 2.5");
		assertEquals(2, graph.nbVertices());
		assertEquals(1, graph.nbEdges());
		Edge edge = graph.findEdge(graph.getVertex("A"), graph.getVertex("B"));
		assertEquals(2.5, edge.weight());

		logger.info("-------------------------------\n" + graph);

		graph = GraphReader.unDiGraph("A B 2.5 A C 5.2");
		assertEquals(3, graph.nbVertices());
		assertEquals(2, graph.nbEdges());
		edge = graph.findEdge(graph.getVertex("A"), graph.getVertex("C"));
		assertEquals(5.2, edge.weight());
		logger.info("-------------------------------\n" + graph);

		UnDiGraph wg = GraphReader.unDiGraph("A B 2.5 A C 5.2 B C 1.0 B D 3.0 C D 2.0 C E 4.0 D E 1.0");
		assertEquals(5, wg.nbVertices());
		assertEquals(7, wg.nbEdges());
		logger.info("-------------------------------\n" + wg);
	}

	@Test
	void testReadUnDiGraph(){
		UnDiGraph graph = GraphReader.unDiGraph("A B C D C E D E ");
		assertEquals(5, graph.nbVertices());
		assertEquals(4, graph.nbEdges());
		Edge edge = graph.findEdge(graph.getVertex("A"), graph.getVertex("B"));
		assertEquals(1.0, edge.weight());
		logger.info("-------------------------------\n" + graph);

	}
}
