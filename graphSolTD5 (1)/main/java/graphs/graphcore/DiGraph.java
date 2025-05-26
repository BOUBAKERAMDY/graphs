package graphs.graphcore;

import java.util.HashMap;
import java.util.Map;

/**
 * A class for directed graph
 */
public class DiGraph extends AbstractGraph {

	// inDegree[u] is the in-degree of u i.e. the number of edges that go to u
	private Map<Vertex,Integer> inDegree;
	
	/**
	 * builds a directed graph with no vertex
	 */
	public DiGraph() {
		super();
		inDegree = new HashMap<>();
	}
	
	@Override
	public Vertex addVertex(String tag) throws DuplicateTagException {
		Vertex v = super.addVertex(tag);
		inDegree.put(v,0);
		return v;
	}

	/**
	 * Update the adjency list of the graph by adding
	 * @param u the origin of the edge
	 * @param v the destination of the edge
	 * @param weight the weight of the edge
	 * @return true if the edge has been added, false otherwise
	 */
	@Override
	protected boolean add(Vertex u, Vertex v, double weight) {
		//the edge has already been added
		if (!super.add(u,v,weight))
			adjacencyList.get(u).add(v); //we want to deal with the case of parallel edges
		return true;
	}

		
	@Override
	public Edge addEdge(Vertex u, Vertex v, double weight) {
		checkVertex(u);
		checkVertex(v);		
		add(u,v,weight);
		Edge e  = storeEdge(u,v,weight);
		nbEdges++;
		inDegree.put(v,inDegree.get(v)+1);
        return e;
    }

	@Override
	public void removeEdge(Vertex u, Vertex v) {
		checkVertex(u);
		checkVertex(v);		
		if ( remove(u,v) ) {
			nbEdges--;
			inDegree.put(v,inDegree.get(v)-1);
		}
	}
	
	@Override
	public int degree(Vertex u) {
		checkVertex(u);
		return outDegree(u) + inDegree(u);
	}

	/**
	 * returns the in-degree of u
	 */	
	public int inDegree(Vertex u) {
		checkVertex(u);
		return inDegree.get(u);
	}
	
	/**
	 * returns the out-degree of u
	 */
	public int outDegree(Vertex u) {
		checkVertex(u);
		return adjacencyList.get(u).size();
	}
	
	@Override
	public String toString() {
		return "Directed Graph\n" + super.toString();
	}

	@Override
	public Edge findEdge(Vertex u, Vertex v) {
		if ( edges.get(u) == null || edges.get(u).get(v) == null )
			return null;
		return edges.get(u).get(v).stream().sorted().findFirst().orElse(null);
	}



}
