package priorityqueue;


import java.util.ArrayList;
import java.util.Comparator;

/**
 * An implementation of a priority queue using an array-based heap
 * @param <K>
 * @param <V>
 */
public class HeapPriorityQueue<K,V> extends AbstractPriorityQueue<K,V> {
    /**
     * Primary collection of priority queue entries. Behaves like a complete binary tree
     */
    protected ArrayList<Entry<K,V>> heap = new ArrayList<>();

    /**
     * Creates an empty priority queue based on the natural ordering of its keys
     */
    public HeapPriorityQueue() { super(); }

    /**
     * Creates an empty priority queue using the given comparator to order keys
     * @param comp comparator
     */
    public HeapPriorityQueue(Comparator<K> comp) { super(comp); }

    // ----------------- protected utilities -------------------------------

    /**
     * Calculates index of parent.
     * @param j index of child
     * @return index of parent
     */
    protected int parent(int j) {
        // Uses integer division implicitly
        return (j - 1) / 2;
    }

    /**
     * Calculates index of left child
     * @param j index of parent
     * @return index of left child
     */
    protected int left(int j) { return (2 * j) + 1; }

    /**
     * Calculates index of right child
     * @param j index of parent
     * @return index of left child
     */
    protected int right(int j) { return (2 * j) + 2; }

    /**
     * Checks whether a node has a left child
     * @param j index of parent
     * @return whether the node has a left child
     */
    protected boolean hasLeft(int j) {
        // Relies on the fact that the heap is a complete binary tree
        // So every index in the array will be filled
        // The only way for a node not to have a left child is if that
        // left child's index is outside the heap's bounds
        return left(j) < heap.size();
    }

    /**
     * Checks whether the heap has a right child
     * @param j index of parent node
     * @return whether the node has a right child
     */
    protected boolean hasRight(int j) {
        return right(j) < heap.size();
    }

    /**
     * Exchange entries at index i and j of heap
     * @param i index i
     * @param j index j
     */
    protected void swap(int i, int j) {
        Entry<K, V> temp = heap.get(i);
        heap.set(i, heap.get(j));
        heap.set(j, temp);
    }

    /**
     * Swaps entries from child to parent until the child is now
     * the root node or we reach
     * a parent with a key smaller than that of the child
     * @param childIdx Index of child from where to begin upheap
     */
    protected void upheap(int childIdx) {
        int parentIdx = parent(childIdx);
        // Repeat while the childIdx is greater than that of root idx
        // and the key of child entry is less than key of parent entry
        while(childIdx > 0 && compare(heap.get(childIdx), heap.get(parentIdx)) < 0) {
            swap(childIdx, parentIdx);
            childIdx = parentIdx;
            parentIdx = parent(childIdx);
        }
    }

    /**
     * Calculate index of child with smaller key
     * @param parentIdx
     * @return index of child with minimum key
     */
    protected int minimumChild(int parentIdx) {
        // We use Integer's MAX VALUE in case the parent has no child ie is a leaf node
        int childIdx = Integer.MAX_VALUE;
        if(hasLeft(parentIdx) && hasRight(parentIdx)) {
            // Compare keys to find smaller child
            if(compare(heap.get(left(parentIdx)), heap.get(right(parentIdx))) <= 0) {
                childIdx = left(parentIdx);
            } else {
                childIdx = right(parentIdx);
            }
        }
        else if(hasLeft(parentIdx)) {
            childIdx = left(parentIdx);
        } else if(hasRight(parentIdx)) {
            childIdx = right(parentIdx);
        }
        return childIdx;
    }

    /**
     * Checks to see if a given node is a leaf node
     * @param idx index of node
     * @return whether or not a given node is a leaf node
     */
    protected boolean isLeaf(int idx) {
        return !hasLeft(idx) && !hasRight(idx);
    }

    /**
     * Swaps entries from parent to child until the parent is a leaf node
     * or we reach a child whose key is larger than that of parent
     * @param parentIdx
     */
    protected void downheap(int parentIdx) {
        int childIdx = minimumChild(parentIdx);
        // Repeat while parent is not a leaf node (does not have child)
        // or key of childIdx is larger than key of parentIdx
        while(! isLeaf(parentIdx) && compare(heap.get(parentIdx), heap.get(childIdx)) > 0) {
            swap(parentIdx, childIdx);
            parentIdx = childIdx;
            // When childIdx == Integer.MAX_VALUE, the parent node will be a leaf so the loop will break
            childIdx = minimumChild(parentIdx);
        }
    }

    /**
     * Returns the number of entries in priority queue
     * @return number of entries in priority queue
     */
    public int size() { return heap.size(); }

    /**
     * Returns (without removing) entry with minimum priority / key
     * @return entry with minimum key
     */
    public Entry<K,V> min() {
        // Entry with minimum key is the first item in the heap
        // Its also the root of the complete binary tree
        if(isEmpty()) return null;
        return heap.get(0);
    }

    /**
     * Inserts a key value pair and returns the entry created
     * @param key key that will be used for comparisons with other entries
     * @param value value
     * @return entry inserted
     * @throws IllegalArgumentException
     */
    public Entry<K,V> insert(K key, V value) throws IllegalArgumentException {
        // Ensure we use a key that allows comparisons with other keys
        checkKey(key);
        // Add new entry to the end of the complete binary tree
        Entry<K, V> entry = new PQEntry<>(key, value);
        heap.add(entry);
        // Perform upheap from the index of currently created entry
        int lastIdx = heap.size() - 1;
        upheap(lastIdx);
        return entry;
    }

    /**
     * Remove and return entry with minimum priority / key
     * @return entry with minimum priority / key
     */
    public Entry<K, V> removeMin() {
        if(isEmpty()) return null;
        // Entry with minimum key is the first item in the heap
        // It's also the root of the complete binary tree
        Entry<K, V> firstEntry = heap.get(0);
        // We remove (swap) last entry and put it at the root of the complete binary tree
        // Then we perform a downheap from the root
        int lastIdx = heap.size() - 1;
        swap(0, lastIdx);
        heap.remove(lastIdx);
        downheap(0);
        return firstEntry;
    }
}
