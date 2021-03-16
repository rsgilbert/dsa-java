package priorityqueue;

import lists.position.LinkedPositionalList;
import lists.position.Position;
import lists.position.PositionalList;

import java.util.Comparator;

/**
 * An implementation of a priority with a sorted list
 *
 * Performance:
 * ---------------------------------------------------------------
 * | Method                          | Running Time              |
 * ---------------------------------------------------------------
 * | size()                          | O(1)                      |
 * | isEmpty()                       | O(1)                      |
 * | insert()                        | O(n)                      |
 * | min                             | O(1)                      |
 * | removeMin()                     | O(1)                      |
 * ---------------------------------------------------------------
 *
 * @param <K> key
 * @param <V> value
 */
public class SortedPriorityQueue<K,V> extends AbstractPriorityQueue<K,V> {
    /**
     * Primary collection of priority queue entries
     */
    private PositionalList<Entry<K, V>> list = new LinkedPositionalList<>();

    /**
     * Creates an empty priority queue based on natural ordering of its keys
     */
    public SortedPriorityQueue() { super(); }

    /**
     * Creates an empty priority queue given a comparator to order keys
     */
    public SortedPriorityQueue(Comparator<K> comp) { super(comp); }

    /**
     * Inserts a key value pair and returns the entry created
     * @param key
     * @param value
     * @return entry created
     * @throws IllegalArgumentException
     */
    public Entry<K, V> insert(K key, V value) throws IllegalArgumentException {
        // auxiliary key-checking method. Could throw an exception
        checkKey(key);
        Entry<K, V> entry = new PQEntry<>(key, value);
        Position<Entry<K,V>> walk = list.last();
        // walk backward looking for smaller key
        // repeat while entry is smaller than the walk's element
        while(walk != null && compare(entry, walk.getElement()) < 0) {
            walk = list.before(walk);
        }
        // we insert the entry right after the smallest position we could find
        if(walk == null) {
            list.addFirst(entry);
        }
        else {
            list.addAfter(walk, entry);;
        }
        return entry;
    }

    /**
     * Returns but does not remove entry with smallest key
     * @return entry with smallest key. Null if priority queue is empty
     */
    public Entry<K,V> min() {
        if(isEmpty()) return null;
        return list.first().getElement();
    }

    /**
     * Removes and returns entry with smallest key
     * @return entry with smallest key which also has been removed
     */
    public Entry<K,V> removeMin() {
        if(isEmpty()) return null;
        return list.remove(list.first());
    }

    /**
     * Returns the number of elements in the priority queue
     * @return number of elements in the priority queue
     */
    public int size() { return list.size(); }
}
