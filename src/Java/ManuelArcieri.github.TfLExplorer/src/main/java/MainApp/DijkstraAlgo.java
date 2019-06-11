package MainApp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

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
                if(distanceFromU<v.shortestDistance){

					/*remove v from queue for updating
					the shortestDistance value*/
                    queue.remove(v);
                    v.shortestDistance = distanceFromU;
                    v.parent = u;
                    queue.add(v);

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



    public static void main(String[] args){

        //initialize the graph base on the LondonTubeMap

        /*
        Marylebone
        BakerStreet
        RegentsPark
        Oxford Circus
        Bond Street
        Marble Arch
        Lancaster Gate
        Hyde Park Corner
        Green Park
        Piccadilly Circus
        Tottenham Court Road
        Warren Street
        Goodge Street
        Holborn
        Convent Garden
        Leicester Square
        Charing Cross
         */
        Node n1 = new Node("Marylebone");
        Node n2 = new Node("BakerStreet");
        Node n3 = new Node("RegentsPark");
        Node n4 = new Node("Oxford Circus");
        Node n5 = new Node("Bond Street");
        Node n6 = new Node("Marble Arch");
        Node n7 = new Node("Lancaster Gate");
        Node n8 = new Node("Hyde Park Corner");
        Node n9 = new Node("Green Park");
        Node n10 = new Node("Piccadilly Circus");
        Node n11 = new Node("Tottenham Court Road");
        Node n12 = new Node("Warren Street");
        Node n13 = new Node("Goodge Street");
        Node n14 = new Node("Holborn");
        Node n15 = new Node("Convent Garden");
        Node n16 = new Node("Leicester Square");
        Node n17 = new Node("Charing Cross");


        /*
        Node[] nodes = {n1,n2,n3,n4,n5,n6,n7,n8,n9,n10,n11,n12,n13,n14};

        for ( Node n : nodes
             ) {

            n.adjacencies = new Edge[]{

            };

        }*/

        //initialize the edges
        n1.adjacencies = new Edge[]{
                new Edge(n2,40)
        };

        n2.adjacencies = new Edge[]{
                new Edge(n1,40),
                new Edge(n3,71),
                new Edge(n5,64)

        };

        n3.adjacencies = new Edge[]{
                new Edge(n2,71),
                new Edge(n4,151)
        };

        n4.adjacencies = new Edge[]{
                new Edge(n1,140),
                new Edge(n5,99),
                new Edge(n3,151),
                new Edge(n6,80),
                new Edge(n9,75),
                new Edge(n10,68),
                new Edge(n11,103),
                new Edge(n12,120)


        };

        n5.adjacencies = new Edge[]{
                new Edge(n2,78),
                new Edge(n6,45),
                new Edge(n4,69),
                new Edge(n9,85)
        };

        n6.adjacencies = new Edge[]{
                new Edge(n4,80),
                new Edge(n7,97)
        };

        n7.adjacencies = new Edge[]{
                new Edge(n6,97)
        };

        n8.adjacencies = new Edge[]{
                new Edge(n9,111)
        };

        n9.adjacencies = new Edge[]{
                new Edge(n8,111),
                new Edge(n10,70),
                new Edge(n5,70),
                new Edge(n4,70)

        };

        n10.adjacencies = new Edge[]{
                new Edge(n9,70),
                new Edge(n4,75),
                new Edge(n16,70),
                new Edge(n17,75)
        };

        n11.adjacencies = new Edge[]{
                new Edge(n9,75),
                new Edge(n16,120),
                new Edge(n14,120),
                new Edge(n13,120)
        };

        n12.adjacencies = new Edge[]{
                new Edge(n4,120),
                new Edge(n13,146)
        };

        n13.adjacencies = new Edge[]{
                new Edge(n12,101),
                new Edge(n14,90)
        };

        n14.adjacencies = new Edge[]{
                new Edge(n11,90),
                new Edge(n15,90)
        };
        n15.adjacencies = new Edge[]{
                new Edge(n14,90),
                new Edge(n16,90)
        };

        n16.adjacencies = new Edge[]{
                new Edge(n11,90),
                new Edge(n15,90),
                new Edge(n17,90),
                new Edge(n10,90)
        };

        n17.adjacencies = new Edge[]{
                new Edge(n10,90),
                new Edge(n16,90)
        };


        Node[] nodes = {n1,n2,n3,n4,n5,n6,n7,n8,n9,n10,n11,n12,n13,n14};

        //compute paths
        computePaths(n1);

        //print shortest paths

		for(Node n: nodes){
			System.out.println("Distance to " +
				n + ": " + n.shortestDistance);
    		List<Node> path = getShortestPathTo(n);
    		System.out.println("Path: " + path);
		}

        List<Node> path = getShortestPathTo(n16);
        System.out.println("Path: " + path);

    }
}
