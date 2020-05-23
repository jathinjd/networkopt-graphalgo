import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Graph {

    private GraphNode[] adjacencyList;
    private List<GraphEdge> edges;
    private int nVertices;
    int totalDegree;
    int maxWeight = 1000;
    double upperPercent = 0.25;
    double lowerPercent = 0.15;

    public Graph(int nVertices){
        this.nVertices = nVertices;
        this.totalDegree = 0;
        this.adjacencyList = new GraphNode[nVertices];
        this.edges = new ArrayList<GraphEdge>();
    }

    public Graph(int nVertices, boolean sparse){
        this.nVertices = nVertices;
        this.totalDegree = 0;
        this.adjacencyList = new GraphNode[nVertices];
        this.edges = new ArrayList<GraphEdge>();
        generateSkeleton();
        if(sparse)
            generateSparseGraph();
        else
            generateDenseGraph();
    }

    //Creates all nVertices and joins them in a circle
    //Done for both graph types to avoid disconnected components
    private void generateSkeleton(){
        adjacencyList[0] = new GraphNode(0);
        for(int vertex = 1; vertex < nVertices; ++vertex){
            GraphNode node = new GraphNode(vertex);
            GraphNode prevNode = adjacencyList[vertex - 1];
            adjacencyList[vertex] = node;
            //Adding the edge with a random weight in the range [1, 1000]
            //Every time we create an edge, add the other node to neighbors
            //list of the adjacent node and add to degree
            int weight = (int) Math.floor(Math.random() * maxWeight + 1);
            GraphEdge edge = new GraphEdge(edges.size(), node, prevNode, weight);
            node.addNeighbor(edge.getLabel(), prevNode, edge);
            prevNode.addNeighbor(edge.getLabel(), node, edge);
            edges.add(edge);
            totalDegree += 2; //Since the edge connects two nodes
        }
        //The last node is not joined to the first node
        //We do not need this for ensuring full connectivity
        //At the end of this, we end up with a line of graph nodes
    }

    private void generateSparseGraph(){
        while((totalDegree / nVertices) < 6){
            GraphNode node1 = adjacencyList[(int) Math.floor(Math.random() * nVertices)];
            GraphNode node2 = adjacencyList[(int) Math.floor(Math.random() * nVertices)];
            //Check conditions to ensure randomly picked graphs are not
            //same or already adjacent. If they are, we re-loop so that
            //we can randomize again
            if(node1 == node2) continue;
            if(node1.isAdjacent(node2) || node2.isAdjacent(node1))
                continue;
            int weight = (int) Math.floor(Math.random() * maxWeight + 1);
            GraphEdge edge = new GraphEdge(edges.size(), node1, node2, weight);
            node1.addNeighbor(edge.getLabel(), node2, edge);
            node2.addNeighbor(edge.getLabel(), node1, edge);
            edges.add(edge);
            totalDegree += 2;
        }
    }

    private void generateDenseGraph(){
        for(int vertex = 0; vertex < nVertices; ++vertex){
            GraphNode node = adjacencyList[vertex];
            if(node.getDegree() > upperPercent * nVertices) continue;
            while(node.getDegree() < lowerPercent * nVertices){
                GraphNode otherNode = adjacencyList[(int) Math.floor(Math.random() * nVertices)];
                //Check conditions to ensure randomly picked graphs are not
                //same or already adjacent or they do not already have too high
                //degree. If they are, we re-loop so we can randomize again
                if(otherNode == node || otherNode.isAdjacent(node) || node.isAdjacent(otherNode)
                        || otherNode.getDegree() > upperPercent * nVertices) continue;
                int weight = (int) Math.floor(Math.random() * maxWeight + 1);
                GraphEdge edge = new GraphEdge(edges.size(), node, otherNode, weight);
                node.addNeighbor(edge.getLabel(), otherNode, edge);
                otherNode.addNeighbor(edge.getLabel(), node, edge);
                edges.add(edge);
                totalDegree += 2;
            }
        }
    }

    public static void main(String args[]){
        //Prints out graph statistics for sparse and dense graphs generated with given
        //number of vertices - verts;
        int verts = 100;

        Graph sparse = new Graph(verts, true);
        Graph dense = new Graph(verts, false);

        System.out.println("Sparse graph:");
        System.out.println("Total degree: " + sparse.totalDegree + "  Average degree: " +
                (sparse.totalDegree / sparse.nVertices) + "  Total Edges = " + sparse.edges.size());
        for(int i = 0; i < verts; ++i){
            System.out.print(i + ": ");
            GraphNode currentNode = sparse.adjacencyList[i];
            Map<Integer, GraphEdge> connectingEdges = currentNode.getEdges();
            for(int eLabel : connectingEdges.keySet()){
                GraphEdge cEdge = connectingEdges.get(eLabel);
                GraphNode otherNode = cEdge.getOtherNode(currentNode);
                System.out.print(otherNode.getNodeLabel() + "(" + cEdge.getWeight() + ") ");
            }
            System.out.print("\n");
        }
        System.out.println("Dense graph:");
        System.out.println("Total degree: " + dense.totalDegree + "  Average degree: " +
                (dense.totalDegree / dense.nVertices) + "  Total Edges = " + dense.edges.size());
        for(int i = 0; i < verts; ++i){
            System.out.print(i + ": ");
            GraphNode currentNode = dense.adjacencyList[i];
            Map<Integer, GraphEdge> connectingEdges = currentNode.getEdges();
            for(int eLabel : connectingEdges.keySet()){
                GraphEdge cEdge = connectingEdges.get(eLabel);
                GraphNode otherNode = cEdge.getOtherNode(currentNode);
                System.out.print(otherNode.getNodeLabel() + "(" + cEdge.getWeight() + ") ");
            }
            System.out.print("\n");
        }

    }


}
