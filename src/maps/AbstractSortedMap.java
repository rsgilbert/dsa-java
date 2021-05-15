package maps;

import priorityqueue.DefaultComparator;
import util.Entry;

import java.util.Comparator;

/**
 * AbstractSortedMap interface
 */
public abstract class AbstractSortedMap<K,V> extends AbstractMap<K,V> implements SortedMap<K,V>
{
    /**
     * Comparator defining the ordering of keys in the sorted map
     */
    private final Comparator<K> comp;

    /**
     * Constructs the sorted map using a given comparator
     * @param c comparator
     */
    public AbstractSortedMap(Comparator<K> c) { comp = c; }

    /**
     * Constructs the sorted map using the default comparator
     */
    public AbstractSortedMap() {
        comp = new DefaultComparator<>();
    }

    /**
     * Utility function to compare two entries based on the comparator of the sorted map class
     * @param a entry a
     * @param b entry b
     * @return an integer representing the comparison result
     */
    public int compare(Entry<K,V> a, Entry<K,V> b) {
        return comp.compare(a.getKey(), b.getKey());
    }


    /**
     * Utility function to compare a key and an entry based on the comparator of the sorted map class
     * @param k key
     * @param e entry
     * @return an integer representing the comparison result
     */
    public int compare(K k, Entry<K,V> e) {
        return comp.compare(k, e.getKey());
    }

    /**
     * Utility for comparing two keys based on the comparator of the sorted map class
     * @param k1 key1
     * @param k2 key2
     * @return an integer representing the comparison result
     */
    public int compare(K k1, K k2) {
        return comp.compare(k1, k2);
    }
}
