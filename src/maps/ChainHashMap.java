package maps;

import util.Entry;

import java.util.ArrayList;

/**
 * A complete definition of ChainHashMap which implements a hash table with separate chaining.
 * Expected running time
 * bucketGet    : O(1)
 * bucketPut    : O(1)
 * bucketRemove : O(1)
 * entrySet     : O(n)
 *
 * @param <K>
 * @param <V>
 */
public class ChainHashMap<K,V> extends AbstractHashMap<K,V> {
    /**
     * A fixed capacity array of UnsortedTableMap that serve as buckets
     * initialized within createTable
     */
    private UnsortedTableMap<K,V>[] table;

    // -- constructors --
    public ChainHashMap() { super(); }
    public ChainHashMap(int cap) { super(cap); }
    public ChainHashMap(int cap, int p) { super(cap,p); }
    // -- end of constructors --

    /***
     * Create an empty table having length equal to the current capacity
     * The table is made up of buckets which are of type UnsortedTableMap
     * Each bucket created is null at first
     */
    protected void createTable() {
        table = (UnsortedTableMap<K, V>[]) new UnsortedTableMap[capacity];
    }

    /**
     * Returns value associated with key k in bucket with hash value h, else null
     * @param h hashValue
     * @param k key
     * @return value associated with key, else null
     */
    protected V bucketGet(int h, K k) {
        UnsortedTableMap<K,V> bucket = table[h];
        if (bucket == null)
            return null;
        return bucket.get(k);
    }

    /**
     * Associates key k with value v in bucket with hashValue h, returns old value
     * @param h hashValue
     * @param k key
     * @param v value
     * @return old value
     */
    protected V bucketPut(int h, K k, V v) {
        UnsortedTableMap<K,V> bucket = table[h];
        if(bucket == null) {
            bucket = table[h] = new UnsortedTableMap<>();
        }
        // Size of bucket may or may not change when we perform a put on it
        // If we end up performing an update size will not change
        // However, if a new entry gets added to the bucket size will change
        // We need to record the change in size of bucket and use it to update size of the hash table
        int oldSize = bucket.size();
        V answer = bucket.put(k, v);
        int bucketSizeChange = bucket.size() - oldSize;
        n += bucketSizeChange;
        return answer;
    }


    /**
     * Removes entry having key k from bucket with hash from bucket with hash value h if any
     * @param h hash value
     * @param k key
     * @return value of removed entry
     */
    protected V bucketRemove(int h, K k) {
        UnsortedTableMap<K,V> bucket = table[h];
        if(bucket == null)
            return null;
        // Size of bucket may or may not change when we perform a remove on it
        // If an entry with given key existed size of bucket will change
        // However, if no such entry existed in bucket size will not change
        // We need to record the change in size of bucket and use it to update size of the hash table
        int oldSize = bucket.size();
        V answer = bucket.remove(k);
        int bucketSizeChange = bucket.size() - oldSize;
        n += bucketSizeChange;
        return answer;
    }

    /**
     * Returns an iterable collection of all key-value entries of the map
     * @return iterable collection
     */
    public Iterable<Entry<K,V>> entrySet() {
        ArrayList<Entry<K,V>> entries = new ArrayList<>();
        for(UnsortedTableMap<K,V> bucket: table) {
            if(bucket != null)
                for(Entry<K,V> entry: bucket.entrySet())
                    entries.add(entry);
        }
        return entries;
    }






}
