import java.util.*;

public class GraphNode {
    private int nodeLabel;
    private List<GraphNode> neighbors;
    private Map<Integer, GraphEdge> edges;

    public GraphNode(int nodeLabel){
        this.nodeLabel = nodeLabel;
        this.neighbors = new ArrayList<GraphNode>();
        this.edges = new HashMap<Integer, GraphEdge>();
    }

    public int getNodeLabel(){
        return nodeLabel;
    }
//
    public boolean addNeighbor(int edgeLabel, GraphNode node, GraphEdge edge){
        if(isAdjacent(node))
            return false;
        neighbors.add(node);
        edges.put(edgeLabel, edge);
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

    public Map<Integer, GraphEdge> getEdges(){
        return edges;
    }

}
