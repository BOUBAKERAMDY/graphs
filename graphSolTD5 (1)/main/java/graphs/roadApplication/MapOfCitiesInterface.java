package graphs.roadApplication;

import graphs.graphcore.Edge;
import graphs.graphcore.UnDiGraph;

import java.util.List;

public interface MapOfCitiesInterface {
    List<City> getCities();

    Edge findRoad(City previous, City next);

    //private static final int maxNumberOfNeighbors = 5;
    UnDiGraph buildGraph(List<City> citiesWithDoublon, int maxDistance);

    City getCity(String name);

    List<City> shortestPath(City from, City to);
}
