package priorityqueue;

import lists.position.LinkedPositionalList;
import lists.position.Position;
import lists.position.PositionalList;

import java.util.Comparator;

/**
 * An implementation of a priority with an unsorted list
 * @param <K> key
 * @param <V> value
 */
public class UnsortedPriorityQueue<K,V> {//extends AbstractPriorityQueue<K,V> {
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
    //public UnsortedPriorityQueue(Comparator<K> comp) { super(comp); }

    /**
     * Returns the position of an entry having minimum key
     * @return Position of entry with minimum key
     */
//    private Position<Entry<K,V>> findMin() {
//       Position<Entry<K,V>> small = list.first();
//       for(Position<Entry<K,V> walk: list.positions()) {
//
//       }
//    }
}
