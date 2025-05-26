/*package graphs.roadApplication;

import graphs.graphcore.Edge;
import graphs.graphcore.Vertex;
import graphs.tools.solutions.ConnectedComponents;
import graphs.tools.GraphTraversal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import roadApplication.solutions.MapOfCities;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class MapOfCitiesTest {


    //todo: change the path to the file
    String path = "./src/main/resources/";
    CityParser cp;
    List<City> cities;
    MapOfCities mapOfCities;
    @BeforeEach
    void setUp() {
        CityParser cp = new CityParser(path + "fr.txt");
        cities = new ArrayList<>();
        cp.readAll(cities);
        mapOfCities = new MapOfCities();
        mapOfCities.buildGraph(cities, 12000);
    }

    @Test
    void testAll() {
        //UnDiGraph.LOGGER.setLevel(java.util.logging.Level.FINE);
        //CityParser cp = new CityParser(path + "fr.txt");
        //List<City> cities = new ArrayList<>();
        //cp.readAll(cities);

        List<City> afterRemoving = CityParser.removeDuplicates(cities);

        City nice = getCity(afterRemoving, "Nice");
        City cagnes = getCity(afterRemoving, "Cagnes-sur-Mer");
        City villeneuve = getCity(afterRemoving, "Villeneuve-Loubet");
        City valbonne = getCity(afterRemoving, "Valbonne");
        City biot = getCity(afterRemoving, "Biot");
        City antibes = getCity(afterRemoving, "Antibes");
        City paris = getCity(afterRemoving, "Paris");
        City sainte_foy = getCity(afterRemoving, "Saint-Foy");
        City laMadeleine = getCity(afterRemoving, "La Madeleine");
        City Laroque = getCity(afterRemoving, "Laroque");
        City saintJean = getCity(afterRemoving, "Saint-Jean");
        City marseille = getCity(afterRemoving, "Marseille");
        City saint_raphael = getCity(afterRemoving, "Saint-Raphael");
        City toulon = getCity(afterRemoving, "Toulon");
        //   System.out.println("Saint-Philippe :" + saint_philippe);
        System.out.println("------------- VILLES --------------");
        System.out.println("Nice:" + nice);
        assertEquals(43.5, nice.getLatitude(),0.5);
        assertEquals(7.25, nice.getLongitude(),0.5);
        System.out.println("Cagnes:" + cagnes);
        System.out.println("Villeneuve:" + villeneuve);
        System.out.println("Biot:" + biot);
        System.out.println("Valbonne:" + valbonne);
        System.out.println("Antibes:" + antibes);
        System.out.println("Paris:" + paris);
        System.out.println("La Madeleine :" + sainte_foy);
        System.out.println("Laroque :" + Laroque);
        System.out.println("Saint-Jean (Roquette sur Siagnes!:" + saintJean);
        System.out.println("Saint-Foy :" + sainte_foy);
        System.out.println("Marseille :" + marseille);
        assertEquals(43, marseille.getLatitude(),0.5);
        assertEquals(5, marseille.getLongitude(),0.5);
        System.out.println("Saint-Raphael :" + saint_raphael);
        assertEquals(43, saint_raphael.getLatitude(),0.5);
        assertEquals(6.9, saint_raphael.getLongitude(),0.5);
        System.out.println("Toulon :" + toulon);
        assertEquals(43.1, toulon.getLatitude(),0.5);
        assertEquals(5.9, toulon.getLongitude(),0.5);
        System.out.println("------------- Distances --------------");
        assertEquals(0, nice.distance(nice),0.5);
        double distance = nice.distance(cagnes);
        System.out.println("De Nice à Cagnes :" + distance);
        assertEquals(11, distance/1000,2);
        distance = cagnes.distance(villeneuve);
        System.out.println("De Cagnes à Villeneuve :" + distance);
        assertEquals(5, distance/1000,2);
        System.out.println("De Villeneuve à Antibes :" + villeneuve.distance(antibes));
        System.out.println("De Antibes à Biot :" + antibes.distance(biot));
        System.out.println("De Biot à Valbonne :" + biot.distance(valbonne));
        System.out.println("De Valbonne à Biot :" + valbonne.distance(biot));
        distance = nice.distance(toulon);
        System.out.println("De Nice à Toulon :" + distance);
        //Too much.... perhaps problem with the data
        assertEquals(150, distance/1000,20);

        System.out.println("De Nice à Marseille :" + nice.distance(marseille));

        System.out.println("De Nice à Saint-Foy :" + nice.distance(sainte_foy));
        System.out.println("De Nice à Valbonne :" + nice.distance(valbonne));
        System.out.println("De Biot à Valbonne :" + biot.distance(valbonne));
        System.out.println("De Nice à Biot :" + nice.distance(biot));
        System.out.println("De Nice à La Madeleine :" + nice.distance(laMadeleine));
        System.out.println("De La Madeleine à Laroque :" + laMadeleine.distance(Laroque));

        System.out.println("De Valbonne à Paris :" + valbonne.distance(paris));
        System.out.println("De Nice à Paris :" + nice.distance(paris));
        System.out.println("De Nice à Marseille :" + nice.distance(marseille));
        assertTrue(nice.distance(marseille) < 220000);
        System.out.println("De Marseille à Saint-Raphael :" + marseille.distance(saint_raphael));
        System.out.println("De Saint-Raphael à Saint-Jean :" + saint_raphael.distance(saintJean));

        System.out.println("------------- Carte --------------");

        //MapOfCities mapOfCities = new MapOfCities();
        //mapOfCities.buildGraph(cities, 12000);
        System.out.println(mapOfCities.roads.nbVertices());
        System.out.println(mapOfCities.roads.nbEdges());

        System.out.println("=====> End computing the MAP");
        System.out.println("------------- Check FALSE MAP --------------");
        checkEqualsCity(mapOfCities, nice, "Nice");
        checkFalse(mapOfCities.roads, marseille, cagnes);
        checkFalse(mapOfCities.roads, nice, paris);
        checkFalse(mapOfCities.roads, nice, sainte_foy);
        checkFalse(mapOfCities.roads, nice, valbonne);
        //checkFalse(mapOfCities.roads,nice, laMadeleine);
        System.out.println("------------- Check TRUE MAP --------------");
        // checkTrue(mapOfCities.roads,nice, cagnes); // distance > 11000 !!
        checkTrue(mapOfCities.roads, cagnes, villeneuve);
        checkTrue(mapOfCities.roads, villeneuve, biot);
        checkTrue(mapOfCities.roads, biot, valbonne);
        System.out.println("------------- END Check  --------------");
        System.out.println("------------- Shortest Roads --------------");
        shortestRoadFrom(mapOfCities, "Nice", "Valbonne");
        shortestRoadFrom(mapOfCities, "Valbonne", "Nice");
        shortestRoadFrom(mapOfCities, "Nice", "Biot");
        shortestRoadFrom(mapOfCities, "Nice", "Antibes");
        shortestRoadFrom(mapOfCities, "Nice", "Frejus");
        //shortestRoadFrom(mapOfCities, "Nice", "Saint-Raphael");
        shortestRoadFrom(mapOfCities, "Nice", "Paris");
        checkEqualsCity(mapOfCities, toulon, "Toulon");
        shortestRoadFrom(mapOfCities, "Nice", "Toulon");
        shortestRoadFrom(mapOfCities, "Nice", "Marseille");
        shortestRoadFrom(mapOfCities, "Marseille", "Nice");
        System.out.println("------------- Connected cities --------------");
        ConnectedComponents cc = new ConnectedComponents(mapOfCities.roads);
        Map<Vertex, Integer> mapOfGroups = cc.find();
        int nbOfGroups = mapOfGroups.size();
        System.out.println("Nb of partitions in France : " + cc.getMaxConnectedComponentNumber());
        Vertex parisVertex = getVertex(mapOfCities.roads, "Paris");
        System.out.println("Paris is in group : " + mapOfGroups.get(parisVertex));
        Vertex brestVertex = getVertex(mapOfCities.roads, "Brest");
        System.out.println("Brest is in group : " + mapOfGroups.get(brestVertex));
        Vertex creuseVertex = getVertex(mapOfCities.roads, "La Souterraine");
        System.out.println("La Souterraine is in group : " + mapOfGroups.get(creuseVertex));
        List<Integer> allGroups = new ArrayList<>();
        mapOfGroups.forEach((vertex, groupNumber) -> {
            if (!allGroups.contains(groupNumber)) {
                allGroups.add(groupNumber);
                System.out.println(vertex + " : " + groupNumber);
                Set<Vertex> connectedTo = cc.getConnectedComponentsTo(vertex);
                System.out.println("Connected in group " + groupNumber + " : " + connectedTo.size());
            }
        });


        Set<Vertex> connectedToNice = cc.getConnectedComponentsTo(getVertex(mapOfCities.roads, "Nice"));
        System.out.println("Connected to Nice : " + connectedToNice.size());
        System.out.println("Paris is Connected to Nice : " + connectedToNice.contains(getVertex(mapOfCities.roads, "Paris")));
        System.out.println("Marseille is Connected to Nice : " + connectedToNice.contains(getVertex(mapOfCities.roads, "Marseille")));

        //System.out.println("Connected to Paris : " + cc.getConnectedComponentsTo(getVertex(mapOfCities.roads, "Paris")));

        //System.out.println(mapOfCities.roads);
    }

    private void checkEqualsCity(MapOfCitiesInterface mapOfCitiesInterface, City city, String cityName) {
        assertEquals(city, mapOfCitiesInterface.getCity(cityName));

    }

    private Vertex getVertex(MapOfCities.UnDiGraph4Cities roads, String name) {
        return roads.getVertex(name);
    }

    private void checkFalse(MapOfCities.UnDiGraph4Cities roads, City from, City to) {
        Vertex fromVertex = roads.getVertex(from.getNom());
        Vertex toVertex = roads.getVertex(to.getNom());

        Edge edge = roads.findEdge(fromVertex, toVertex);
        assertNull(edge);
        System.out.println("Edge from " + from + " to " + to + " does not exist");
    }

    private void checkTrue(MapOfCities.UnDiGraph4Cities roads, City from, City to) {
        Vertex fromVertex = roads.getVertex(from.getNom());
        Vertex toVertex = roads.getVertex(to.getNom());

        Edge edge = roads.findEdge(fromVertex, toVertex);
        assertNotNull(edge);
        System.out.println("Edge from " + from + " to " + to + " exists : " + edge);
    }


    private static City getCity(List<City> cities, String Biot) {
        return cities.stream().filter(city -> city.getNom().equals(Biot)).findFirst().orElse(null);
    }

    private static void shortestRoadFrom(MapOfCities mapOfCities, String from, String to) {
        System.out.println("=====> Start computing the shortest path from " + from + " to " + to);
        try {
            List<Vertex> shortestPath = GraphTraversal.dijkstra(
                    mapOfCities.roads,
                    mapOfCities.roads.getVertex(from),
                    mapOfCities.roads.getVertex(to));
            System.out.println(shortestPath);
            double pathDistance = displayShortestPath(mapOfCities, shortestPath);
            double straightDistance = mapOfCities.getCity(from).distance(mapOfCities.getCity(to));
            //on autorise 20km de plus pour les routes qui ne sont pas les plus directes
            assertTrue((pathDistance > straightDistance) && (pathDistance < straightDistance+20000 ) );

        } catch (StackOverflowError e) {
            System.out.println("No path from " + from + " to " + to + " : " + e.getMessage());
            //e.printStackTrace();
        }
        shortestRoadFrom(mapOfCities, mapOfCities.getCity(from), mapOfCities.getCity(to));
    }

    private static void shortestRoadFrom(MapOfCitiesInterface mapOfCitiesInterface, City from, City to) {
        System.out.println("=====> Start computing the shortest path from inside map" + from + " to " + to);
        try {
            List<City> shortestPathOfCities = mapOfCitiesInterface.shortestPath(from, to);
            System.out.println(shortestPathOfCities);
            double pathDistance = displayShortestPathOfCities(mapOfCitiesInterface, shortestPathOfCities);
            double straightDistance = from.distance(to);
            //on autorise 20km de plus pour les routes qui ne sont pas les plus directes
            assertTrue((pathDistance > straightDistance) && (pathDistance < straightDistance+20000 ) );

        } catch (StackOverflowError e) {
            System.out.println("No path from " + from + " to " + to + " : " + e.getMessage());
            //e.printStackTrace();
        }
    }

    private static double displayShortestPathOfCities(MapOfCitiesInterface mapOfCitiesInterface, List<City> shortestPathOfCities) {

        City previous = shortestPathOfCities.get(0);
        City start = previous;
        double distance = 0;
        for (City next : shortestPathOfCities) {
            //System.out.println(next);
            if (previous != next) {
                distance += previous.distance(next);
                System.out.println("Distance from " + previous.getNom() + " to " + next.getNom() + " : " + previous.distance(next));
                //System.out.println("Registered edge" + mapOfCities.findRoad(previous, next));
                //System.out.println("Previous in Path : " + previous);
                //System.out.println("Previous in Graph : " + mapOfCities.getCity(previous.getNom()));
                //System.out.println("Next in Path : " + next);
                //System.out.println("Next in Graph : " + mapOfCities.getCity(next.getNom()));
            }
            previous = next;
        }
        System.out.println("Total distance : " + distance);

        System.out.println("against distance directe : " + start + " to " + previous + " : " + start.distance(previous));

        return distance;
    }

    private static double displayShortestPath(MapOfCitiesInterface mapOfCitiesInterface, List<Vertex> shortestPath) {
        //List<City> cities = mapOfCities.getCities();

        City previous = mapOfCitiesInterface.getCity(shortestPath.get(0).getTag());
        City start = previous;
        double distance = 0;
        for (Vertex v : shortestPath) {
            City next = mapOfCitiesInterface.getCity(v.getTag());
            //System.out.println(next);
            if (previous != next) {
                distance += previous.distance(next);
                System.out.println("Distance from " + previous.getNom() + " to " + next.getNom() + " : " + previous.distance(next));
                //System.out.println("Registered edge" + mapOfCities.findRoad(previous, next));
                //System.out.println("Previous in Path : " + previous);
                //System.out.println("Previous in Graph : " + mapOfCities.getCity(previous.getNom()));
                //System.out.println("Next in Path : " + next);
                //System.out.println("Next in Graph : " + mapOfCities.getCity(next.getNom()));
            }
            previous = next;
        }
        System.out.println("Total distance : " + distance);

        System.out.println("against distance directe : " + start + " to " + previous + " : " + start.distance(previous));

        return distance;
    }



}*/