package graphs.graphcore;

import java.util.*;
//import java.util.logging.Logger;

/**
 * A class for undirected graph
 */
public class UnDiGraph extends AbstractGraph {
	//public static final Logger LOGGER = Logger.getLogger(UnDiGraph.class.getName());

	/**
	 * builds an undirected graph with no vertex
	 */
	public UnDiGraph() {
		super();
	}

	@Override
	public Edge addEdge(Vertex u, Vertex v, double weight) {
		checkVertex(u);
		checkVertex(v);
		Edge e ;
		e = storeEdge(u,v,weight);
		add(u,v,weight);
		add(v,u,weight);
		nbEdges++;
		return e;
	}

	@Override
	public void removeEdge(Vertex u, Vertex v) {
		checkVertex(u);
		checkVertex(v);
		if ( remove(u,v) ) {
			remove(v,u);
			nbEdges--;
		}
	}

	@Override
	public int degree(Vertex u) {
		checkVertex(u);
		return adjacencyList.get(u).size();
	}

	@Override
	public int inDegree(Vertex u) {
		return degree(u);
	}

	@Override
	public int outDegree(Vertex u) {
		return degree(u);
	}

	@Override
	public String toString() {
		return "Undirected Graph\n" + super.toString();
	}

	/**
	 * Returns one edge between u and v
	 * if it exists in the graph
	 * if there is no edge between u and v
	 * the method returns null
	 */
	@Override
    public Edge findEdge(Vertex u, Vertex v) {

		Map<Vertex, Set<Edge>> mapU = edges.get(u);
		Map<Vertex, Set<Edge>> mapV = edges.get(v);

		if ( ( mapU == null ) && (mapV == null) )
			return null;
		if ( ( ( mapU == null ) && (mapV.get(u) == null) ) ||
				( ( mapV == null ) && (mapU.get(v) == null) ) )
			return null;

	/*	if ( ( mapU == null ) && edges.get(u).get(v) != null )
			return edges.get(v).get(u).stream().sorted(Comparator.comparingDouble(Edge::weight)).findFirst().orElse(null);

		if ( ( mapU == null ) && edges.get(v).get(u) != null )
			return edges.get(v).get(u).stream().sorted(Comparator.comparingDouble(Edge::weight)).findFirst().orElse(null);
*/
		if ( mapU == null )
			return edges.get(v).get(u).stream().min(Comparator.comparingDouble(Edge::weight)).orElse(null);

		Set<Edge> e = mapU.get(v);
		if (e == null)
			if (mapV.get(u) != null)
				return mapV.get(u).stream().sorted(Comparator.comparingDouble(Edge::weight)).findFirst().orElse(null);
			else
				return null;
		return e.stream().sorted(Comparator.comparingDouble(Edge::weight)).findFirst().orElse(null);
	}


	@Override
	public Set<Edge> findEdges(Vertex u, Vertex v) {
		Set<Edge> connectedEdges = new HashSet<>();
		if ( edges.get(u) != null && edges.get(u).get(v) != null )
			connectedEdges.addAll(edges.get(u).get(v));
		if ( edges.get(v) != null && edges.get(v).get(u) != null )
			connectedEdges.addAll(edges.get(v).get(u));
		return connectedEdges;
	}

	@Override
	public List<Edge> getEdges(Vertex origine, Vertex destination) {
		Set<Edge> edgeSet = findEdges(origine, destination);
		List<Edge> edgeList = new ArrayList<>();
		edgeList.addAll(edgeSet);
		return edgeList;
	}
}
