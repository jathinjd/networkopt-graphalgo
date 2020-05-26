import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Graph {

    Map<Integer, GraphNode> adjacencyList;
    private List<GraphEdge> graphEdges;
    private int nVertices;
    private int totalDegree;
    private int nEdges;
    private int maxWeight = 1000;

    /*
    //General Method for an empty Graph
    //Might need a methods that can add adjacencyList capacity dynamically
    public Graph(){
    }
     */

    public Graph(int nVertices){
        this.nVertices = nVertices;
        adjacencyList = new HashMap<Integer, GraphNode>();
        graphEdges = new ArrayList<GraphEdge>();
        totalDegree = 0;
        nEdges = 0;
    }

    public int getNVertices() {
        return nVertices;
    }

    public int getNEdges(){
        return nEdges;
    }

    public int getTotalDegree(){
        return totalDegree;
    }

    //Creates all nVertices and joins them in a line
    //Used for both graph types to avoid disconnected components
    void generateSkeleton(){
        adjacencyList.put(0, new GraphNode(0));
        for(int vertex = 1; vertex < nVertices; ++vertex){
            GraphNode node = new GraphNode(vertex);
            GraphNode prevNode = adjacencyList.get(vertex - 1);
            adjacencyList.put(vertex, node);
            //Adding the edge with a random weight in the range [1, maxWeight]
            //Every time we create an edge, add the other node to neighbors
            //list of the adjacent node and add to degree
            int weight = generateRandomWeight();
            addEdge(node, prevNode, weight);
        }
        //The last node is not joined to the first node
        //We do not need this for ensuring full connectivity
        //At the end of this, we end up with a line of graph nodes
    }

    int generateRandomWeight(){
        return (int) Math.floor(Math.random() * maxWeight + 1);
    }

    int getRandomNodeLabel(){
        return (int) Math.floor(Math.random() * nVertices);
    }

    boolean addEdge(GraphNode node1, GraphNode node2, int weight){
        GraphEdge edge1 =  new GraphEdge(node1, node2, weight);
        GraphEdge edge2 =  new GraphEdge(node2, node1, weight);
        node1.addEdge(edge1);
        node2.addEdge(edge2);
        totalDegree += 2;
        graphEdges.add(edge1);
        ++nEdges;
        return true;
    }

    List<GraphEdge> getGraphEdges(){
        return graphEdges;
    }
}
