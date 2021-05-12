package priorityqueue;

import lists.position.LinkedPositionalList;
import util.Position;
import lists.position.PositionalList;

import java.util.Comparator;

/**
 * An implementation of a priority with an unsorted list
 *
 * Performance:
 * ---------------------------------------------------------------
 * | Method                          | Running Time              |
 * ---------------------------------------------------------------
 * | size()                          | O(1)                      |
 * | isEmpty()                       | O(1)                      |
 * | insert()                        | O(1)                      |
 * | min                             | O(n)                      |
 * | removeMin()                     | O(n)                      |
 * ---------------------------------------------------------------
 *
 * @param <K> key
 * @param <V> value
 */
public class UnsortedPriorityQueue<K,V> extends AbstractPriorityQueue<K,V> {
    /**
     * primary collection of priority queue entries
     */
    private PositionalList<Entry<K,V>> list = new LinkedPositionalList<>();

    /**
     *  Creates an empty priority queue based on the natural ordering of its keys
     */
    public UnsortedPriorityQueue() { super(); }

    /**
     * Creates an empty priority queue using the given comparator to order keys
     * @param comp comparator that will be used to order keys
     */
    public UnsortedPriorityQueue(Comparator<K> comp) { super(comp); }

    /**
     * Returns the position of an entry having minimum key
     * Only called when the list is not empty
     * @return Position of entry with minimum key
     */
    private Position<Entry<K,V>> findMin() {
       Position<Entry<K,V>> small = list.first();
       for(Position<Entry<K,V>> walk: list.positions()) {
           // check if walk has a smaller key than that of the current small
           if(compare(walk.getElement(), small.getElement()) < 0) {
               small = walk;
           }
       }
       return small;
    }

    /**
     * Insert a key value pair and return the entry created
     * @param key
     * @param value
     * @return
     * @throws IllegalArgumentException
     */
    public Entry<K, V> insert(K key, V value) throws IllegalArgumentException {
        // Auxiliary key-checking method (could throw an exception)
        checkKey(key);
        Entry<K, V> entry = new PQEntry<>(key, value);
        list.addLast(entry);
        return entry;
    }

    /**
     * Returns without removing an entry with minimum key
     * @return entry with minimum key
     */
    public Entry<K,V> min() {
        if(list.isEmpty()) return null;
        return findMin().getElement();
    }

    /**
     * Removes and returns an entry with minimum key
     * @return
     */
    public Entry<K, V> removeMin() {
        if(list.isEmpty()) return null;
        return list.remove(findMin());
    }

    /**
     * Returns the number of items in the priority queue
     * @return number of items in the priority queue
     */
    public int size() { return list.size(); }

}
