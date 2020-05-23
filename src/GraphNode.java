import java.util.ArrayList;
import java.util.List;

public class GraphNode {
    private int nodeLabel;
    private List<GraphNode> neighbors;

    public GraphNode(int nodeLabel){
        this.nodeLabel = nodeLabel;
        this.neighbors = new ArrayList<>();
    }

    public int getNodeLabel(){
        return nodeLabel;
    }

    public boolean addNeighbor(GraphNode node){
        if(isAdjacent(node))
            return false;
        neighbors.add(node);
        return true;
    }

    public int getDegree(){
        return neighbors.size();
    }

    public boolean isAdjacent(GraphNode node){
        if(neighbors.contains(node))
            return true;
        return false;
    }

    public List<GraphNode>  getNeighbors(){
        return neighbors;
    }

}
