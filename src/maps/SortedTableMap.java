package maps;


import util.Entry;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Concrete implementation of the SortedTableMap that supports the sorted map ADT
 * Includes the findIndex utility method that uses the recursive binary search algorithm to return
 * the index of the leftmost entry in the search range having key greater than or equal to k
 *
 *
 * Running time
 * size: O(1)
 * get : O(logn)
 * put : O(n); O(log n) if map has entry with given key
 * remove : O(n)
 * firstEntry, lastEntry: O(1)
 * ceilingEntry, floorEntry, lowerEntry, higherEntry : O(log n)
 * subMap : O(s + log n)
 * entrySet, keySet, values : O(n)
 *
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
     * @param high higher index bound
     */
    private int findIndex(K key, int low, int high) {
        if (high < low)
            // by convention, no entry qualifies
            // Occurs when high + 1 == low
            return high + 1; // or low
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

    /**
     * Support for snapshot iterators for entrySet() and subMap()
     * Returns an iterable of entries beginning with entry at startIndex of table upto entry with largest key less than stop key
     * @param startIndex index of table from which to start the iterable
     * @param stop smallest key outside snapshot range
     * @return iterable of entries from entry at startIndex of table to entry with largest key less than stop key
     */
    private Iterable<Entry<K,V>> snapshot(int startIndex, K stop) {
        List<Entry<K,V>> buffer = new ArrayList<>();
        int idx = startIndex;
        while(idx < table.size() &&
                (stop == null || compare(stop, table.get(idx)) > 0)) {
            buffer.add(table.get(idx++));
        }
        return buffer;
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

    // - map functions -

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
            return null;
        return table.remove(idx).getValue();
    }

    // - end of map functions -

    // - Methods specific to sorted map -
    /**
     * Returns the entry having the least key or null if map is empty
     * @return entry with least key or null if map is empty
     */
    public Entry<K,V> firstEntry() {
        return safeEntry(0);
    }

    /**
     * Returns entry having the greatest key or null if map is entry
     * @return entry with greatest key or null if map is empty
     */
    public Entry<K,V> lastEntry() {
        return safeEntry(table.size() - 1);
    }

    /**
     * Returns entry with least key greater than or equal to given key if any
     * @param key key
     * @return entry with least key greater than or equal to given key if any
     */
    public Entry<K,V> ceilingEntry(K key) {
        return safeEntry(findIndex(key));
    }

    /**
     * Returns the entry with greatest key less or equal to given key if any
     *
     * @param key
     * @return entry with greatest key less or equal to given key if any
     */
    public Entry<K,V> floorEntry(K key) {
        int ceilIdx = findIndex(key);

        // If we do not get a match, we take the entry one index lower
        if(ceilIdx == table.size() || !table.get(ceilIdx).getKey().equals(key))
            ceilIdx --;

        return safeEntry(ceilIdx);
    }

    /**
     * Returns the entry with greatest key strictly less than given key (if any)
     * @param key
     * @return entry with greatest key strictly less that given key if any
     */
    public Entry<K,V> lowerEntry(K key) {
        // go strictly below the ceiling entry
        return safeEntry(findIndex(key) - 1);
    }

    /**
     * Return the entry with smallest key strictly greater than given key
     *
     * Running time: O(log n)
     *
     * @param key
     * @return entry with smallest key strictly greater than given key
     */
    public Entry<K,V> higherEntry(K key) {
        int ceilIdx = findIndex(key);
        if(ceilIdx < size() && table.get(ceilIdx).getKey().equals(key))
            ceilIdx ++;
        return safeEntry(ceilIdx);
    }

    /**
     * Returns an iterable of all the entries in the sorted map
     * @return an iterable of all entries n the sorted map
     */
    public Iterable<Entry<K,V>> entrySet() {return snapshot(0, null);}

    /**
     * Returns a snapshot of entries in sorted order from the fromKey to and excluding the toKey
     * @return A sorted iterable of entrie from a given fromKey to the given toKey
     */
    public Iterable<Entry<K,V>> subMap(K fromKey, K toKey) {
        return snapshot(findIndex(fromKey), toKey);
    }


    // - end of methods specific to sorted map -
}
