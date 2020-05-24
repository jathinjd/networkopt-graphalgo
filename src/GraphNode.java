import java.util.HashMap;
import java.util.Map;

public class GraphNode {
    private int nodeLabel;
    private Map<Integer, GraphEdge> edges;

    public GraphNode(int nodeLabel){
        this.nodeLabel = nodeLabel;
        edges = new HashMap<Integer, GraphEdge>();
    }

    public int getNodeLabel(){
        return nodeLabel;
    }

    public int getDegree(){
        return edges.size();
    }

    public boolean isAdjacent(GraphNode doubtNode){
        if(edges.containsKey(doubtNode.getNodeLabel()))
            return true;
        return false;
    }

    public Map<Integer, GraphEdge> getEdges(){
        return edges;
    }

    public boolean addEdge(GraphEdge edge){
        edges.put(edge.getEdgeLabel(), edge);
        return true;
    }
}
