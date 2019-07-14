package MainApp;

import java.util.List;

import static MainApp.DijkstraAlgo.finalDirection;
import static MainApp.DijkstraAlgo.getTimeShortestPathTo;

public class Itinerary {

    public List<Node> path;

    public String stationFrom;
    public String stationTo;

    public double time;

    public Itinerary(String stationFrom, String stationTo){

        path = finalDirection(stationFrom,stationTo);

        this.stationFrom = stationFrom;

        this.stationTo = stationTo;

        time = getTimeShortestPathTo(this.path);
    }
}
