import java.util.Arrays;

public class MaxHeap {
    private int size;
    private int capacity;
    private TreeNode[] elements;
    private Graph graph;
    //private TreeNode[] nodes;

    public MaxHeap(Graph graph){
        size = 0;
        capacity = 10;
        this.graph = graph;
        elements = new TreeNode[capacity];
    }

    public MaxHeap(int capacity){
        this.capacity = capacity;
        size = 0;
        elements = new TreeNode[this.capacity];
    }

    public int size(){
        return size;
    }

    private int getLeftChildIndex(int index){
        return ((index * 2) + 1);
    }
    private int getRightChildIndex(int index){
        return ((index * 2) + 2);
    }
    private int getParentIndex(int index){
        return ((index - 1) / 2);
    }

    private TreeNode getLeftChild(int index){
        return elements[getLeftChildIndex(index)];
    }
    private TreeNode getRightChild(int index){
        return elements[getRightChildIndex(index)];
    }
    private TreeNode getParent(int index){
        return elements[getParentIndex(index)];
    }

    private boolean hasLeftChild(int index){
        return getLeftChildIndex(index) < size;
    }
    private boolean hasRightChild(int index){
        return getRightChildIndex(index) < size;
    }
    private boolean hasParent(int index){
        return getParentIndex(index) >= 0;
    }

    private void ensureCapacity(){
        if(size == capacity){
            capacity *= 2;
            elements = Arrays.copyOf(elements, capacity);
        }
    }

    public boolean isEmpty(){
        return size == 0;
    }

    private void swapElements(int index1, int index2){
        TreeNode temp = elements[index1];
        elements[index1] = elements[index2];
        elements[index2] = temp;
    }

    public TreeNode maximum(){
        return elements[0];
    }

    public TreeNode poll(){
        TreeNode max = maximum();
        delete();
        return max;
    }

    public void insert(TreeNode element){
        ++size;
        ensureCapacity();
        elements[size - 1] = element;
        maxHeapifyUp();
    }

    public void delete(){
        swapElements(0, size - 1);
        elements[--size] = null;
        maxHeapifyDown();
    }

    private void maxHeapifyUp(){
        int index = size - 1;
        while(hasParent(index) && (getWeight(getParent(index)) < getWeight(elements[index]))){
            swapElements(index, getParentIndex(index));
            index = getParentIndex(index);
        }
    }

    private void maxHeapifyDown(){
        int index = 0;
        while(hasLeftChild(index)){
            int swapIndex = getLeftChildIndex(index);
            if(hasRightChild(index) && getWeight(getRightChild(index)) >
                    getWeight(getLeftChild(index))){
                swapIndex = getRightChildIndex(index);
            }
            if(getWeight(elements[index]) > getWeight(elements[swapIndex])) break;
            else swapElements(index, swapIndex);
            index = swapIndex;
        }
    }

    int getWeight(TreeNode element){
        int nodeLabel1 = element.parentLabel;
        int nodeLabel2 = element.label;
        return graph.adjacencyList[nodeLabel1].getEdges().get(nodeLabel2).getWeight();
    }

    /*public static void main(String args[]){
        MaxHeap mhp = new MaxHeap(new SparseGraph(10));
        TreeNode[] nodes = new TreeNode[10];
        for(int i = 1; i <= 7; ++i){
            TreeNode node = new TreeNode(i);
            node.parentLabel = i - 1;
            nodes[i - 1] = node;
        }
        mhp.insert(nodes[0]);
        mhp.insert(nodes[1]);
        mhp.insert(nodes[2]);
        mhp.insert(nodes[3]);
        mhp.insert(nodes[4]);
        mhp.insert(nodes[5]);
        System.out.println(mhp.size());
        System.out.println(mhp.poll());
        System.out.println(mhp.size());
        System.out.println(mhp.maximum());
        System.out.println(mhp.size());
        mhp.insert(nodes[7]);
        System.out.println(mhp.maximum());
        System.out.println(mhp.size());
    }*/
}
