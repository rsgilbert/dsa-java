package maps;


import java.util.Iterator;

/**
 * Base class that provides the functionality that is shared by all of our map implementations
 *
 * Running time
 * isEmpty() : O(1)
 * keySet()  : O(n)
 * values()  : O(n)
 *
 * @param <K> Key
 * @param <V> Value
 */
public abstract class AbstractMap<K, V> implements Map<K,V> {
    public boolean isEmpty() { return size() == 0; }

    // -- nested MapEntry class --
    protected static class MapEntry<K, V> implements Entry<K, V> {
        private K k;
        private V v;

        public MapEntry(K key, V value) {
            k = key;
            v = value;
        }

        // public methods of the Entry interface
        public K getKey() { return k; }
        public V getValue() { return v; }

        // utilities not exposed as part of the Entry interface
        protected void setKey(K key) { k = key; }
        protected V setValue(V value) {
            V old = v;
            v = value;
            return old;
        }
    }
    // -- End of MapEntry --


    // -- Support for keySet() method --
    private class KeyIterator implements Iterator<K> {
        // reuse entrySet
        final private Iterator<Entry<K, V>> entries = entrySet().iterator();
        @Override
        public boolean hasNext() { return entries.hasNext(); }
        @Override
        public K next() { return entries.next().getKey(); }
    }
    // Makes use of KeyIterator
    private class KeyIterable implements Iterable<K> {
        public Iterator<K> iterator() { return new KeyIterator(); }
    }
    public Iterable<K> keySet() { return new KeyIterable(); }
    // -- keySet support end --

    // -- Support for values() method --
    private class ValueIterator implements Iterator<V> {
        // reuse entrySet
        final private Iterator<Entry<K,V>> entries = entrySet().iterator();
        @Override
        public boolean hasNext() { return entries.hasNext(); }
        @Override
        public V next() { return entries.next().getValue(); }
    }
    private class ValueIterable implements Iterable<V> {
        @Override
        public Iterator<V> iterator() { return new ValueIterator(); }
    }
    @Override
    public Iterable<V> values() { return new ValueIterable(); }



}
