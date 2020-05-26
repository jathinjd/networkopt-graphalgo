import java.util.Map;

public class DenseGraph extends Graph {
    private double upperPercent = 0.25;
    private double lowerPercent = 0.17;

    public DenseGraph(int nVertices) {
        super(nVertices);
        generateSkeleton();
        generateDenseGraph();
    }

    private void generateDenseGraph(){
        for(int vertex = 0; vertex < getNVertices(); ++vertex){
            GraphNode node = adjacencyList[vertex];
            if(node.getDegree() > upperPercent * getNVertices()) continue;
            while(node.getDegree() < lowerPercent * getNVertices()){
                GraphNode otherNode = adjacencyList[getRandomNodeLabel()];
                //Check conditions to ensure randomly picked graphs are not
                //same or already adjacent or they do not already have too high
                //degree. If they are, we re-loop so we can randomize again
                if(otherNode == node || otherNode.isAdjacent(node) || node.isAdjacent(otherNode)
                        || otherNode.getDegree() > upperPercent * getNVertices()) continue;
                int weight = generateRandomWeight();
                addEdge(node, otherNode, weight);
            }
        }
    }

    public static void main(String args[]){
        int verts = 100;
        DenseGraph graph = new DenseGraph(verts);
        System.out.println("Dense graph:");
        System.out.println("Total degree: " + graph.getTotalDegree() + "  Average degree: " +
                (graph.getTotalDegree() / graph.getNVertices()) + "  Total Edges = " + graph.getNEdges() +
                " graphEdges List size: " + graph.getGraphEdges().size());
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
