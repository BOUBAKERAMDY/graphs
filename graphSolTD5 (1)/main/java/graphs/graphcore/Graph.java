package graphs.graphcore;

import java.util.Collection;
import java.util.List;

/**
 * An interface for graphs.
 * Notice that some methods have a different meaning depending on the
 * kind of graph they are applied to (directed or undirected graph).
 *
 * Notice: for all methods taking Vertex as parameter, calling the method
 * with a vertex which doesn't belong to the graph will raise a
 * BadVertexException exception
 */
public interface Graph {


	/**
	 * Return the number of vertices
	 */
	 int nbVertices();
	
	/**
	 * Return the number of edges
	 */
	 int nbEdges();
	
	/**
	 * Add a new vertex of tag 'tag' to the graph
	 * and returns that vertex
	 * If 'tag' is already used in that graph, the
	 * method raises a DuplicateTagException exception
	 */
	 Vertex addVertex(String tag) throws DuplicateTagException;
	
	/**
	 * Return the vertex of tag 'tag' from the graph
	 * If no vertex has tag 'tag', the method
	 * returns null
	 */
	 Vertex getVertex(String tag);
	

	/**
	 * Adds the new edge ('u','v') to the graph
	 */
	 Edge addEdge(Vertex u, Vertex v);
	
	/**
	 * Adds the new edge ('u','v') with weight 'weight'
	 * to the graph unless this edge is already present
	 * in the graph
	 * According to the kind of graph, this method creates a new edge anyway
	 * or updates the weight of the existing edge
	 * todo:  add more information about the edge to make it easier to extend

	 * @return the edge
	 */
	 Edge addEdge(Vertex u, Vertex v, double weight);

	/**
	 * Returns the edges ('u','v') existing in the graph
	 * @param u : the origin of the edges
	 * @param v : the destination of the edges
	 * @return the list of edges ('u','v') if they exist in the graph
	 */
	 List<Edge> getEdges(Vertex u, Vertex v);

	 /**
	 * Removes one edge ('u','v') from the graph unless
	 * there is no edge ('u','v') in the graph
	 *
	 */
	 void removeEdge(Vertex u, Vertex v);
	
	/**
	 * Removes edge 'e' from the graph unless
	 * this edge is not present in the graph
	 */
	 void removeEdge(Edge e);
	
	/**
	 * Returns an iterable object over the vertices
	 * of the graph. The vertices come in random
	 * order
	 */
	 Iterable<Vertex> vertices();
	
	/**
	 * Returns an iterable object over the edges
	 * of the graph. The edges come in random
	 * order
	 */	
	 Collection<Edge> edges();
	
	/**
	 * Returns an iterable object over the adjacent
	 * vertices of vertex 'u' in the graph. The adjacents
	 * come in random order
	 * Notice: for DiGraph, adjacents(u) = successors(u)
	 * Notice: for UnDiGraph, adjacents(u) = neighbors(u)
	 */
	 Iterable<Vertex> adjacents(Vertex u);
	
	/**
	 * Returns true if 'v' is adjacent to 'u' in
	 * the graph, false otherwise
	 *
	 */
	 boolean adjacents(Vertex u, Vertex v);
	
	/**
	 * Returns an iterable object over the incident
	 * edges of vertex 'u' in the graph. The incident
	 * edges come in random order.
	 * For all incident edge e, e.origin() = u
	 *
	 */
	 Collection<Edge> incidents(Vertex u);
	
	/**
	 * Returns the total degree of vertex 'u'
	 * Notice: for DiGraph, degree(v) = inDegree(v) + outDegree(v)
	 */
	 int degree(Vertex u);
	
	/**
	 * Returns the in-degree of vertex 'u'
	 * Notice: for UnDiGraph, inDegree(v) = degree(v)
	 */	
	 int inDegree(Vertex u);
	
	/**
	 * Returns the out-degree of vertex 'u'
	 * Notice: for UnDiGraph, outDegree(v) = degree(v)
	 */		
	 int outDegree(Vertex u);
	
}
