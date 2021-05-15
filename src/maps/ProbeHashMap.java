package maps;


import util.Entry;

import java.util.ArrayList;

/**
 * An implementation of ProbeHashMap using open addressing with linear probing
 * Meant for devices with small memory, hence optimized for space
 * @param <K> key
 * @param <V> value
 */
public class ProbeHashMap<K,V> extends AbstractHashMap<K,V> {
    // A fixed array of entries
    // All initially null
    private MapEntry<K,V>[] table;
    private final MapEntry<K,V> DEFUNCT = new MapEntry<>(null, null);

    // -- constructors --
    public ProbeHashMap(){super(); }
    public ProbeHashMap(int cap) { super(cap); }
    public ProbeHashMap(int cap, int p) { super(cap, p); }
    // -- end of constructors --

    /**
     * Creates an empty table having length equal to current capacity;
     */
    protected void createTable() {
        table = (MapEntry<K,V>[]) new MapEntry[capacity];
    }

    // -- private utility functions --
    /**
     * Returns true if location is either empty or the defunct entry
     */
    private boolean isAvailable(int idx) {
        return table[idx] == null || table[idx] == DEFUNCT;
    }

    /**
     * Returns index with key k, or -(a + 1) such that k could be added at index a
     */
    private int findSlot(int h, K k) {
        int avail = -1;
        int idx = h;
        do {
            if(isAvailable(idx)) {
                if(avail == -1)
                    // We've encountered our first available slot
                    avail = idx;
                if(table[idx] == null)
                    // We've found an empty slot
                    break;
            } else if(table[idx].getKey().equals(k))
                // slot already has an Entry with this key
                return idx;
            idx = (idx + 1) % capacity;
        }
        // Until we've visited all slots
        // We assume table always has some empty (null) slots because
        // we always resize it when it approaches a certain size to capacity ratio in AbstractHashMap
        while(idx != h);
        // At this point avail can never be -1
        // Search has failed so we return available index for insertion wrapped according to our logic
        return -(avail + 1);
    }

    /**
     * Calculate corresponding index based on our convention for representing idx belonging to defunct slots or new slots in findSlot method
     * @param idx index
     * @return corresponding table index
     */
    private int calculateIdx(int idx) {
        return -(idx + 1);
    }
    // -- end of private utilities --

    /**
     * Returns value associated with key k in bucket with hash value h or null
     * @param h hash value
     * @param k key
     * @return value, null if not found
     */
    protected V bucketGet(int h, K k) {
        int idx = findSlot(h, k);
        if(idx >= 0)
            return table[idx].getValue();
        return null;
    }

    /**
     * Associate key k with value v in bucket with hash value h, returns old value
     * @param h hash value
     * @param k key
     * @param v value
     * @return old value, null if new entry is created
     */
    protected V bucketPut(int h, K k, V v) {
        int idx = findSlot(h, k);
        if (idx >= 0) {
            return table[idx].setValue(v);
        }
        int newIdx = calculateIdx(idx);
        table[newIdx] = new MapEntry<>(k, v);
        n++;
        return null;
    }

    /**
     * Removes entry having key k from bucket with hash value h, returns value of removed entry
     * @param h hash value
     * @param k key
     * @return value of removed entry
     */
    protected V bucketRemove(int h, K k) {
        int idx = findSlot(h, k);
        if(idx >= 0) {
            V answer = table[idx].getValue();
            table[idx] = DEFUNCT;
            n--;
            return answer;
        }
        return null;
    }

    /**
     * Returns an iterable collection of all key value pairs
     * Order does not matter
     * @return iterable collection of entries
     */
    public Iterable<Entry<K,V>> entrySet() {
        ArrayList<Entry<K,V>> buffer = new ArrayList<>();
        for(int idx = 0; idx < capacity; idx++) {
            if(!isAvailable(idx))
                buffer.add(table[idx]);
        }
        return buffer;
    }





}
