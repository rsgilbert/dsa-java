package maps;


import com.sun.xml.internal.bind.v2.TODO;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * Concrete implementation of the SortedTableMap that supports the sorted map ADT
 * Includes the findIndex utility method that uses the recursive binary search algorithm to return
 * the index of the leftmost entry in the search range having key greater than or equal to k
 */
public class SortedTableMap<K, V> extends AbstractSortedMap<K, V> {
    private ArrayList<MapEntry<K, V>> table = new ArrayList<>();

    // -- constructors --

    /**
     * Constructs SortedTableMap using the default comparator
     */
    public SortedTableMap() {
        super();
    }

    /**
     * Constructs SortedTableMap using a given comparator
     *
     * @param comp comparator that will be used to compare keys
     */
    public SortedTableMap(Comparator<K> comp) {
        super(comp);
    }
    // -- end of constructors --

    // -- utility functions --

    /**
     * Uses binary search to find index
     *
     * @param key key
     * @param low lower index bound
     * @return Smallest index for range table[low..high] inclusive storing an entry with a key
     * greater than or equal to k (or else index high+1, by convention)
     * @para high higher index bound
     */
    private int findIndex(K key, int low, int high) {
        if (high < low)
            // by convention, no entry qualifies
            // most likely high + 1 == low
            return high + 1;
        int mid = (low + high) / 2;
        int comp = compare(key, table.get(mid));
        if (comp == 0)
            return mid;
        else if (comp < 0)
            // We can find a smaller index with k >= key
            return findIndex(key, low, mid - 1);
        else
            // index has k < key
            return findIndex(key, mid + 1, high);
    }

    /**
     * A version of findIndex that searches the entire table
     */
    private int findIndex(K k) {
        return findIndex(k, 0, size() - 1);
    }

    /**
     * Returns the entry at index idx, else null if idx is out of bounds
     * @param idx index
     * @return entry at idx if idx is within bounds
     */
    private Entry<K,V> safeEntry(int idx) {
        if(idx < 0 || idx >= table.size()) return null;
        return table.get(idx);
    }

    // -- end of utility functions --

    /**
     * Returns the number of entries in the map
     */
    @Override
    public int size() {
        return table.size();
    }

    /**
     * Returns the value associated with the specified key
     *
     * @param k key
     * @return value associated with k
     */
    public V get(K k) {
        int idx = findIndex(k);
        if (idx == size() || compare(k, table.get(idx)) != 0)
            // No match
            return null;
        return table.get(idx).getValue();
    }

    /**
     * Associate a given value with a given key returning any overridden value
     *
     * @param k key
     * @param v value
     * @return overridden value
     */
    public V put(K k, V v) {
        int idx = findIndex(k);
        if (idx == size() || compare(k, table.get(idx)) != 0) {
            table.add(idx, new MapEntry<>(k, v));
            return null;
        }
        // Found match, override value
        return table.get(idx).setValue(v);
    }

    /**
     * Removes the entry having key k (if any) and returns its associated value
     * @param key key
     * @return v associated value if any
     */
    public V remove(K key) {
        int idx = findIndex(key);
        if (idx == size() || compare(key, table.get(idx)) != 0)
            return table.remove(idx).getValue();
        return null;
    }

    // TODO continue from firstEntry method
}
