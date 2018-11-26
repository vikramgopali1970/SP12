package SP12;

import rbk.BFSOO;
import rbk.Graph;
import rbk.Graph.Vertex;

import java.io.File;
import java.util.Scanner;

public class Diameter {


    /**
     * This is a helper method which will return a vertex which has the highest distance and if
     * multiple vertices in the graph have maximum distance it returns the first vertex.
     *
     * @return  Vertex which has the highest distance
     * */
    private Vertex getMaxDistanceFromSource(Graph g,BFSOO b) {
        rbk.Graph.Vertex maxDistanceVertex = g.getVertex(1);
        for(rbk.Graph.Vertex u : g){
            if(b.getDistance(u) > b.getDistance(maxDistanceVertex)){
                maxDistanceVertex = u;
            }
        }
        return maxDistanceVertex;
    }

    /**
     * This method is used to calculate the diameter of the undirected acyclic graph which is the
     * longest distance between 2 vertices.
     *
     * @return  returns the diameter of the undirected acyclic graph.
     * */



    /**
     * This is a static method to calculate the diameter of the undirected acyclic graph which is the
     * longest distance between 2 vertices.
     *
     * @param g undirected acyclic graph whose diameter is to be calculated.
     * @return  returns the diameter of the undirected acyclic graph.
     * */
    public static int diameter(Graph g){
        BFSOO b = new BFSOO(g);
        Diameter dot = new Diameter();
        rbk.Graph.Vertex src = g.getVertex(1);
        b.bfs(src);
        Vertex maxDist = dot.getMaxDistanceFromSource(g,b);
        b.bfs(maxDist);
        return b.getDistance(dot.getMaxDistanceFromSource(g,b));
    }

    public static void main(String[] args) throws Exception {
        String string = "10 9  1 2 0    2 3 0   3 4 0   2 6 0   1 5 0   5 7 0   5 8 0   8 9 0   8 10 0  4";
        Scanner in;
        // If there is a command line argument, use it as file from which
        // input is read, otherwise use input from string.
        in = args.length > 0 ? new Scanner(new File(args[0])) : new Scanner(string);
        // Read graph from input
        Graph g = Graph.readGraph(in,false);
        int s = in.nextInt();

        // Call breadth-first search
        System.out.println("Diameter of Undirected Acyclic Graph is "+diameter(g));
    }
}
