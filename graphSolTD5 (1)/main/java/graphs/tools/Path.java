package graphs.tools;

import graphs.graphcore.Edge;
import graphs.graphcore.Graph;
import graphs.graphcore.Vertex;

import java.util.ArrayList;
import java.util.List;

public class Path extends ArrayList<Edge> {
    Vertex origin;
    public Path(Vertex start) {
        super();
        origin = start;
    }

    public Path(List<Edge> edges, Vertex origin) {
        super(edges);
        this.origin = origin;
    }

    public Path(Graph graph, List<Vertex> vertices) {
        origin = vertices.get(0);
        for(int i = 0; i < vertices.size() - 1; i++) {
            Vertex originVertex = vertices.get(i);
            Vertex destinationVertex = vertices.get(i+1);
            List<Edge> edges = graph.getEdges(originVertex, destinationVertex);
            edges.sort((e1, e2) -> Double.compare(e1.weight(), e2.weight()));
            if (edges.size() == 0) {
                throw new IllegalArgumentException("No edge between " + originVertex + " and " + destinationVertex);
            }
            add(edges.get(0));
        }

    }

    public List<Vertex> vertices() {
        List<Vertex> vertices = new ArrayList<>();
        vertices.add(origin);
        Vertex previous = origin;
        for(Edge edge : this) {
            Vertex newVertex = edge.destination().equals(previous) ? edge.origin() : edge.destination();
            vertices.add(newVertex);
            previous = newVertex;
        }
        return vertices;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        List<Vertex> vertices = vertices();

        for(int i = 0; i < vertices.size() - 1; i++) {
            sb.append(vertices.get(i).toString());
            sb.append(" -> ");
        }
        if (vertices.size() > 0)
            sb.append(vertices.get(vertices.size()-1).toString());

        sb.append(" : "); sb.append(weight());
        return sb.toString();
    }


    public double weight() {
        int distance = 0;
        for(Edge edge : this) {
            distance += edge.weight();
        }
        return distance;
    }
}
