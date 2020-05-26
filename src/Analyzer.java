public class Analyzer {

    //Test Settings
    private static int nGPairs = 5;
    private static int nVertices = 5000;
    private static int nVPairs = 5;
    public static void main(String[] args){

        for(int pair = 0; pair < nGPairs; ++pair){
            //Create a pair of graphs - one sparse, one dense
            Graph sparseG = new SparseGraph(nVertices);
            Graph denseG = new DenseGraph(nVertices);

            //Create source-destination pairs
            //Note: We use a single source-destination pair for sparse
            //and dense graphs
            int[][] sdPairs = new int[nVPairs][2];
            for(int hubs = 0; hubs < nVPairs; ++hubs){
                int source = (int) Math.floor(Math.random() * nVertices);
                int destination = (int) Math.floor(Math.random() * nVertices);
                sdPairs[hubs] = new int[]{source, destination};
            }

            System.out.println("GRAPH SET: " + (pair + 1));
            System.out.println();

            for(int sdi = 0; sdi < sdPairs.length; ++sdi){
                int source = sdPairs[sdi][0];
                int destination = sdPairs[sdi][1];
                System.out.println("PAIR " + (sdi + 1) + ": Source Vertex - "
                        + source + ", Destination Vertex - " + destination);

                long startTime = 0; long endTime = 0;

                startTime = System.nanoTime();
                Dijkstra dijkstraSparse = new Dijkstra(sparseG, source, destination);
                endTime = System.nanoTime();
                System.out.println("Dijkstra on sparse graph " + (pair + 1) + ":");
                System.out.println("Maximum bandwidth: " + dijkstraSparse.getMaxBw());
                System.out.println("Maximum bandwidth path: " + dijkstraSparse.getMaxBwPath());
                System.out.println("Execution Time: " + (endTime - startTime) + " ns");

                startTime = System.nanoTime();
                Dijkstra dijkstraDense = new Dijkstra(sparseG, source, destination);
                endTime = System.nanoTime();
                System.out.println("Dijkstra on dense graph " + (pair + 1) + ":");
                System.out.println("Maximum bandwidth: " + dijkstraDense.getMaxBw());
                System.out.println("Maximum bandwidth path: " + dijkstraDense.getMaxBwPath());
                System.out.println("Execution Time: " + (endTime - startTime) + " ns");

                startTime = System.nanoTime();
                DijkstraHeap dijkstraHeapSparse = new DijkstraHeap(sparseG, source, destination);
                endTime = System.nanoTime();
                System.out.println("Dijkstra with Heap on sparse graph " + (pair + 1) + ":");
                System.out.println("Maximum bandwidth: " + dijkstraHeapSparse.getMaxBw());
                System.out.println("Maximum bandwidth path: " + dijkstraHeapSparse.getMaxBwPath());
                System.out.println("Execution Time: " + (endTime - startTime) + " ns");

                startTime = System.nanoTime();
                DijkstraHeap dijkstraHeapDense = new DijkstraHeap(sparseG, source, destination);
                endTime = System.nanoTime();
                System.out.println("Dijkstra with Heap on dense graph " + (pair + 1) + ":");
                System.out.println("Maximum bandwidth: " + dijkstraHeapDense.getMaxBw());
                System.out.println("Maximum bandwidth path: " + dijkstraHeapDense.getMaxBwPath());
                System.out.println("Execution Time: " + (endTime - startTime) + " ns");

                /*
                startTime = System.nanoTime();
                //DijkstraHeap kruskalSparse = new DijkstraHeap(sparseG, source, destination);
                endTime = System.nanoTime();
                System.out.println("Kruskal on sparse graph " + (pair + 1) + ":");
                System.out.println("Maximum bandwidth: " + kruskalSparse.getMaxBw());
                System.out.println("Maximum bandwidth path: " + kruskalSparse.getMaxBwPath());
                System.out.println("Execution Time: " + (endTime - startTime) + " ns");

                startTime = System.nanoTime();
                //DijkstraHeap kruskalDense = new DijkstraHeap(sparseG, source, destination);
                endTime = System.nanoTime();
                System.out.println("Kruskal on dense graph " + (pair + 1) + ":");
                System.out.println("Maximum bandwidth: " + kruskalDense.getMaxBw());
                System.out.println("Maximum bandwidth path: " + kruskalDense.getMaxBwPath());
                System.out.println("Execution Time: " + (endTime - startTime) + " ns");
                */

                System.out.println();
            }
            System.out.println();
        }


    }
}
