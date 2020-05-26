import java.util.*;

public class Kruskal {
    private Graph graph;
    private int source;
    private int destination;
    private int maxBandWidth = Integer.MAX_VALUE;
    private String mbPath;

    public Kruskal(Graph graph, int source, int destination){
        this.graph = graph;
        this.source = source;
        this.destination = destination;
        //runKruskal();
    }

    List<GraphEdge> graphEdges;
    MaxHeapEdge heap;
    DisjointSet dSet;
    List<GraphEdge> mstEdges;

    public List<GraphEdge> runKruskal(){
        heap = new MaxHeapEdge();
        graphEdges = graph.getGraphEdges();
        for(GraphEdge edge : graphEdges){
            heap.insert(edge);
        }
        dSet = new DisjointSet();
        for(int vertex : graph.adjacencyList.keySet()){
            dSet.makeSet(vertex);
        }
        //The second condition ensures we snap out of the while
        //loop as soon as source and destination become part
        //of a single tree. The tree forming is the MST and
        //we can find the mbp connecting source and destination
        //Since we are using path compression, time taken for
        //checking this conditions is just O(1)
        mstEdges = new ArrayList<GraphEdge>();
        while(!heap.isEmpty() && !sndInTree()){
            GraphEdge edge = heap.poll();
            int sourceVertex = edge.getSource().getNodeLabel();
            int destVertex = edge.getDestination().getNodeLabel();
            int root1 = dSet.findSet(sourceVertex);
            int root2 = dSet.findSet(destVertex);

            if(root1 == root2) continue;
            dSet.union(sourceVertex, destVertex);
            mstEdges.add(edge);
        }
        //By the end of this while loop, we should have all the edges we need
        //in the mstEdges array
        /*int vertex = source;
        while(vertex != destination){
            GraphNode node1 = graph.adjacencyList[vertex];
        }*/
        return mstEdges;
    }


    private void formPath(Graph maxST){
        Set<Integer> visited = new HashSet<Integer>();
        visited.add(source);
        Stack<String> path = new Stack<String>();
        path.push(Integer.toString(source));
        Stack<Integer> bandwidths = new Stack<Integer>();
        formPathRecurse(maxST, source, destination, visited, path, bandwidths);
        //By the end of this dfs, our path should contain all nodes along with weights in
        //the path and bandwidths should have all the weights.
        while(!bandwidths.isEmpty()){
            maxBandWidth = Math.min(bandwidths.pop(), maxBandWidth);
        }
        StringBuilder pathsb = new StringBuilder();
        Stack<String> reversePath = new Stack<String>();
        while(!path.isEmpty()){
            reversePath.push(path.pop());
        }
        while(!reversePath.isEmpty()){
            pathsb.append(reversePath.pop() + " -> ");
        }
        mbPath = pathsb.toString();
    }
    private void formPathRecurse(Graph maxST, int current, int target, Set<Integer> visited,
                                 Stack<String> path, Stack<Integer> bandwidths){
        if(current == target) return;
        for(GraphEdge neighborEdge : maxST.adjacencyList.get(current).getEdges().values()){
            int adjNodeLabel = neighborEdge.getOtherNode(maxST.adjacencyList.get(current)).getNodeLabel();
            int adjWeight = neighborEdge.getWeight();
            String forPath = adjNodeLabel + "(" + adjWeight + ")";
            path.push(forPath);
            bandwidths.push(adjWeight);
            visited.add(adjNodeLabel);
            formPathRecurse(maxST, adjNodeLabel, target, visited, path, bandwidths);
        }
        bandwidths.pop();
        path.pop();
        return;
    }

    private Graph makeMST(List<GraphEdge> mstEdges){
        Graph mstGraph = new Graph(mstEdges.size() + 1);
        Set<GraphNode> visited = new HashSet<GraphNode>();
        for(GraphEdge edge : mstEdges){
            GraphNode node1 = edge.getSource();
            GraphNode node2 = edge.getDestination();
            if(visited.contains(node1) || visited.contains(node2))
                continue;
            int weight = edge.getWeight();
            mstGraph.addEdge(node1, node2, weight);
            mstGraph.adjacencyList.put(node1.getNodeLabel(), node2);
            mstGraph.adjacencyList.put(node2.getNodeLabel(), node1);
        }
        return mstGraph;
    }

    private boolean sndInTree(){
        int srcSet = dSet.findSet(source);
        int destSet = dSet.findSet(destination);
        return srcSet == destSet;
    }

    public static void main(String[] args){
        Graph testgraph = new SparseGraph(10);
        Kruskal gkrus = new Kruskal(testgraph, 5, 6);
        List<GraphEdge> spanningTreeEdges = gkrus.runKruskal();
        Graph mst = gkrus.makeMST(spanningTreeEdges);
        System.out.println(mst.adjacencyList.size());
        System.out.println("Total degree: " + mst.getTotalDegree() + "  Average degree: " +
                (mst.getTotalDegree() / mst.getNVertices()) + "  Total Edges = " + mst.getNEdges() +
                " graphEdges set size: " + mst.getGraphEdges().size());
        for(int i : mst.adjacencyList.keySet()){
            System.out.print(i + ": ");
            GraphNode currentNode = mst.adjacencyList.get(i);
            Map<Integer, GraphEdge> connectingEdges = currentNode.getEdges();
            for(int eLabel : connectingEdges.keySet()){
                GraphEdge cEdge = connectingEdges.get(eLabel);
                GraphNode otherNode = cEdge.getOtherNode(currentNode);
                System.out.print(otherNode.getNodeLabel() + "(" + cEdge.getWeight() + ") ");
            }
            System.out.print("\n");
        }
        //gkrus.formPath(mst);
        //System.out.println(gkrus.mbPath);
        //System.out.println(gkrus.maxBandWidth);
    }

}
