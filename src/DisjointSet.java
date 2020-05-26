import java.util.HashMap;
import java.util.Map;

public class DisjointSet {

    class DSNode{
        int data;
        DSNode parent;
        int rank;
    }

    private Map<Integer, DSNode> map = new HashMap<Integer, DSNode>();

    public void makeSet(int data){
        DSNode node = new DSNode();
        node.data = data;
        node.parent = node;
        node.rank = 0;
        map.put(data, node);
    }

    //does path compression and return the representative node of set
    public DSNode findSet(DSNode node){
        DSNode parentNode = node.parent;
        if(parentNode == node)
            return node;
        node.parent = findSet(parentNode);
        return node.parent;
    }

    public int findSet(int element){
        return map.get(element).parent.data;
    }

    public void union(int data1, int data2){
        DSNode node1 = map.get(data1);
        DSNode node2 = map.get(data2);

        DSNode parent1 = findSet(node1);
        DSNode parent2 = findSet(node2);
        //Same set condition
        if(parent1.data == parent2.data) return;

        if(parent1.rank >= parent2.rank){
            //increase rank if both ranks are equal
            if(parent1.rank == parent2.rank) ++parent1.rank;
            parent2.parent = parent1;
        }
        else{
            parent1.parent = parent2;
        }
    }

    public static void main(String args[]){
        DisjointSet ds = new DisjointSet();
        ds.makeSet(1);
        ds.makeSet(2);
        ds.makeSet(3);
        ds.makeSet(4);
        ds.makeSet(5);
        ds.makeSet(6);
        ds.makeSet(7);
        ds.makeSet(8);

        ds.union(1, 2);
        ds.union(2, 3);
        ds.union(4, 5);
        ds.union(6, 7);
        ds.union(5, 6);
        ds.union(3, 7);

        System.out.println(ds.findSet(ds.map.get(1)).data);
        System.out.println(ds.findSet(ds.map.get(2)).data);
        System.out.println(ds.findSet(ds.map.get(3)).data);
        System.out.println(ds.findSet(ds.map.get(4)).data);
        System.out.println(ds.findSet(ds.map.get(5)).data);
        System.out.println(ds.findSet(ds.map.get(6)).data);
        System.out.println(ds.findSet(ds.map.get(7)).data);
        System.out.println(ds.findSet(ds.map.get(8)).data);
    }

}
