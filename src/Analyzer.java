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
                //~~~~~~~~~~~~~Call Dijkstra without Heap - sparseG, sdPair as args
                endTime = System.nanoTime();
                System.out.println("Dijkstra without Heap on sparse graph " +
                        (pair + 1) + ": " + (endTime - startTime) + " ns");
                startTime = System.nanoTime();
                //~~~~~~~~~~~~~Call Dijkstra without Heap - denseG, sdPair as args
                endTime = System.nanoTime();
                System.out.println("Dijkstra without Heap on dense graph " +
                        (pair + 1) + ": " + (endTime - startTime) + " ns");

                startTime = System.nanoTime();
                //~~~~~~~~~~~~~Call Dijkstra with Heap = sparseG, sdPair as args
                endTime = System.nanoTime();
                System.out.println("Dijkstra with Heap on sparse graph " +
                        (pair + 1) + ": " + (endTime - startTime) + " ns");

                startTime = System.nanoTime();
                //~~~~~~~~~~~~~Call Dijkstra with Heap = denseG, sdPair as args
                endTime = System.nanoTime();
                System.out.println("Dijkstra with Heap on dense graph " +
                        (pair + 1) + ": " + (endTime - startTime) + " ns");

                startTime = System.nanoTime();
                //~~~~~~~~~~~~~Call Kruskal with Heap = sparseG, sdPair as args
                endTime = System.nanoTime();
                System.out.println("Kruskal with Heap on sparse graph " +
                        (pair + 1) + ": " + (endTime - startTime) + " ns");

                startTime = System.nanoTime();
                //~~~~~~~~~~~~~Call Kruskal with Heap = denseG, sdPair as args
                endTime = System.nanoTime();
                System.out.println("Kruskal with Heap on dense graph " +
                        (pair + 1) + ": " + (endTime - startTime) + " ns");

                System.out.println();
            }
            System.out.println();
        }


    }
}
