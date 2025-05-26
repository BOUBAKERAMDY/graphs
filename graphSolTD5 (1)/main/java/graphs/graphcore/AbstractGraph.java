package graphs.graphcore;

import java.util.*;

/**
 * A class for abstract graph. An abstract graph is the common 
 * implementation between directed graph (DiGraph) and undirected
 * graph (UnDiGraph)
 */
public abstract class AbstractGraph implements Graph {

	// the adjacency list i.e. the list of vertices adjacent to each vertex
	protected Map<Vertex,List<Vertex>> adjacencyList;
	// the number of edges
	protected int nbEdges;
	// the set of all tags
	private final Map<String,Vertex> tags;
	// the set of edges
	protected Map<Vertex,Map<Vertex,Set<Edge>> > edges;

	/**
	 * Builds an abstract graph with no vertices
	 */
	protected AbstractGraph() {
		adjacencyList = new HashMap<>();
		tags = new HashMap<>();
		edges = new HashMap<>();
		nbEdges = 0;
	}

	/////////////// public methods ///////////////

	/**
	 * Returns the number of vertices
	 */
	public int nbVertices() {
		return adjacencyList.size();
	}

	/**
	 * Returns the number of edges
	 */
	public int nbEdges() {
		return nbEdges;
	}

	/**
	 * Add a new vertex of tag 'tag' to the graph
	 * and returns that vertex
	 * If 'tag' is already used in that graph, the
	 * method raises a DuplicateTagException exception
	 */
	public Vertex addVertex(String tag) throws DuplicateTagException {
		if ( tags.containsKey(tag) )
			throw new DuplicateTagException(tag);
		InnerVertex v = new InnerVertex(this,tag);
		tags.put(tag, v);
		adjacencyList.put(v,new LinkedList<>());
		return v;
	}

	/**
	 * Return the vertex of tag 'tag' from the graph
	 * If no vertex has tag 'tag', the method
	 * returns null
	 */
	public Vertex getVertex(String tag) {
		if ( ! tags.containsKey(tag) )
			return null;
		return tags.get(tag);
	}



	/**
	 * Adds the new edge ('u','v') to the graph unless
	 * this edge is already present in the graph
	 */
	public Edge addEdge(Vertex u, Vertex v) {
		checkVertex(u);
		checkVertex(v);
		return addEdge(u,v,1.0);
	}

	/**
	 * get the edges  between two vertices
	 * Must be implemented in the subclasses
	 */
	public List<Edge> getEdges(Vertex origine, Vertex destination) {
		List<Edge> edgeList = new ArrayList<>();
		for (Edge edge : incidents(origine)) {
			if (edge.destination().equals(destination)) {
				edgeList.add(edge);
			}
		}
		return edgeList;
	}
	/**
	 * Removes edge 'e' from the graph unless
	 * this edge is not present in the graph
	 */
	public void removeEdge(Edge e) {
		removeEdge(e.origin(), e.destination());
	}

	/**
	 * Returns an iterable object over the vertices
	 * of the graph. The vertices come in random
	 * order
	 */
	public Iterable<Vertex> vertices() {
		return adjacencyList.keySet();
	}

	/**
	 * Returns an iterable object over the edges
	 * of the graph. The edges come in random
	 * order
	 */
	public Collection<Edge> edges() {
		return edges.values().stream().flatMap(m -> m.values().stream().flatMap(Set::stream)).toList();
	}



	/**
	 * Returns an iterable object over the adjacent
	 * vertices of vertex 'u' in the graph. The adjacents
	 * come in random order
	 */
	public Iterable<Vertex> adjacents(Vertex u) {
		checkVertex(u);
		return adjacencyList.get(u);
	}

	/**
	 * Returns true if 'v' is adjacent to 'u' in
	 * the graph, false otherwise
	 */
	public boolean adjacents(Vertex u, Vertex v) {
		checkVertex(u);
		checkVertex(v);
		for ( Vertex ve : adjacencyList.get(u) )
			if ( ve == v )
				return true;
		return false;
	}

	/**
	 * Returns an iterable object over the incident
	 * edges of vertex 'u' in the graph. The incident
	 * edges come in random order. For all incident
	 * edge e, e.origin() = u
	 */
	public Collection<Edge> incidents(Vertex u) {
		checkVertex(u);
		List<Vertex> neighbors = adjacencyList.get(u);
		Set<Edge> connectedEdges = new HashSet<>();
		if ( neighbors == null )
			return connectedEdges;
		for ( Vertex v : neighbors ) {
			Set<Edge> edgesUtoV = findEdges(u, v);
			connectedEdges.addAll(edgesUtoV);
		}
		return connectedEdges;
	}

	@Override
	public String toString() {
		StringBuilder s = new StringBuilder();
		s.append("number of verticies: ").append(nbVertices());
		s.append("\nnumber of edges: ").append(nbEdges);
		s.append("\nvertices:");
		for ( Vertex u : vertices() )
			s.append(" ").append(u);
		s.append("\nedges :");
		for ( Edge e : edges() )
			s.append(" ").append(e);
		return s.toString();
	}

	/////////////// public abstract methods ///////////////




	/////////////// protected abstract methods ///////////////

	/**
	 * finds the edge between vertices u and v
	 * @param u the origin of the edge
	 * @param v the destination of the edge
	 * @return the edge between u and v if it exists or null
	 */
	public abstract Edge findEdge(Vertex u, Vertex v);
	public  Set<Edge> findEdges(Vertex u, Vertex v){
		Set<Edge> connectedEdges = new HashSet<>();
		if ( edges.get(u) != null && edges.get(u).get(v) != null )
			connectedEdges.addAll(edges.get(u).get(v));
		return connectedEdges;
	}

	/////////////// protected methods ///////////////

	/**
	 * Update the adjency list of the graph by adding
	 * @param u the origin of the edge
	 * @param v the destination of the edge
	 * @param weight the weight of the edge
	 * @return true if the edge has been added, false otherwise
	 */
	protected boolean add(Vertex u, Vertex v, double weight) {
		//the edge has already been added
		if ( adjacencyList.get(u).contains(v) )
			return false; // an edge  already exists between u and v
		adjacencyList.get(u).add(v);
		return true;
	}

	protected boolean remove(Vertex u, Vertex v) {
		if ( adjacencyList.get(u).contains(v) ) {
			adjacencyList.get(u).remove(v);
			return true;
		}
		return false;
	}

	protected void checkVertex(Vertex v) {
		//We choose to remove this check because it is not necessary in our case
/*		if ( ((InnerVertex) v).fromGraph != this )
			throw new BadVertexException(v.getTag());

 */
	}

	protected Edge storeEdge(Vertex u , Vertex v, double weight) {
		edges.computeIfAbsent(u, k -> new HashMap<>());
		if ( edges.get(u).get(v) == null )
			edges.get(u).put(v,new HashSet<>());
		Set<Edge> set = edges.get(u).get(v);
		Edge e = new InnerEdge(u,v,weight);
		set.add(e);
		return e;
	}

	/////////////// private class and methods ///////////////

	// A class for vertices
	private class InnerVertex implements Vertex {
		AbstractGraph fromGraph; // back link to the host graph
		String tag; // the tag of the vertex
		double weight = 1; // the weight of the vertex (mainly for the Dijkstra algorithm) by default 1 to compute the number of edges in a path when the weight of the edges is not specified

		// Builds a vertex of tag 'tag'  in the graph 'fromGraph'
		InnerVertex(AbstractGraph fromGraph, String tag) {
			this.tag = tag;
			this.fromGraph = fromGraph;
		}

		/**
		 * Returns the tag of the vertex
		 */
		public String getTag() {
			return tag;
		}

		/**
		 * Returns the weight of the vertex
		 */
		public double getWeight() {
			return weight;
		}

		/**
		 * Sets the weight of the vertex to 'weight'
		 */
		public void setWeight(double weight) {
			this.weight = weight;
		}

		/**
		 * Vertices can have a weight and be comparable
		 * on that weight (mainly for the Dijkstra algorithm)
		 */
		public int compareTo(Vertex v) {
			InnerVertex vv = (InnerVertex) v;
			return Double.compare(weight, vv.weight);
		}

		@Override
		public String toString() {
			return tag;
		}
	}



	// a class for edges
	private class InnerEdge implements Edge {

		//We need to distinguish the edges even if they have the same origin and destination
		static int numberOfEdge = 0;

		int id = numberOfEdge++;
		Vertex x; // the origin of the edge
		Vertex y; // the destination of the edge
		double weight;
		//T edgeObject; // the object associated with the edge
		/**
		 * builds the edge (x,y,weight)
		 */
		InnerEdge(Vertex x, Vertex y, double weight) {
			this.x = x;
			this.y = y;
			this.weight = weight;
		}

		/**
		 * Returns the origin of the edge
		 */
		public Vertex origin() {
			return x;
		}


		/**
		 * Returns the destination of the edge
		 */
		public Vertex destination() {
			return y;
		}

		/**
		 * Returns the weight of the edge
		 */
		public double weight() {
			return weight;
		}

		@Override
		public void setWeight(double weight) {
			this.weight = weight;
		}

		/**
		 * edges can have a weight and be compared on that weight
		 * (mainly for Prim and Kruskal algorithms)
		 */
		public int compareTo(Edge e) {
			InnerEdge ee = (InnerEdge) e;
			return Double.compare(weight, ee.weight);
		}

		@Override
		public String toString() {
			return "{" + x + ", " + y + ": " + weight +")";
		}

		@Override
		public boolean equals(Object o) {
			if (this == o) return true;
			if (o == null || getClass() != o.getClass()) return false;
			InnerEdge innerEdge = (InnerEdge) o;
			return id == innerEdge.id && Double.compare(weight, innerEdge.weight) == 0 && Objects.equals(x, innerEdge.x) && Objects.equals(y, innerEdge.y);
		}

		@Override
		public int hashCode() {
			return Objects.hash(id, x, y, weight);
		}
	}



}
