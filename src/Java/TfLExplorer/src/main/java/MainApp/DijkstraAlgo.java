package MainApp;

import service.ServiceSqlRequest;
import java.util.*;

public class DijkstraAlgo {
    /* Dijkstra Algorithm
     *
     *
     */
    public static void computePaths(Node source){
        source.shortestDistance=0;

        //implement a priority queue
        PriorityQueue<Node> queue = new PriorityQueue<Node>();
        queue.add(source);

        while(!queue.isEmpty()){
            Node u = queue.poll();

			/*visit the adjacencies, starting from
			the nearest node(smallest shortestDistance)*/

            for(Edge e: u.adjacencies){

                Node v = e.target;
                double weight = e.weight;

                //relax(u,v,weight)
                double distanceFromU = u.shortestDistance+weight;

                try{
                    if(distanceFromU<v.shortestDistance){

					/*remove v from queue for updating
					the shortestDistance value*/
                        queue.remove(v);
                        v.shortestDistance = distanceFromU;
                        v.parent = u;
                        queue.add(v);

                    }
                }catch (Exception er){
                    System.out.println(er);

                }

            }
        }
    }

    public static List<Node> getShortestPathTo(Node target){

        //trace path from target to source
        List<Node> path = new ArrayList<Node>();

        for(Node node = target; node!=null; node = node.parent){
            path.add(node);
        }

        //reverse the order such that it will be from source to target
        Collections.reverse(path);

        return path;
    }

    public static double getTimeShortestPathTo(List<Node> path){

        double time =0;
        ServiceSqlRequest base = new ServiceSqlRequest();
        String previous_line = "";
        previous_line = base.getLinebetweenStations(path.get(0).value,path.get(1).value);
        path.get(0).line = previous_line;

        for(int i=1; i< path.size(); i++){


                /*Add 1 min if their is line changement*/
                if(!previous_line.equals(base.getLinebetweenStations(path.get(i - 1).value, path.get(i).value))){
                    time +=3;

                    System.out.println("");

                    System.out.println(base.getLinebetweenStations(path.get(0).value,path.get(1).value )+ " [INFO] 3 min add for the line changement");
                    previous_line = base.getLinebetweenStations(path.get(i-1).value,path.get(i).value);
                    path.get(i).line = previous_line;


                }else{
                    System.out.println("");
                    path.get(i).line = previous_line;
                }


                System.out.println("Station : "+path.get(i).value+" join by the line : "+previous_line);
                time += base.getTimeBetweenStations(path.get(i-1).value,path.get(i).value);

        }
        return time;
    }

    public static Node findUsingEnhancedForLoop(
            String station, List<Node> nodes) {

        for (Node n : nodes) {
            if (n.value.equals(station)) {
                return n;
            }
        }
        return null;
    }

    public static Node[] initGraphe(){

        ServiceSqlRequest base = new ServiceSqlRequest();

        return null;
    }



    public static List<Node> finalDirection(String stationFrom, String stationTo){

        ServiceSqlRequest base = new ServiceSqlRequest();

        ArrayList<Node> nodeslist = new ArrayList<>(base.getListAllStation());

        HashMap<String, Node> hmap = new HashMap<>();


        for( Node n : nodeslist){
            hmap.put(n.value,n);
        }
        for(Map.Entry<String, Node> entry : hmap.entrySet()) {


            String cle = entry.getKey();
            Node valeur = entry.getValue();


            /*try {*/
            for (String edge : base.getAdjacencies(cle)) {
                double time = 0;
                try {
                    time = base.getTimeBetweenStations(
                            valeur.value,
                            hmap.get(edge).value);
                }catch(Exception e ){
                    System.out.println(e +" : Erreur pour le getTime dans l'affecte des arretes");
                }



                try{
                    valeur.adjacencies.add(

                            new Edge(
                                    hmap.get(edge),
                                    time
                            )
                    );
                }catch (Exception e){
                    System.out.println(e);
                }

            }
            /*}catch (Exception e) {
                System.out.println(e);
            }*/



            entry.setValue(valeur);
        }

        nodeslist.clear();

        for(Map.Entry<String, Node> entry : hmap.entrySet()) {
            nodeslist.add(entry.getValue());
        }

        //initialize the edges
        /*n1.adjacencies = new Edge[]{
                new Edge(n2,base.getTimeBetweenStations(n1.value,n2.value))
        };
        */

        /*Node[] nodes = {n1,....,n14};*/

        //compute paths




        computePaths(findUsingEnhancedForLoop(stationFrom.toUpperCase(),nodeslist));


        //print shortest paths

        for(Node n: nodeslist){
            System.out.println("Distance to " +
                    n + ": " + n.shortestDistance);
            List<Node> path = getShortestPathTo(n);
            System.out.println("Path: " + path);
        }


        List<Node> path = getShortestPathTo(findUsingEnhancedForLoop(stationTo.toUpperCase(),nodeslist));
        System.out.println("Path: " + path);


        System.out.println("TEMPS DE TRAJET : ----> " + getTimeShortestPathTo(path));

        return path;
    }

    public static void main(String[] args){

        //initialize the graph base on the LondonTubeMap


        /*Node n1 = new Node("Marylebone");
        Node n2 = new Node("Baker Street");
        Node n3 = new Node("Regents Park");
        */



        Scanner sc = new Scanner(System.in);
        System.out.println("Station from : ");

        String stationFrom = sc.nextLine();

        System.out.println("Station to : ");

        String stationTo = sc.nextLine();

        List<Node> path = finalDirection(stationFrom,stationTo);


    }
}

//south harrow - edgware road | Work | problem is solve
// KENTON - ANGEL | WORK
