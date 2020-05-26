import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class Dijkstra{

    private Graph graph;
    private int source;
    private int destination;

    //when an object is created, dijkstra is automatically run on given graph,
    //source and destination
    public Dijkstra(Graph graph, int source, int destination) {
        this.graph = graph;
        this.source = source;
        this.destination = destination;
        runDijkstra();
    }

    private Set<Integer> inTree;
    private Set<Integer> fringes;
    private Set<Integer> unseen;
    private TreeNode[] nodes;

    public void runDijkstra(){
        inTree = new HashSet<Integer>();
        fringes = new HashSet<Integer>();
        unseen = new HashSet<Integer>();
        nodes = new TreeNode[graph.getNVertices()];
        //Set all nodes as unseen
        for(int vertex = 0; vertex < graph.getNVertices(); ++vertex){
            TreeNode node = new TreeNode(vertex, Integer.MIN_VALUE);
            node.status = 0;
            nodes[vertex] = node;
            unseen.add(vertex);
        }
        //Set source node as seen
        unseen.remove(source);
        inTree.add(source);
        nodes[source].status = 2;
        GraphNode sourceVertex = graph.adjacencyList.get(source);
        for(int adjVertexLabel : sourceVertex.getEdges().keySet()){
            nodes[adjVertexLabel].status = 1;
            unseen.remove(adjVertexLabel);
            fringes.add(adjVertexLabel);
            nodes[adjVertexLabel].bandwidth = getWeight(sourceVertex.getNodeLabel(), adjVertexLabel);
            nodes[adjVertexLabel].parentLabel = sourceVertex.getNodeLabel();
        }

        while(!fringes.isEmpty() && nodes[destination].status != 2){
            //pick the fringe with highest bandwidth
            int maxbwinfringe = Integer.MIN_VALUE;
            int maxbwfringe = -1;
            for(int fringeLabel : fringes){
                if(nodes[fringeLabel].bandwidth > maxbwinfringe){
                    maxbwfringe = fringeLabel;
                }
            }
            //set maxbwfringe intree
            nodes[maxbwfringe].status = 2;
            fringes.remove(maxbwfringe);
            inTree.add(maxbwfringe);

            Map<Integer, GraphEdge> adjList = graph.adjacencyList.get(maxbwfringe).getEdges();
            for(int adjVertex : adjList.keySet()){
                TreeNode node = nodes[adjVertex];
                if(node.status == 0){
                    //Convert status to fringe from unseen
                    node.status = 1;
                    unseen.remove(adjVertex);
                    fringes.add(adjVertex);
                    //Update bandwidth so far and parent
                    node.parentLabel = maxbwfringe;
                    node.bandwidth = Math.min(nodes[maxbwfringe].bandwidth,
                            getWeight(maxbwfringe, adjVertex));
                }
                else if(node.status == 1 && node.bandwidth < Math.min(nodes[maxbwfringe].bandwidth,
                        getWeight(maxbwfringe, adjVertex))){
                    node.parentLabel = maxbwfringe;
                    node.bandwidth = Math.min(nodes[maxbwfringe].bandwidth,
                            getWeight(maxbwfringe, adjVertex));
                }
            }
        }
        //By the end of this while loop, either all vertices are added inTree and their
        //bandwidths are the maximum bandwidths from the source or the destination got
        //added inTree and we have the required maximum bandwidth pah from source.
        //To get the path, we hold the destination node and keep following parent node
        //until we reach the source
    }

    int getWeight(int node1Label, int node2Label){
        return graph.adjacencyList.get(node1Label).getEdges().get(node2Label).getWeight();
    }

    public String getMaxBwPath(){
        StringBuilder path = new StringBuilder();
        int nodeLabel = destination;
        while(nodes[nodeLabel].parentLabel != -1){
            StringBuilder block = new StringBuilder(Integer.toString(nodeLabel) + "(" +
                    getWeight(nodeLabel, nodes[nodeLabel].parentLabel) + ") -> ");
            path.append(block.reverse().toString());
            nodeLabel = nodes[nodeLabel].parentLabel;
        }
        StringBuilder sourcelab = new StringBuilder();
        sourcelab.append(nodeLabel);
        sourcelab.reverse();
        path.append(" >- ");
        path.append(sourcelab.toString());
        path.reverse();
        path.setLength(path.length() - 4);
        return path.toString();
    }
    public int getMaxBw(){
        return nodes[destination].bandwidth;
    }

    public static void main(String args[]){
        Graph grp = new SparseGraph(100);
        Graph dgrp = new DenseGraph(100);
        Dijkstra d = new Dijkstra(grp, 1, 11);
        Dijkstra dd = new Dijkstra(dgrp, 1, 11);
        System.out.println("Max bw: " + d.getMaxBw());
        System.out.println("Max bwp: " + d.getMaxBwPath());
        System.out.println("Max bw: " + dd.getMaxBw());
        System.out.println("Max bwp: " + dd.getMaxBwPath());
    }
}