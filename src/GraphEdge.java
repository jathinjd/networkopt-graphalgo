public class GraphEdge {
    private int label;
    private GraphNode node1;
    private GraphNode node2;
    private int weight;

    public GraphEdge(int label){
        this.label = label;
        this.node1 = null;
        this.node2 = null;
        this.weight = 0;
    }

    public GraphEdge(int label, GraphNode node1, GraphNode node2){
        this.label = label;
        this.node1 = node1;
        this.node2 = node2;
        this.weight = 0;
    }

    public GraphEdge(int label, GraphNode node1, GraphNode node2, int weight){
        this.label = label;
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

    public int getLabel(){
        return label;
    }

}
