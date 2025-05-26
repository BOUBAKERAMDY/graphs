package graphs.tools.solutions;

import java.util.*;
import graphs.graphcore.*;
import graphs.tools.GraphReader;

/**
 * A class to find connected components from an undirected graphs
 *
 */
public class ConnectedComponents {
	
	private  Map<Vertex,Integer> connectedComponentsMap; // the resulting map


	/*****
	 * Returns the number of the connected component of vertex v
	 * @param v
	 * @return the number of the connected component of vertex v
	 * If the component of v is not numbered, the method returns -1
	 */
	public int getComponentNumber(Vertex v) {
		Integer i  = connectedComponentsMap.get(v);
		if (i == null)
			return -1;
		return i;
	}
	/******
	 * Returns the set of vertices that are in the same connected component
	 * @param v
	 * @return the set of vertices that are in the same connected component as v
	 * If the component of v is not numbered, the method returns an empty set
	 */
	public Set<Vertex> getConnectedComponentsTo(Vertex v) {
		Integer i  = connectedComponentsMap.get(v);
		if (i == null)
			return new HashSet<>();
		Set<Vertex> result = new HashSet<>();

		//TODO : improve this code with an entrySet
		for (Vertex u : connectedComponentsMap.keySet()) {
			if (connectedComponentsMap.get(u).equals(i))
				result.add(u);
		}
		return result;
	}

	private final UnDiGraph unDiGraph; // the undirected graph

	public int getMaxConnectedComponentNumber() {
		return maxConnectedComponentNumber;
	}

	private int maxConnectedComponentNumber = 0;

	/**
	 * Builds a connected components finder for the undirected graph 'diGraph'
	 * @param graph the undirected graph
	 */
	public ConnectedComponents(UnDiGraph graph) {
		connectedComponentsMap = new HashMap<>();
		unDiGraph = graph;
	}

	/**
	 * Returns the map of the connected components of the graph
	 * If cc is the returned map, then, for all vertices u and v,
	 * cc.get(u) = cc.get(v) if and only if u and v are in the same
	 * connected component
	 */
	public Map<Vertex,Integer> find() {
		int number = 1;

		// for each vertex u,
		// if u is not numbered, we set the component number of u with number++
		// and all the vertices reachable from u
		for ( Vertex u : unDiGraph.vertices() ) {
			if ( notNumbered(u) )
				setComponentIterative(u,number++);
		}

		maxConnectedComponentNumber = number - 1;
		return connectedComponentsMap;
	}
	
	/**
	 * Set the component number to 'number' for 'u' and
	 * all the vertices reachable from u
	 */
	private void setComponent(Vertex u, int number) {
		setNumber(u, number);

		for ( Vertex a : unDiGraph.adjacents(u) )
			if ( notNumbered(a) )
				setComponent(a,number);
	}

	/**
	 * Set the component number to componentNumber for startVertex and
	 * all the vertices reachable from startVertex
	 * This method uses an iterative approach to avoid stack overflow for deep recursion
	 * @param startVertex the starting vertex
	 * @param componentNumber the component number to set
	 * principles of the algorithm:
	 * 1. we use a stack to store the vertices to be processed
	 * 2. we pop a vertex from the stack
	 * 3. if the vertex is not numbered, we set its number
	 * 4. we push all its adjacent vertices that are not numbered
	 * 5. we repeat until the stack is empty
	 * In this version, we use a set to store the visited vertices
	 *    but we could avoid it checking if the vertex is numbered
	 */
	private void setComponentIterativeWithVisited(Vertex startVertex, int componentNumber) {
		Set<Vertex> visited = new HashSet<>();
		Deque<Vertex> stack = new LinkedList<>();

		stack.push(startVertex);


		while (!stack.isEmpty()) {
			Vertex currentVertex = stack.pop();
			if (!visited.contains(currentVertex)) {
				setNumber(currentVertex, componentNumber);
				visited.add(currentVertex);
				for (Vertex adjacentVertex : unDiGraph.adjacents(currentVertex)) {
					if (!visited.contains(adjacentVertex)) {
						stack.push(adjacentVertex);
					}
				}
			}
		}
	}

	/**
	 * Set the component number to componentNumber for startVertex and
	 * all the vertices reachable from startVertex
	 * This method uses an iterative approach to avoid stack overflow for deep recursion
	 * @param startVertex the starting vertex
	 * @param componentNumber the component number to set
	 * principles of the algorithm:
	 * 1. we use a stack to store the vertices to be processed
	 * 2. we pop a vertex from the stack
	 * 3. if the vertex is not numbered, we set its number
	 * 4. we push all its adjacent vertices that are not numbered
	 * 5. we repeat until the stack is empty
	 */
	private void setComponentIterative(Vertex startVertex, int componentNumber) {
		Stack<Vertex> stack = new Stack<>();

		stack.push(startVertex);

		while (!stack.isEmpty()) {
			Vertex currentVertex = stack.pop();
			if (notNumbered(currentVertex)) {
				setNumber(currentVertex, componentNumber);
				for (Vertex adjacentVertex : unDiGraph.adjacents(currentVertex)) {
					if (notNumbered(adjacentVertex)) {
						stack.push(adjacentVertex);
					}
				}
			}
		}
	}
	
	private boolean notNumbered(Vertex u) {
		return !connectedComponentsMap.containsKey(u);
	}
	
	private void setNumber(Vertex u, int number) {
		connectedComponentsMap.put(u, number);
	}
	
	public static void main(String[] s) {


		UnDiGraph U1 = GraphReader.unDiGraph("A B C D C E D E E F G H G K H I H J H K I J");
		UnDiGraph U2 = GraphReader.unDiGraph("A D A E A J B C B F B G B I C F C G C H D E D F G H");

		ConnectedComponents ccFinder = new ConnectedComponents(U1);
		
		Map<Vertex,Integer> cc = ccFinder.find();
		System.out.println(cc);
		System.out.println("maxConnectedComponentNumber = " + ccFinder.maxConnectedComponentNumber);

		ccFinder = new ConnectedComponents(U2);
		cc = ccFinder.find();
		System.out.println(cc);
		System.out.println("maxConnectedComponentNumber = " + ccFinder.maxConnectedComponentNumber);
	}
}
