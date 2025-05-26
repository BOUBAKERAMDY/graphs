package graphs.tools;


import graphs.graphcore.Edge;
import graphs.graphcore.Graph;
import graphs.graphcore.Vertex;

import java.util.*;

public class GraphTraversal {

    private GraphTraversal() {
    }
    /**
     * Returns the list of paths of 'graph' that go from 'start' to 'end'
     *
     * @param graph
     * @param origin
     * @param destination
     * @return the list of paths
     */
    public static List<Path> findPaths(Graph graph, Vertex origin, Vertex destination) {
        List<Edge> alreadyVisited = new ArrayList<>();
        return buildPathList(graph, origin, destination, alreadyVisited);
    }

    /**
     * Returns the list of paths of 'graph' that go from 'start' to 'end'
     * @param graph the graph
     * @param origin the origin vertex
     * @param destination   the destination vertex
     * @param alreadyVisited the list of already visited edges
     * @return the list of paths
     */
    private static List<Path> buildPathList(Graph graph, Vertex origin, Vertex destination, List<Edge> alreadyVisited) {
        List<Path> paths = new ArrayList<>();

        ArrayList<Edge> localAlreadVisited = new ArrayList<>(alreadyVisited);
        Collection<Edge> fromTo = graph.incidents(origin);
        for (Edge edge : fromTo) {
            if (localAlreadVisited.contains(edge))
                continue;
            else
                localAlreadVisited.add(edge);

            Path p = new Path(origin);
            if ((links(edge, destination)) && (destination != origin)) {
                p.add(edge);
                paths.add(p);
            } else {
                Vertex from = getOtherVertex(origin, edge);
                List<Edge> localAlreadVisited2 = new ArrayList<>(localAlreadVisited);
                localAlreadVisited2.addAll(fromTo);
                List<Path> subPaths = buildPathList(graph, from, destination, localAlreadVisited2);
                for (Path sb : subPaths) {
                    sb.add(0, edge);
                    sb.origin = origin;
                    paths.add(sb);
                }
            }
        }
        return paths;
    }

    private static Vertex getOtherVertex(Vertex origin, Edge edge) {
        Vertex destination1 = edge.destination() == origin ? edge.origin() : edge.destination();
        return destination1;
    }

    //todo en faire une exigence de graphe et mettre cet implem dans undirectedGraph
    private static boolean links(Edge e, Vertex v) {
        return e.origin().equals(v) || e.destination().equals(v);
    }

    //TODO : Aie ne marche que pour les graphes non orientés....
    //tester dur des graphes orientés
   /* private static List<Path> findSubPaths(Graph graph, Vertex start, Vertex destination, List<Edge> alreadyVisited) {
        List<Path> paths = new ArrayList<>();
        for (Edge edge : graph.incidents(start)) {



            /*if (alreadyVisited.contains(edge))
                continue;
            else {
                if (links(edge, destination)) {
                    Path p = new Path(start);
                    p.add(edge);
                    paths.add(p);
                } else {
                    Vertex from = getOtherVertex(origin, edge);
                    if (!alreadyVisited.contains(edge)) {
                        alreadyVisited.add(edge);
                        List<Path> subPaths = findSubPaths(graph, getOtherVertex(start,edge), destination, alreadyVisited);
                        for (Path sb : subPaths) {
                            Path p = new Path(start);
                            p.add(0, edge);
                            paths.add(sb);
                        }
                    }
                }
            }
        }
        return paths;
    }

    */
    /* -------------------- DFS ---------------------- */

    /**
     * Returns the list of vertices of 'graph' in DFS order
     *
     * @param graph
     * @param start
     * @return
     */
    public static List<Vertex> dfs(Graph graph, Vertex start) {
        Set<Vertex> visited = new HashSet<>();
        List<Vertex> sorted = new ArrayList<>();
        visitDFS(graph, start, visited, sorted);
        return sorted;
    }

    /**
     * Visit the graph 'graph' using DFS from vertex 'u' and add all the visited
     * vertices in 'sorted' such that they appear in topological order
     */
    public static void visitDFS(Graph graph, Vertex u, Set<Vertex> visited, List<Vertex> sorted) {
        visited.add(u);
        sorted.add(u);
        for (Vertex a : graph.adjacents(u))
            if (!visited.contains(a))
                visitDFS(graph, a, visited, sorted);
        //All the adjacent vertices of u have been visited
    }

    /* -------------------- BFS ---------------------- */

    /**
     * Returns the list of vertices of 'graph' in BFS order
     *
     * @param graph
     * @param start
     * @return
     */
    public static List<Vertex> bfs(Graph graph, Vertex start) {
        List<Vertex> sorted = new ArrayList<>();
        List<Vertex> toVisit = new ArrayList<>();
        toVisit.add(start);
        visitBFS(graph, toVisit, sorted);
        return sorted;
    }

    /**
     * Visit the graph 'graph' using BFS from vertex 'u' and add all the visited
     * vertices in 'sorted' such that they appear in topological order
     */
    public static void visitBFS(Graph graph, List<Vertex> toVisit, List<Vertex> sorted) {
        if (toVisit.isEmpty())
            return;
        Vertex u = toVisit.remove(0);
        if (!sorted.contains(u)) {
            sorted.add(u);
            for (Vertex a : graph.adjacents(u)) {
                if (!sorted.contains(a))
                    toVisit.add(a);
            }
        }
        visitBFS(graph, toVisit, sorted);
    }

    /* -------------------- Dijkstra ---------------------- */

    /**
     * returns the shortest path from 'start' to 'end' in 'graph' using Dijkstra adapted with "Shortest Path Faster Algorithm" (SPFA).
     * @param graph
     * @param start
     * @param end
     * @return
     */
    public static List<Vertex> dijkstra(Graph graph, Vertex start, Vertex end) {
        //distances de chaque sommet correspondant au chemin le plus court déjà vu depuis le sommet de départ
        Map<Vertex, Double> distances = new HashMap<>();
        //prédécesseurs de chaque sommet correspondant au chemin le plus court déjà vu depuis le sommet de départ
        Map<Vertex, Vertex> predecessors = new HashMap<>();
        //Cette version utilise une PriorityQueue pour la file d'attente, ce qui permet de sélectionner le sommet suivant avec la plus petite distance en temps constant.
        // Cela peut améliorer la performance de l'algorithme dans certains cas, notamment lorsque le graphe est dense ou lorsque les distances varient considérablement entre les sommets.
        PriorityQueue<Vertex> queue = new PriorityQueue<>(Comparator.comparingDouble(distances::get));

        // Initialisation des distances à l'infini sauf pour le sommet de départ
        for (Vertex vertex : graph.vertices()) {
            distances.put(vertex, Double.POSITIVE_INFINITY);
        }
        distances.put(start, 0.0);

        // Ajout du sommet de départ à la file d'attente
        queue.offer(start);

        while (!queue.isEmpty()) {
            Vertex current = queue.poll();
            // Si le sommet est notre cible, on passe au suivant, il n'est pas nécessaire de continuer.
            if (current == end) {
                continue;
            }
            // Parcours des voisins du sommet courant
            for (Edge edge : graph.incidents(current)) {
                Vertex neighbor = getNeighbor(current, edge);
                double weight = edge.weight();

                double newDistance = distances.get(current) + weight;
                // Mise à jour de la distance au voisin si une distance plus courte est trouvée
                if (newDistance < distances.get(neighbor)) {
                    //Enregistrement de la nouvelle distance et du prédécesseur
                    distances.put(neighbor, newDistance);
                    predecessors.put(neighbor, current);
                    // retrait du voisin de la file d'attente s'il y était
                    queue.remove(neighbor);
                    // Ajout du voisin à la file d'attente et on force la mise à jour de la priorité
                    queue.offer(neighbor);

                }
            }
        }

        // Reconstruction du plus court chemin

        List<Vertex> shortestPath = new ArrayList<>();
        Vertex current = end;
        while (current != null) {
            shortestPath.add(0, current);
            current = predecessors.get(current);
        }

        return shortestPath;
    }

    private static Vertex getNeighbor(Vertex current, Edge edge) {
        Vertex neighbor = edge.destination();
        if (neighbor == current) {
            neighbor = edge.origin();
        }
        return neighbor;
    }


    public static List<Vertex> dijkstraWithoutOptimization(Graph graph, Vertex start, Vertex end) {
        //On précise la taille pour éviter les redimensionnements sur les HashMap dans le cas d'un grand graphe
        int size = graph.nbVertices();
        //les prédécesseurs de chaque sommet correspondant au chemin le plus court déjà vu depuis le sommet de départ
        Map<Vertex, Vertex> predecessors = HashMap.newHashMap(size);
        //les distances de chaque sommet correspondant au chemin le plus court déjà vu depuis le sommet de départ
        Map<Vertex, Double> distances = HashMap.newHashMap(size);
        List<Vertex> visited = new ArrayList<>();
        //les sommets à visiter
        List<Vertex> toVisit = new ArrayList<>();
        toVisit.add(start);
        //On initialise la distance du sommet de départ à 0 (puisqu'il est à 0 de lui-même)
        distances.put(start, 0.0);
        visitDijkstra(graph, toVisit, visited, predecessors, distances, end);
        List<Vertex> nodes;
        //Utilisation de la recursivité
        //nodes = new ArrayList<>();
        //nodes.add(end);
        //buildPathRec(predecessors, nodes, start, end);
        //On construit le chemin à partir des prédécesseurs stockés dans la HashMap
        nodes = buildPath(predecessors, start, end);
        return nodes;
    }



    /**
     * Compute the distance between two vertices in a graph using Dijkstra's algorithm.
     * @param graph
     * @param start
     * @return
     */
    public static Map<Vertex, Vertex> dijkstra(Graph graph, Vertex start) {
        //distances de chaque sommet correspondant au chemin le plus court déjà vu depuis le sommet de départ
        Map<Vertex, Double> distances = HashMap.newHashMap(graph.nbVertices());
        //prédécesseurs de chaque sommet correspondant au chemin le plus court déjà vu depuis le sommet de départ
        Map<Vertex, Vertex> predecessors = HashMap.newHashMap(graph.nbVertices());
        PriorityQueue<Vertex> queue = new PriorityQueue<>(Comparator.comparingDouble(distances::get));
        Set<Vertex> visited = new HashSet<>();

        // Initialisation des distances avec une valeur infinie pour tous les sommets sauf le sommet de départ
        for (Vertex vertex : graph.vertices()) {
            distances.put(vertex, Double.POSITIVE_INFINITY);
        }
        distances.put(start, 0.0);
        queue.add(start);

        while (!queue.isEmpty()) {
            Vertex current = queue.poll();
            visited.add(current);

            for (Edge edge : graph.incidents(current)) {
                Vertex neighbor = getNeighbor(current, edge);
                if (visited.contains(neighbor)) {
                    continue; // On ignore les sommets déjà visités
                }
                double newDistance = distances.get(current) + edge.weight();
                if (newDistance < distances.get(neighbor)) {
                    distances.put(neighbor, newDistance);
                    predecessors.put(neighbor, current);
                    queue.remove(neighbor); // On met à jour la priorité du sommet dans la file de priorité
                    queue.add(neighbor);
                }
            }
        }
        return predecessors;
    }

    //  Attention la récursivité dans des grands graphes peut poser problème !! (StackOverflowError)
    // On modifie pour passer par une boucle while dans la méthode buildPath
    private static void buildPathRec(Map<Vertex, Vertex> predecessors, List<Vertex> sorted, Vertex start, Vertex end) {
        if (!end.equals(start)) {
            sorted.add(0, predecessors.get(end));
            //Attention la récursivité dans des grands graphes peut poser problème !! (StackOverflowError)
            buildPathRec(predecessors, sorted, start, predecessors.get(end));
        }
    }

    public static List<Vertex> buildPath(Map<Vertex, Vertex> predecessors, Vertex start, Vertex end) {
        List<Vertex> path = new ArrayList<>();
        Vertex current = end;

        while (current != null && !current.equals(start)) {
            path.add(0, current);
            current = predecessors.get(current);
        }

        //current == null => pas de chemin trouvé
        //else current == start => on a trouvé un chemin
        if (current != null) {
            path.add(0, start);
        }

        return path;
    }

    public static void visitDijkstraRec(Graph graph,
                                        List<Vertex> toVisit,
                                        List<Vertex> visited,
                                        Map<Vertex, Vertex> predecessors,
                                        Map<Vertex, Double> distances,
                                        Vertex end) {
        if (toVisit.isEmpty())
            return;
        Vertex u = toVisit.remove(0);
        if (!visited.contains(u)) {
            visited.add(u);
            if (u.equals(end))
                return;

            for (Vertex a : graph.adjacents(u)) {
                if (!visited.contains(a))
                    toVisit.add(a);
                if (distances.get(a) == null ||
                        distances.get(a) > distances.get(u) + graph.getEdges(u, a).getFirst().weight()) {
                    distances.put(a, distances.get(u) + graph.getEdges(u, a).getFirst().weight());
                    predecessors.put(a, u);
                }
            }
        }
        visitDijkstraRec(graph, toVisit, visited, predecessors, distances, end);
    }

    //Todo : Improve this method avoiding parameters passing
    public static void visitDijkstra(Graph graph,
                                     List<Vertex> toVisit,
                                     List<Vertex> visited,
                                     Map<Vertex, Vertex> predecessors,
                                     Map<Vertex, Double> distances,
                                     Vertex end) {
        while (!toVisit.isEmpty()) {
            Vertex u = toVisit.remove(0);
            if (!visited.contains(u)) {
                visited.add(u);
                if (u.equals(end))
                    return;
                for (Vertex a : graph.adjacents(u)) {
                    if (!visited.contains(a))
                        toVisit.add(a);
                    //On met à jour la distance et le prédécesseur si on trouve un chemin plus court ou si on n'a pas encore de chemin
                    if (distances.get(a) == null ||
                            distances.get(a) > distances.get(u) + graph.getEdges(u, a).getFirst().weight()) {
                        distances.put(a, distances.get(u) + graph.getEdges(u, a).getFirst().weight());
                        predecessors.put(a, u);
                    }
                }
            }
        }
    }

    //New version more efficient


}
