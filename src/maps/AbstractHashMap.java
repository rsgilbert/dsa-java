package maps;

import java.util.ArrayList;
import java.util.Random;

/**
 * Java Hash Table implementation
 * Provides functionality common to implementations that use separate chaining
 * and implementations that use open addressing with linear probing
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

    public AbstractHashMap(int cap, int p) {
        prime = p;
        capacity = cap;
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
    private int hashCodeFn(K key) {
        return (int) Math.abs(key.hashCode() * scale + shift) % prime;
    }
    private int compressFn(int hashCode) {
        return hashCode % capacity;
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
