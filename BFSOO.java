/** Breadth-first search: Object-oriented version
 *  @author SP12.rbk
 *  Version 1.0: 2018/10/16
 */

package SP12;

import SP12.Graph.Vertex;
import SP12.Graph.Edge;
import SP12.Graph.Factory;

import java.io.File;
import java.util.Queue;
import java.util.LinkedList;
import java.util.Scanner;

public class BFSOO extends Graph.GraphAlgorithm<BFSOO.BFSVertex> {
    public static final int INFINITY = Integer.MAX_VALUE;
    Vertex src;

    // Class to store information about vertices during BFS
    public static class BFSVertex implements Factory {
        boolean seen;
        Vertex parent;
        int distance;  // distance of vertex from source
        public BFSVertex(Vertex u) {
            seen = false;
            parent = null;
            distance = INFINITY;
        }
        public BFSVertex make(Vertex u) { return new BFSVertex(u); }
    }

    // code to initialize storage for vertex properties is in GraphAlgorithm class
    public BFSOO(Graph g) {
        super(g, new BFSVertex(null));
    }


    // getter and setter methods to retrieve and update vertex properties
    public boolean getSeen(Vertex u) {
        return get(u).seen;
    }

    public void setSeen(Vertex u, boolean value) {
        get(u).seen = value;
    }

    public Vertex getParent(Vertex u) {
        return get(u).parent;
    }

    public void setParent(Vertex u, Vertex p) {
        get(u).parent = p;
    }

    public int getDistance(Vertex u) {
        return get(u).distance;
    }

    public void setDistance(Vertex u, int d) {
        get(u).distance = d;
    }

    /**
     * This method resets the data of the graph so that it can be used again for another call of the algorithm bfs. It
     * does the initial setting up of values.
     *
     * @param src   It is a vertex which needs to be set as a source vertex
     * */
    public void initialize(Vertex src) {
        for(Vertex u: g) {
            setSeen(u, false);
            setParent(u, null);
            setDistance(u, INFINITY);
        }
        setDistance(src, 0);
    }

    /**
     * It is a setter method to set the source vertex.
     *
     * @param src   It is a vertex which needs to be set as a source vertex
     * */
    public void setSource(Vertex src) {
        this.src = src;
    }

    /**
     * It is a getter method to get the source vertex.
     *
     * @return   It returns the value of source vertex
     * */
    public Vertex getSource() {
        return this.src;
    }

    // Visit a node v from u
    /**
     * This method denotes that the vertex v is visited from u and sets this vertex v as visited
     * and also sets its parent as u since it was visited from u.
     *
     * @param   u   It is the vertex whose neighbour is being visited.
     * @param   v   It is the neighbour of u which is being visited.
     * */
    void visit(Vertex u, Vertex v) {
        setSeen(v, true);
        setParent(v, u);
        setDistance(v, getDistance(u)+1);
    }

    /**
     * This method performs breadth first search for the given graph from  a specified source.
     *
     * @param   src   The source vertex from where the Breadth First Search will begin.
     * */
    public void bfs(Vertex src) {
        setSource(src);
        initialize(src);

        Queue<Vertex> q = new LinkedList<>();
        q.add(src);
        setSeen(src, true);

        while(!q.isEmpty()) {
            Vertex u = q.remove();
            for(Edge e: g.incident(u)) {
                Vertex v = e.otherEnd(u);
                if(!getSeen(v)) {
                    visit(u,v);
                    q.add(v);
                }
            }
        }
        System.out.println();
    }


    /**
     * This is a helper method which will return a vertex which has the highest distance and if
     * multiple vertices in the graph have maximum distance it returns the first vertex.
     *
     * @return  Vertex which has the highest distance
     * */
    private Vertex getMaxDistanceFromSource() {
        Vertex maxDistanceVertex = g.getVertex(1);
        for(Vertex u : g){
            if(this.getDistance(u) > getDistance(maxDistanceVertex)){
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
    private int diameterOfTree(){
        Vertex src = g.getVertex(1);
        this.bfs(src);
        Vertex maxDist = getMaxDistanceFromSource();
        this.bfs(maxDist);
        return getDistance(getMaxDistanceFromSource());
    }


    /**
     * This is a static method to calculate the diameter of the undirected acyclic graph which is the
     * longest distance between 2 vertices.
     *
     * @param g undirected acyclic graph whose diameter is to be calculated.
     * @return  returns the diameter of the undirected acyclic graph.
     * */
    public static int diameterOfTree(Graph g){
        BFSOO dt = new BFSOO(g);
        return  dt.diameterOfTree();
    }

    /**
     * This is a static method to run the Breadth First search algorithm on a graph g with start vertex source.
     *
     * @param g undirected acyclic graph.
     * @param src source vertex for running BFS.
     * @return  BFSOO object with BFS run with specified vertex as start vertex.
     * */
    public static BFSOO breadthFirstSearch(Graph g, Vertex src) {
        BFSOO b = new BFSOO(g);
        b.bfs(src);
        return b;
    }


    /**
     * This is a static method to run the Breadth First search algorithm on a graph g with start vertex index.
     *
     * @param g undirected acyclic graph .
     * @param s index of source vertex for running BFS.
     * @return  BFSOO object with BFS run with specified vertex index as start vertex.
     * */
    public static BFSOO breadthFirstSearch(Graph g, int s) {
        return breadthFirstSearch(g, g.getVertex(s));
    }

    public static void main(String[] args) throws Exception {
        String string = "10 9  1 2 0    2 3 0   3 4 0   2 6 0   1 5 0   5 7 0   5 8 0   8 9 0   8 10 0  4";
        Scanner in;
        // If there is a command line argument, use it as file from which
        // input is read, otherwise use input from string.
        in = args.length > 0 ? new Scanner(new File(args[0])) : new Scanner(string);
        // Read graph from input
//        Graph g = Graph.readDirectedGraph(in);
        Graph g = Graph.readGraph(in,false);
        int s = in.nextInt();

        // Call breadth-first search
        BFSOO b = breadthFirstSearch(g, s);


        g.printGraph(false);

        System.out.println("Output of BFS:\nNode\tDist\tParent\n----------------------");
        for(Vertex u: g) {
            if(b.getDistance(u) == INFINITY) {
                System.out.println(u + "\tInf\t--");
            } else {
                System.out.println(u + "\t" + b.getDistance(u) + "\t" + b.getParent(u));
            }
        }

        System.out.println(diameterOfTree(g));
    }
}

/* Sample run:
______________________________________________
Graph: n: 7, m: 8, directed: true, Edge weights: false
1 :  (1,2) (1,3)
2 :  (2,4)
3 :  (3,4)
4 :  (4,5)
5 :  (5,1)
6 :  (6,7)
7 :  (7,6)
______________________________________________
Output of BFS:
Node	Dist	Parent
----------------------
1	0	null
2	1	1
3	1	1
4	2	2
5	3	4
6	Inf	--
7	Inf	--
*/