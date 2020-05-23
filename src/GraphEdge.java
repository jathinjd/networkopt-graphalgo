public class GraphEdge {
    private int edgeLabel;
    private GraphNode node1;
    private GraphNode node2;
    private int weight;

    public GraphEdge(int edgeLabel){
        this.edgeLabel = edgeLabel;
        this.node1 = new GraphNode();
        this.node2 = new GraphNode();
        this.weight = 0;
    }

    public GraphEdge(int edgeLabel, GraphNode node1, GraphNode node2){
        this.edgeLabel = edgeLabel;
        this.node1 = node1;
        this.node2 = node2;
        this.weight = 0;
    }

    public GraphEdge(int edgeLabel, GraphNode node1, GraphNode node2, int weight){
        this.edgeLabel = edgeLabel;
        this.node1 = node1;
        this.node2 = node2;
        this.weight = weight;
    }

    public int getWeight(){
        return this.weight;
    }

    public GraphNode getOtherNode(GraphNode node){
        if(node1.getNodeLabel() == node.getNodeLabel()){
            return node2;
        }
        else if(node2.getNodeLabel() == node.getNodeLabel()){
            return node1;
        }
        return null;
    }

    public int getEdgeLabel(){
        return edgeLabel;
    }

}
