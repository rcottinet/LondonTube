package MainApp;

import java.util.ArrayList;

public class Node implements Comparable<Node>{
    public final String value;
    public ArrayList<Edge> adjacencies = new ArrayList<>();
    public double shortestDistance = Double.POSITIVE_INFINITY;
    public Node parent;

    public Node(String val){
        value = val;
    }

    public String toString(){
        return value;
    }

    public int compareTo(Node other){
        return Double.compare(shortestDistance, other.shortestDistance);
    }
}
