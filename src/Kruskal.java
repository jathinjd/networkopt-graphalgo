import com.sun.source.tree.Tree;

import java.util.List;

public class Kruskal {
    private Graph graph;
    private int source;
    private int destination;

    public Kruskal(Graph graph, int source, int destination){
        this.graph = graph;
        this.source = source;
        this.destination = destination;
        runKruskal();
    }

    List<GraphEdge> graphEdges;
    MaxHeapEdge heap;
    DisjointSet dSet;
    GraphEdge[] mstEdges;
    int sourceInd;
    int destinationInd;
    int mind;

    public void runKruskal(){
        heap = new MaxHeapEdge();
        graphEdges = graph.getGraphEdges();
        for(GraphEdge edge : graphEdges){
            heap.insert(edge);
        }
        dSet = new DisjointSet();
        for(GraphNode vertex : graph.adjacencyList){
            dSet.makeSet(vertex.getNodeLabel());
        }
        //The second condition ensures we snap out of the while
        //loop as soon as source and destination become part
        //of a single tree. The tree forming is the MST and
        //we can find the mbp connecting source and destination
        //Since we are using path compression, time taken for
        //checking this conditions is just O(1)
        mstEdges = new GraphEdge[graphEdges.size()];
        mind = 0;
        while(!heap.isEmpty() && !sndInTree()){
            GraphEdge edge = heap.poll();
            int sourceVertex = edge.getSource().getNodeLabel();
            int destVertex = edge.getDestination().getNodeLabel();
            int root1 = dSet.findSet(sourceVertex);
            int root2 = dSet.findSet(destVertex);

            if(root1 == root2) continue;
            dSet.union(sourceVertex, destVertex);
            if(source == sourceVertex || source == destVertex)
                sourceInd = mind;
            if(destination == destVertex || destination == sourceVertex)
                destinationInd = mind;
            mstEdges[mind++] = edge;
        }
        //By the end of this while loop, we should have all the edges we need
        //in the mstEdges array
        /*int vertex = source;
        while(vertex != destination){
            GraphNode node1 = graph.adjacencyList[vertex];
        }*/
    }



    private boolean sndInTree(){
        int srcSet = dSet.findSet(source);
        int destSet = dSet.findSet(destination);
        return srcSet == destSet;
    }

}
