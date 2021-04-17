package maps;


/**
 *
 */

/**
 * A formal definition of a Java interface for our version of the map ADT.
 * @param <K> Key
 * @param <V> Value
 */
public interface Map<K,V> {
    int size();
    boolean isEmpty();
    V get(K key);
    V put(K key, V value);
    V remove(K key);
    Iterable<K> keySet();
    Iterable<V> values();
    Iterable<Entry<K,V>>  entrySet();
}
