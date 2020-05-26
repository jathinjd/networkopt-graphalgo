import java.util.Arrays;

public class MaxHeapEdge {

    private int size;
    private int capacity;
    private GraphEdge[] elements;
    //private TreeNode[] nodes;

    public MaxHeapEdge(){
        size = 0;
        capacity = 10;
        elements = new GraphEdge[capacity];
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

    private GraphEdge getLeftChild(int index){
        return elements[getLeftChildIndex(index)];
    }
    private GraphEdge getRightChild(int index){
        return elements[getRightChildIndex(index)];
    }
    private GraphEdge getParent(int index){
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
        GraphEdge temp = elements[index1];
        elements[index1] = elements[index2];
        elements[index2] = temp;
    }

    public GraphEdge maximum(){
        return elements[0];
    }

    public GraphEdge poll(){
        GraphEdge max = maximum();
        delete();
        return max;
    }

    public void insert(GraphEdge element){
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
        while(hasParent(index) && (getParent(index).getWeight() < elements[index].getWeight())){
            swapElements(index, getParentIndex(index));
            index = getParentIndex(index);
        }
    }

    private void maxHeapifyDown(){
        int index = 0;
        while(hasLeftChild(index)){
            int swapIndex = getLeftChildIndex(index);
            if(hasRightChild(index) && getRightChild(index).getWeight() >
                    getLeftChild(index).getWeight()){
                swapIndex = getRightChildIndex(index);
            }
            if(elements[index].getWeight() > elements[swapIndex].getWeight()) break;
            else swapElements(index, swapIndex);
            index = swapIndex;
        }
    }
}
