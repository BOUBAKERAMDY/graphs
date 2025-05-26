package graphs.roadApplication;

import graphs.graphcore.Edge;
import graphs.graphcore.UnDiGraph;

import java.util.List;
import java.util.Map;

public class MapOfCities implements MapOfCitiesInterface{






    private UnDiGraph graph;


    public MapOfCities() {
        graph = new UnDiGraph();
    }



    @Override
    public List<City> getCities() {
        return List.of();
    }

    @Override
    public Edge findRoad(City previous, City next) {
        return null;
    }

    @Override
    public UnDiGraph buildGraph(List<City> citiesWithDoublon, int maxDistance) {
        return null;
    }

    @Override
    public City getCity(String name) {
        return null;
    }

    @Override
    public List<City> shortestPath(City from, City to) {
        return List.of();
    }
}
