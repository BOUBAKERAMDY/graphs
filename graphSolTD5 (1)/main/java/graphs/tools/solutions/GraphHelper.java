package graphs.tools.solutions;


import graphs.graphcore.*;
import graphs.tools.GraphReader;

import java.util.*;

/**
 * This is a convenience class to support some graph algorithms
 * like cycle detection, path finding, root finding, etc.
 * Take care several graph helpers can be used at the same time
 */
public class GraphHelper {

	private Graph aGraph;


	/*************************************************
	 *
	 * Cycle detection for undirected graphs
	 *
	 **************************************************** */


	private Set<Vertex> visited;

	public GraphHelper() {
		visited = new HashSet<>();
	}

	/**
	 * Returns true if the graph is cyclic
	 * false otherwise
	 */
	public boolean hasCycle(UnDiGraph graph) {
		visited = new HashSet<>();
		aGraph = graph;
		for (Vertex u : graph.vertices())
			if (!visited.contains(u))
				if (hasCycle(u, null))
					return true;
		return false;
	}


	/**
	 * Returns true if a cycle was found by traversing
	 * the graph, coming from vertex 'from' and going by vertex u
	 * Precondition: vertex 'from' is visited and vertex 'u' is
	 * not visited
	 */
	private boolean hasCycle(Vertex u, Vertex from) {
		visited.add(u);
		//For each adjacent vertex a of u
		for (Vertex a : aGraph.adjacents(u)) {
			if (!visited.contains(a)) {
				if (hasCycle(a, u))
					return true;
				//we visited a and no cycle was found
			} else //we already visited a
				if (a != from) {// a was visited and it is not the vertex from which we came
					System.out.println("Cycle found from " + u + " to " + from);
					return true; //there is a cycle
				}
		}
		//System.out.println("No cycle found from " + u + " to " + from);
		return false;
	}

	/***********************************************
	 *
	 * Cycle detection for directed graphs
	 *
	 *
	 ************************************ */

	private enum Status {UN_DISCOVERED, IN_PROGRESS, COMPLETED} // status of vertex

	private Map<Vertex, Status> vertexStatus; // to set the status of each vertex

	/**
	 * Returns true if the graph 'diGraph' is cyclic
	 * false otherwise
	 */
	public boolean hasCycle(DiGraph diGraph) {
		vertexStatus = new HashMap<>();
		aGraph = diGraph;

		for (Vertex u : diGraph.vertices())
			if (status(u) == Status.UN_DISCOVERED)
				if (hasCycle(u))
					return true;
		return false;
	}

	/**
	 * Returns true if a cycle was found by traversing
	 * the graph from vertex u
	 */
	public boolean hasCycleFromTo(DiGraph diGraph, Vertex from) {
		vertexStatus = new HashMap<>();
		aGraph = diGraph;
		return hasCycleFromTo(from);
	}

	private boolean hasCycleFromTo(Vertex from) {
		visited = new HashSet<>();
		visited.add(from);
		return hasCycle(from);
	}

	/**
	 * Returns true if a cycle was found by traversing
	 * the graph from vertex u
	 * Precondition: vertex 'u' is 'UnDiscovered'
	 */
	private boolean hasCycle(Vertex u) {
		// we set the status of u to in progress to avoid visiting it again in the future
		setStatus(u, Status.IN_PROGRESS);
		for (Vertex a : aGraph.adjacents(u)) {
			if (status(a) == Status.UN_DISCOVERED) {
				if (hasCycle(a))
					return true;
			} else
				// a was visited, so if it is in progress, it means that we have found a cycle
				if (status(a) == Status.IN_PROGRESS)
					return true;
		}
		// all adjacent vertices of u were visited and no cycle was found
		// so we can set the status of u to completed to avoid visiting it again
		setStatus(u, Status.COMPLETED);
		return false;
	}


	/**
	 * Returns the status of vertex 'u'
	 */
	private Status status(Vertex u) {
		Status s = vertexStatus.get(u);
		if (s == null)
			return Status.UN_DISCOVERED;
		return s;
	}

	/**
	 * Sets the status of vertex 'u' to 's'
	 */
	private void setStatus(Vertex u, Status s) {
		vertexStatus.put(u, s);
	}

	/* ------------------- Path finding ------------------- */

	/**
	 * Returns a path as from vertex 'from' to vertex 'to' in the graph
	 * as a list of vertices if such a path exists, the empty list
	 * otherwise
	 */
	public List<Vertex> findPath(Graph graph, Vertex from, Vertex to) {
		aGraph = graph;
		visited = new HashSet<>();
		List<Vertex> path = new LinkedList<>();
		findPath(from, to, path);
		return path;
	}



	/**
	 * Returns the distance of the path 'path' in the graph 'graph'
	 *
	 * @param path
	 * @param graph
	 * @return the distance of the path 'path' in the graph 'graph'
	 * If the path is not valid, the method returns Double.POSITIVE_INFINITY
	 */
	public double computeDistanceOfPath(List<Vertex> path, Graph graph) {
		double distance = 0;
		for (int i = 0; i < path.size() - 1; i++) {
			List<Edge> edges = graph.getEdges(path.get(i), path.get(i + 1));
			if (edges.isEmpty()) {
				return Double.POSITIVE_INFINITY; // no edge between path.get(i) and path.get(i+1)
			}
			distance += edges.get(0).weight();
		}
		return distance;
	}

	/**
	 * If there is a path from vertex 'from' to vertex 'to' in the graph, the
	 * vertices of this path are stored in the list 'path' and the method
	 * returns true. Else the list 'path' remains unchanged and the method
	 * returns false
	 */
	private boolean findPath(Vertex from, Vertex to, List<Vertex> path) {
		visited.add(from);
		if (from == to) { // we found the path
			path.add(from);
			return true;
		}
		for (Vertex a : aGraph.adjacents(from)) {
			if (!visited.contains(a)) {
				if (findPath(a, to, path)) {
					// There is a path from a to 'to' and the path is stored in path
					// So we add u to the path in front of a and return true
					path.add(0, from);
					//as soon as we find a path, we return it and stop the search returning true
					return true;
				}
			}
		}
		return false;
	}


	/* ------------------- Root finding ------------------- */

	/**** Rechercher les racines d'un graphe orienté ***/
	/**
	 * Returns the roots of the graph 'diGraph' if
	 * such roots exist, null otherwise
	 * A root of a directed graph is a vertex r such that
	 * there is a path from r to every other vertex u in the graph
	 * <p>
	 * Une racine dans un graphe orienté est un sommet r tel qu’il existe un chemin de r à
	 * u pour tout sommet u different de r.
	 */
	public List<Vertex> findRoots(DiGraph diGraph) {
		aGraph = diGraph;
		visited = new HashSet<>();
		List<Vertex> roots = new LinkedList<>();

		for (Vertex u : aGraph.vertices()) {
			// we visit all the vertices reachable from u
			// if all the vertices of the graph are reachable from u
			// then u is a root
			visited = new HashSet<>();
			visited.add(u);
			if (visit(u) == aGraph.nbVertices())
				roots.add(u);
		}
		return roots;
	}


	/**
	 * Returns a root of the graph 'diGraph' if it exists
	 * such as the graph is a rooted graph i.e. a graph with a root that is
	 * a vertex r such that there is a path from r to every other vertex u in the graph
	 * and there is no path from any other vertex to r
	 *
	 * @param diGraph
	 * @return the "root" of the graph 'diGraph' if it exists
	 */


	public Optional<Vertex> findRoot(DiGraph diGraph) {
		aGraph = diGraph;
		visited = new HashSet<>();

		Vertex candidate = candidate();
		if (candidate == null)
			return Optional.empty(); // if the graph has a cycle, we cannot find a root
		// we visit all the vertices reachable from candidate
		visited = new HashSet<>();
		if (visit(candidate) != aGraph.nbVertices())
			return Optional.empty(); // if the number of vertices reachable from candidate is not equal to the number of vertices in the graph, candidate is not a root
		return Optional.ofNullable(candidate);
	}

	/**
	 * Returns a root candidate
	 * A candidate is a vertex that is not reachable from any other vertex and that can join all the other vertices
	 * Principles
	 * 1. We visit all the vertices reachable from any vertex u
	 * 2. If a new vertex u was not yet visited, it means that it is not reachable from a previous vertex
	 * 3. So we set candidate to u
	 * 4. we do it for all the vertices of the graph that were not yet visited
	 * The candidate is the last vertex that was not reachable from any other vertex
	 * 5. If the candidate is involved in a cycle, i.e., it's reachable from itself, it is not a root
	 *
	 * */
	private Vertex candidate() {
		visited = new HashSet<>();
		Vertex candidate = null;
		for (Vertex u : aGraph.vertices()) {
			if (!visited.contains(u)) {
				// u is not reachable from any other vertex until now
				// so the previous vertex  candidate (candidate) was not a root as u was not reachable from it
				candidate = u; // so we set last to u
				visit(u); //we visit all the vertices reachable from u and that were not reachable from any other vertex
			}
		}
		if (isInvolvedInACycle(aGraph, candidate)) {
			return null;
			// if the candidate is involved in a cycle, it is not a root
		}
			//}
			return candidate;
	}

	public boolean isInvolvedInACycle(Graph g, Vertex candidate) {
		aGraph = g;
		// we visit all the vertices reachable from candidate
		visited = new HashSet<>();
		for (Vertex a : aGraph.adjacents(candidate)) {
			if (!visited.contains(a))
				visit(a);
		}
		return (visited.contains(candidate)); // if the candidate is involved in a cycle, it is not a root
	}

		/**
		 * Visits all the reachable vertices from
		 * vertex 'u' and returns the number of
		 * those vertices
		 * Take care that the method depends on the visited set
		 */
		private int visit (Vertex u){
			visited.add(u);
			int n = 1;
			for (Vertex a : aGraph.adjacents(u))
				if (!visited.contains(a))
					n += visit(a);
			return n;
		}


		///////////////

		public static void main (String[] s){
			UnDiGraph U1 = GraphReader.unDiGraph("A B C D C E D E E F G H G K H I H J H K I J");
			GraphHelper helper = new GraphHelper();
			System.out.println(helper.hasCycle(U1));

			DiGraph diGraph = GraphReader.diGraph("A B C D C E D E E F G H G K H I H J H K I J");
			helper = new GraphHelper();
			System.out.println(helper.hasCycle(diGraph));
		}



}

