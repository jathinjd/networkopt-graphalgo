import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class DijkstraHeap {
    private Graph graph;
    private int source;
    private int destination;

    //when an object is created, dijkstra is automatically run on given graph,
    //source and destination
    public DijkstraHeap(Graph graph, int source, int destination) {
        this.graph = graph;
        this.source = source;
        this.destination = destination;
        runDijkstraHeap();
    }

    private int getWeight(int node1Label, int node2Label){
        return graph.adjacencyList.get(node1Label).getEdges().get(node2Label).getWeight();
    }

    private Set<Integer> inTree;
    private MaxHeap fringeHeap;
    private Set<Integer> unseen;
    TreeNode[] nodes;

    public void runDijkstraHeap(){
        inTree = new HashSet<Integer>();
        fringeHeap = new MaxHeap(graph);
        unseen = new HashSet<Integer>();
        nodes = new TreeNode[graph.getNVertices()];
        //Set all nodes as unseen
        for(int vertex = 0; vertex < graph.getNVertices(); ++vertex){
            TreeNode node = new TreeNode(vertex, Integer.MIN_VALUE);
            node.status = 0;
            nodes[vertex] = node;
            unseen.add(vertex);
        }
        //Set source node as inTree and set adjacent nodes as fringes
        unseen.remove(source);
        inTree.add(source);
        nodes[source].status = 2;
        GraphNode sourceVertex = graph.adjacencyList.get(source);
        for(int adjVertexLabel : sourceVertex.getEdges().keySet()){
            nodes[adjVertexLabel].status = 1;
            unseen.remove(adjVertexLabel);
            nodes[adjVertexLabel].bandwidth = getWeight(sourceVertex.getNodeLabel(), adjVertexLabel);
            nodes[adjVertexLabel].parentLabel = sourceVertex.getNodeLabel();
            fringeHeap.insert(nodes[adjVertexLabel]);
            //It is important that insertion in heap happen last as we need node to contain
            //parentLabel data.
        }

        while(!fringeHeap.isEmpty() && nodes[destination].status != 2){
            //pick the fringe with highest bandwidth;
            TreeNode maxbwfringe = fringeHeap.poll();
            //set maxbwfringe intree
            nodes[maxbwfringe.label].status = 2;
            inTree.add(maxbwfringe.label);

            Map<Integer, GraphEdge> adjList = graph.adjacencyList.get(maxbwfringe.label).getEdges();
            for(int adjVertex : adjList.keySet()){
                TreeNode node = nodes[adjVertex];
                if(node.status == 0){
                    //Convert status to fringe from unseen
                    node.status = 1;
                    unseen.remove(adjVertex);
                    //Update bandwidth so far and parent
                    node.parentLabel = maxbwfringe.label;
                    node.bandwidth = Math.min(nodes[maxbwfringe.label].bandwidth,
                            getWeight(maxbwfringe.label, adjVertex));
                    fringeHeap.insert(node);
                }
                else if(node.status == 1 && node.bandwidth < Math.min(nodes[maxbwfringe.label].bandwidth,
                        getWeight(maxbwfringe.label, adjVertex))){
                    node.parentLabel = maxbwfringe.label;
                    node.bandwidth = Math.min(nodes[maxbwfringe.label].bandwidth,
                            getWeight(maxbwfringe.label, adjVertex));
                }
            }
        }
        //By the end of this while loop, either all vertices are added inTree and their
        //bandwidths are the maximum bandwidths from the source or the destination got
        //added inTree and we have the required maximum bandwidth pah from source.
        //To get the path, we hold the destination node and keep following parent node
        //until we reach the source
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
        DijkstraHeap dhp = new DijkstraHeap(grp, 1, 92);
        System.out.println("Max bw: " + dhp.getMaxBw());
        System.out.println("Max bwp: " + dhp.getMaxBwPath());
    }
}
