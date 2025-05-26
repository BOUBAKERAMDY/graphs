package graphs.graphcore;
/**
 * Exception raised when a tag is already used in the graph
 * and the user tries to add a new vertex with that tag
 *
 * According to the kind of graph, this exception may not be raised
 */
public class DuplicateTagException extends Exception {

	public DuplicateTagException(String tag) {
		super(tag);
	}
}
