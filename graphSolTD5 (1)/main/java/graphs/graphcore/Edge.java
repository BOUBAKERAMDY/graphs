package graphs.graphcore;

/**
 * An interface for the edges of a graph.
 * Perspective : add info on the edge, but we should check if it increases the size of the graph too much
 *
 */
public interface Edge {
		
	/**
	 * Returns the origin of the edge
	 */
	 Vertex origin();
	
	/**
	 * Returns the destination of the edge
	 */
	 Vertex destination();
	
	/**
	 * Returns the weight of the edge
	 */
	 double weight();

	 /**
	  * Sets the weight of the edge
	  */
	 void setWeight(double weight);

}
