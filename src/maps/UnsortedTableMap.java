package maps;


import util.Entry;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * A very simple concrete implementation of the map ADT that relies
 * on storing key-value pairs in arbitrary order within a Java ArrayList
 *
 *
 * Running time
 * get(k)     : O(n)
 * put(k, v)  : O(n)
 * remove(k)  : O(n)
 * entrySet() : O(n)
 * size()     : O(1)
 *
 * @param <K> key
 * @param <V> value
 */
public class UnsortedTableMap<K, V> extends AbstractMap<K, V> {
    /**
     * Underlying storage for the map of entries
     */
    private ArrayList<MapEntry<K, V>> table = new ArrayList<>();

    /**
     * Constructs an initially empty map
     */
    public UnsortedTableMap() {
    }

    // -- private utility --

    /**
     * Returns the index of an entry with equal key, or -1 if no entry is found
     *
     * @param key key
     * @return index of an entry with equal key or -1 if no entry is found
     */
    private int findIndex(K key) {
        int n = table.size();
        for (int i = 0; i < n; i++)
            if (table.get(i).getKey().equals(key)) return i;
        return -1;
    }
    // -- end of private utility --

    /**
     * Returns the number of entries in the map
     */
    @Override
    public int size() {
        return table.size();
    }

    /**
     * Returns the entry associated with a given key, null if key is not in map
     *
     * @param k key
     * @return value
     */
    @Override
    public V get(K k) {
        int idx = findIndex(k);
        if (idx == -1) return null;
        return table.get(idx).getValue();
    }

    /**
     * Puts a new entry into the map or updates the value of an existing entry
     *
     * @param k key
     * @param v value
     * @return replaced value if updating or null if adding a new entry
     */
    @Override
    public V put(K k, V v) {
        int idx = findIndex(k);
        if (idx == -1) {
            table.add(new MapEntry<>(k, v));
            return null;
        } else return table.get(idx).setValue(v);
    }

    /**
     * Remove entry with specified key and return value of removed entry
     *
     * @param key key
     * @return value of removed entry
     */
    @Override
    public V remove(K key) {
        int idx = findIndex(key);
        if (idx == -1) return null;
        MapEntry<K, V> old = table.get(idx);
        int lastIdx = table.size() - 1;
        table.set(idx, table.get(lastIdx));
        table.remove(lastIdx);
        return old.getValue();
    }

    // -- Support for entrySet() method --
    private class EntryIterator implements Iterator<Entry<K,V>> {
        private int idx = 0;
        @Override
        public boolean hasNext() { return idx < table.size(); }
        @Override
        public MapEntry<K,V> next() {
            if (!hasNext()) throw new NoSuchElementException("next");
            return table.get(idx++);
        }
    }
    private class EntryIterable implements Iterable<Entry<K,V>> {
        @Override
        public Iterator<Entry<K,V>> iterator() { return new EntryIterator(); }
    }
    // -- End of support for entrySet() --

    /**
     * Returns an iterable of entries
     * @return iterable of entries
     */
    @Override
    public Iterable<Entry<K,V>> entrySet() { return new EntryIterable(); }


}
