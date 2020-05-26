import java.util.Map;

public class SparseGraph extends Graph {
    public SparseGraph(int nVertices) {
        super(nVertices);
        generateSkeleton();
        generateSparseGraph();
    }

    private void generateSparseGraph(){
        while((super.getTotalDegree() / super.getNVertices()) < 6){
            GraphNode node1 = adjacencyList[getRandomNodeLabel()];
            GraphNode node2 = adjacencyList[getRandomNodeLabel()];
            //Check conditions to ensure randomly picked graphs are not
            //same or already adjacent. If they are, we re-loop so that
            //we can randomize again
            if(node1 == node2) continue;
            if(node1.isAdjacent(node2) || node2.isAdjacent(node1))
                continue;
            int weight = generateRandomWeight();
            addEdge(node1, node2, weight);
        }
    }

    public static void main(String args[]){
        int verts = 100;
        SparseGraph graph = new SparseGraph(verts);
        System.out.println("Dense graph:");
        System.out.println("Total degree: " + graph.getTotalDegree() + "  Average degree: " +
                (graph.getTotalDegree() / graph.getNVertices()) + "  Total Edges = " + graph.getNEdges() +
                " graphEdges set size: " + graph.getGraphEdges().size());
        for(int i = 0; i < verts; ++i){
            System.out.print(i + ": ");
            GraphNode currentNode = graph.adjacencyList[i];
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
