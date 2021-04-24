package maps;

import java.util.ArrayList;
import java.util.Random;

/**
 * Java Hash Table implementation
 * Provides functionality common to implementations that use separate chaining
 * and implementations that use open addressing with linear probing
 * Mainly deals with calculating the hash value for a given key and resizing
 * the hash map when a certain condition is met which is usually when the table
 * hits a particular size to capacity ratio
 *
 * Expected running time
 * get    : O(1)
 * put    : O(1)
 * remove : O(1)
 * @param <K> Key
 * @param <V> Value
 */
public abstract class AbstractHashMap<K,V> extends AbstractMap<K,V> {
    // number of entries in the dictionary
    protected int n = 0;
    // length of the table
    protected int capacity;
    // prime factor
    private int prime;
    // shift and scaling factors
    private long scale, shift;

    /**
     * Initializes capacity, prime, scale and shift and then creates the hash table
     * @param cap capacity
     * @param p prime
     */
    public AbstractHashMap(int cap, int p) {
        capacity = cap;
        // For compress function
        // @see compressFn
        prime = p;
        Random rand = new Random();
        scale = rand.nextInt(prime - 1) + 1;
        shift = rand.nextInt(prime);
        createTable();
    }

    public AbstractHashMap(int cap) {
        // use default prime
        this(cap, 109345121);
    }

    public AbstractHashMap() {
        // use default capacity
        this(17);
    }

    // -- public methods --
    public int size() { return n; }
    public V get(K key) {
        return bucketGet(hashValue(key), key);
    }
    public V remove(K key) {
        return bucketRemove(hashValue(key), key);
    }
    public V put(K key, V value) {
        V answer = bucketPut(hashValue(key), key, value);
        // keep load factor <= 0.5
        if(n > capacity / 2) {
            // A prime number is most preferred as new capacity
            resize(2 * capacity - 1);
        }
        return answer;
    }

    // -- end of public methods --

    // -- private utilities --
    private int hashValue(K key) {
        return compressFn(hashCodeFn(key));
    }
    // - hash function utilities -
    /**
     * HashCode function
     * Relies on the built-in/default hash code function for objects
     * @param key key
     * @return hashCode of key
     */
    private int hashCodeFn(K key) {
        return key.hashCode();
    }
    /**
     * Compression function.
     * Maps an integer hashCode into the range [0, capacity-1]
     * Minimizes number of collisions for a given set of distinct hash codes
     * Uses the MAD method (Multiply-Add-and-Divide)
     * MAD = [(ai + b) mod p] mod N, Goodrich pg 416
     * prime is a prime number larger than n
     * scale and shift are random numbers in the range [0, prime - 1], scale is greater than 0
     * @return hash value
     */
    private int compressFn(int hashCode) {
        return (int) ((Math.abs(hashCode() * scale + shift) % prime) % capacity);
    }
    // - end of hash function utilities -

    private void resize(int newCap) {
        ArrayList<Entry<K,V>> buffer = new ArrayList<>(n);
        for(Entry<K,V> e: entrySet())
            buffer.add(e);
        capacity = newCap;
        // create table based on updated capacity
        createTable();
        // size will be recomputed while reinserting entries
        n = 0;
        for(Entry<K,V> e: buffer)
            put(e.getKey(), e.getValue());
    }
    // protected abstract methods to be implemented by subclasses
    protected abstract void createTable();
    protected abstract V bucketGet(int h, K k);
    protected abstract V bucketPut(int h, K k, V v);
    protected abstract V bucketRemove(int h, K k);
}
