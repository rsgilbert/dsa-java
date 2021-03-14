package priorityqueue;

/**
 * Interface for a key-value pair
 * Application of Composition Design Pattern
 */
public interface Entry<K,V> {
    K getKey();
    V getValue();
}
