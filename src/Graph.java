public class Graph {

    GraphNode[] adjacencyList;
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
        adjacencyList = new GraphNode[nVertices];
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
        adjacencyList[0] = new GraphNode(0);
        for(int vertex = 1; vertex < nVertices; ++vertex){
            GraphNode node = new GraphNode(vertex);
            GraphNode prevNode = adjacencyList[vertex - 1];
            adjacencyList[vertex] = node;
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
        node1.addEdge(new GraphEdge(node1, node2, weight));
        node2.addEdge(new GraphEdge(node2, node1, weight));
        totalDegree += 2;
        ++nEdges;
        return true;
    }
}
