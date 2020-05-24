import java.util.Arrays;

public class MaxHeap {
    private int size;
    private int capacity;
    private int[] elements;

    public MaxHeap(){
        size = 0;
        capacity = 10;
        elements = new int[capacity];
    }

    public MaxHeap(int capacity){
        this.capacity = capacity;
        size = 0;
        elements = new int[this.capacity];
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

    private int getLeftChild(int index){
        return elements[getLeftChildIndex(index)];
    }
    private int getRightChild(int index){
        return elements[getRightChildIndex(index)];
    }
    private int getParent(int index){
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

    private void swapElements(int index1, int index2){
        int temp = elements[index1];
        elements[index1] = elements[index2];
        elements[index2] = temp;
    }

    public int maximum(){
        return elements[0];
    }

    public int poll(){
        int max = maximum();
        delete();
        return max;
    }

    public void insert(int element){
        ++size;
        ensureCapacity();
        elements[size - 1] = element;
        maxHeapifyUp();
    }

    public void delete(){
        swapElements(0, size - 1);
        elements[--size] = 0;
        maxHeapifyDown();
    }

    private void maxHeapifyUp(){
        int index = size - 1;
        while(hasParent(index) && getParent(index) < elements[index]){
            swapElements(index, getParentIndex(index));
            index = getParentIndex(index);
        }
    }

    private void maxHeapifyDown(){
        int index = 0;
        while(hasLeftChild(index)){
            int swapIndex = getLeftChildIndex(index);
            if(hasRightChild(index) && getRightChild(index) > getLeftChildIndex(index)){
                swapIndex = getRightChildIndex(index);
            }
            if(elements[index] > elements[swapIndex]) break;
            else swapElements(index, swapIndex);
            index = swapIndex;
        }
    }

    public static void main(String args[]){
        MaxHeap mhp = new MaxHeap();
        mhp.insert(1);
        mhp.insert(2);
        mhp.insert(3);
        mhp.insert(4);
        mhp.insert(5);
        mhp.insert(6);
        System.out.println(mhp.size());
        System.out.println(mhp.poll());
        System.out.println(mhp.size());
        System.out.println(mhp.maximum());
        System.out.println(mhp.size());
        mhp.insert(100);
        System.out.println(mhp.maximum());
        System.out.println(mhp.size());
    }
}
