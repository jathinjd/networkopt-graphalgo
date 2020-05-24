public class GraphEdge {
    private GraphNode sourceNode;
    private GraphNode destinationNode;
    private int edgeLabel;
    private int weight;

    public GraphEdge(GraphNode sourceNode, GraphNode destinationNode, int weight){
        edgeLabel = destinationNode.getNodeLabel();
        this.sourceNode = sourceNode;
        this.destinationNode = destinationNode;
        this.weight = weight;
    }

    public int getWeight(){
        return weight;
    }

    public GraphNode getOtherNode(GraphNode node){
        if(node == sourceNode)
            return destinationNode;
        else if(node == destinationNode)
            return sourceNode;
        else
            return null;
    }

    public int getEdgeLabel() {
        return edgeLabel;
    }
}
