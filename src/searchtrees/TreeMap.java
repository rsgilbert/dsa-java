package searchtrees;


import maps.AbstractSortedMap;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import util.Entry;
import util.Position;

/**
 * An implementation of a sorted map using a binary search tree
 */
public class TreeMap<K, V> extends AbstractSortedMap<K, V> {
    // We use a specialized subclass of the LinkedBinaryTree to represent the
    // underlying tree structure
    protected BalanceableBinaryTree<Entry<K, V>> tree = new BalanceableBinaryTree<>();

    /**
     * Constructs an empty map using the natural ordering of keys
     */
    public TreeMap() {
        // call the AbstractSortedMap constructor
        super();
        // Creates a sentinel leaf as root
        tree.addRoot(null);
    }

    /**
     * Constructs an empty map using the given comparator to order keys
     */
    public TreeMap(Comparator<K> comp) {
        super(comp);
        // Creates a sentinel leaf as root
        tree.addRoot(null);
    }


    // -- utility methods --

    /**
     * Utility when inserting a new entry at a leaf of the tree
     * Sets an entry to the given position and sets the position's left and right nodes to null
     *
     * @param p     leaf position
     * @param entry Entry to set at the leaf
     */
    private void expandExternal(Position<Entry<K, V>> p, Entry<K, V> entry) {
        if (tree.isInternal(p))
            throw new IllegalArgumentException("Position p must be a leaf");
        tree.set(p, entry);
        addLeafSentinels(p);
    }

    /**
     * Utility for adding sentinel nodes to an external node
     *
     * @param p
     */
    private void addLeafSentinels(Position<Entry<K, V>> p) {
        tree.addLeft(p, null);
        tree.addRight(p, null);
    }

    /**
     * Core algorithm to the data structure
     * Returns the position in p's subtree having given key (or else the terminal leaf)
     *
     * @param p   position whose subtree to search
     * @param key key to search for
     * @return position
     */
    private Position<Entry<K, V>> treeSearch(Position<Entry<K, V>> p, K key) {
        if (isExternal(p))
            return p;
        Entry<K, V> entry = p.getElement();
        int comparison = compare(key, entry);
        if (comparison < 0)
            return treeSearch(left(p), key);
        if (comparison == 0)
            return p;
        else return treeSearch(right(p), key);
    }

    /**
     * Core algorithm
     * Find the position with maximum key
     *
     * @param p position in tree to start from
     * @return position
     */
    private Position<Entry<K, V>> subTreeMax(Position<Entry<K, V>> p) {
        if (isExternal(right(p)))
            return p;
        return subTreeMax(right(p));
    }

    /**
     * Core algorithm
     * Find the position with minimum key
     *
     * @param p position in tree to start from
     * @return position
     */
    private Position<Entry<K, V>> subTreeMin(Position<Entry<K, V>> p) {
        if (isExternal(left(p)))
            return p;
        return subTreeMin(left(p));
    }

    /**
     * Utility for recursively creating a list of internal entries using inorder traversal
     *
     * @param p    position to begin from
     * @param list list to add entries to
     */
    private void mapInorder(Position<Entry<K, V>> p, List<Entry<K, V>> list) {
        Position<Entry<K, V>> left = left(p);
        Position<Entry<K, V>> right = right(p);
        if (isInternal(left))
            mapInorder(left, list);
        list.add(p.getElement());
        if (isInternal(right))
            mapInorder(right, list);
    }

    // -- end of utility methods --

    // -- shorthand protected methods to wrap operations on the underlying linked binary tree --
    protected Position<Entry<K, V>> root() {
        return tree.root();
    }

    protected Position<Entry<K, V>> left(Position<Entry<K, V>> p) {
        return tree.left(p);
    }

    protected Position<Entry<K, V>> right(Position<Entry<K, V>> p) {
        return tree.right(p);
    }

    protected Position<Entry<K, V>> parent(Position<Entry<K, V>> p) {
        return tree.parent(p);
    }

    protected Entry<K, V> set(Position<Entry<K, V>> p, Entry<K, V> entry) {
        return tree.set(p, entry);
    }

    protected boolean isExternal(Position<Entry<K, V>> p) {
        return tree.isExternal(p);
    }

    protected boolean isInternal(Position<Entry<K, V>> p) {
        return tree.isInternal(p);
    }

    protected Position<Entry<K, V>> sibling(Position<Entry<K, V>> p) {
        return tree.sibling(p);
    }

    protected Iterable<Position<Entry<K, V>>> children(Position<Entry<K, V>> p) {
        return tree.children(p);
    }
    // -- end of shorthand methods --

    // -- public methods --

    /**
     * Get the total number of entries in the tree
     * Only the internal nodes have entries
     * The total number of entries equals to the total number of internal nodes
     * N = E + I
     * E = I + 1
     * N = 2I + 1
     * I = (N - 1) / 2
     *
     * @return number of entries
     */
    @Override
    public int size() {
        return (tree.size() - 1) / 2;
    }

    /**
     * Returns the value associated with the specified key (or else null)
     *
     * @param key key
     * @retur value for entry with given key (null if not exists)
     */
    @Override
    public V get(K key) throws IllegalArgumentException {
        checkKey(key);
        Position<Entry<K, V>> p = treeSearch(root(), key);
        // hook for balanced tree search
        rebalanceAccess(p);
        // All external nodes are sentinels
        if (isExternal(p)) return null;
        return p.getElement().getValue();
    }

    /**
     * Updates the value for entry at specified key if entry exists else creates new entry
     *
     * @param key   key
     * @param value value
     * @return old value (null if new entry)
     */
    @Override
    public V put(K key, V value) {
        checkKey(key);
        MapEntry<K, V> newEntry = new MapEntry<>(key, value);
        Position<Entry<K, V>> p = treeSearch(root(), key);
        if (tree.isExternal(p)) {
            expandExternal(p, newEntry);
            // hook for balanced tree subclasses
            rebalanceInsert(p);
            return null;
        } else {
            V result = set(p, newEntry).getValue();
            // hook for balanced tree subclasses
            rebalanceAccess(p);
            return result;
        }
    }

    /**
     * Removes entry having key, key (if any), and returns its associated value
     *
     * @param key key
     * @return associated value for key key
     */
    @Override
    public V remove(K key) throws IllegalArgumentException {
        checkKey(key);
        Position<Entry<K, V>> p = treeSearch(root(), key);
        if (isExternal(p)) {
            // Hook for balanced tree subclasses
            rebalanceAccess(p);
            return null;
        }
        V result = p.getElement().getValue();

        if (isInternal(left(p)) && isInternal(right(p))) {
            // Both children of the nodes are internal
            // We replace it with subtree node with highest value
            Position<Entry<K, V>> replacement = subTreeMax(left(p));
            set(p, replacement.getElement());
            // Move to the position where we got our replacement from
            // and proceed
            p = replacement; // at this point p's right child is assured to be external
        }
        // p how has at most 1 child
        // We delegate the remove implementation to the remove method in LinkedBinaryTree
        Position<Entry<K, V>> leaf = isExternal(right(p)) ? right(p) : left(p);
        // Get a reference to the node that will be deleted
        Position<Entry<K, V>> sib = sibling(leaf);
        tree.remove(leaf);
        tree.remove(p);
        // Hook for balanced tree subclasses
        rebalanceDelete(sib);
        return result;
    }

    // -- public methods specific to sorted map interface --

    /**
     * Gets the entry with greatest key
     *
     * @return entry
     */
    @Override
    public Entry<K, V> lastEntry() {
        if (isEmpty()) return null;
        return subTreeMax(root()).getElement();
    }

    /**
     * Gets entry with smallest key
     *
     * @return entry
     */
    @Override
    public Entry<K, V> firstEntry() {
        if (isEmpty()) return null;
        return subTreeMin(root()).getElement();
    }

    /**
     * Gets entry with key less than or equal to given key
     *
     * @param key key
     * @return entry
     */
    @Override
    public Entry<K, V> floorEntry(K key) {
        checkKey(key);
        // After this if() we can take it for granted that walker is not the root
        if (isEmpty()) return null;
        Position<Entry<K, V>> walker = treeSearch(root(), key);
        // If walker is internal then we got a match
        if (isInternal(walker)) return walker.getElement();
        // We turn left because the given key is smaller
        // We turn right because the given key is larger
        // We want the position we were at the last time we turned right
        // because at that position the key at that position was less than
        // the key we were given. This is the floor we are looking for
        Position<Entry<K, V>> parent = parent(walker);
        // Get the first ancestor of walker that has walker in its right branch
        while (left(parent) == walker) {
            if (root() == parent) return null;
            walker = parent;
            parent = parent(walker);
        }
        // At this point right(parent) == walker
        return parent.getElement();
    }

    /**
     * Gets entry with key higher than or equal to given key
     *
     * @param key key
     * @return entry
     */
    @Override
    public Entry<K, V> ceilingEntry(K key) {
        checkKey(key);
        if (isEmpty()) return null;
        Position<Entry<K, V>> walker = treeSearch(root(), key);
        if (isInternal(walker)) return walker.getElement();
        // Get the entry at which we last made a left turn
        // because at that entry the key for that entry was
        // larger than all the keys we have encountered after it
        // including the given key

        // parent cannot be null because if walker was the root
        // and it was also external that would mean that the tree is
        // empty and that is a situation we have already handled
        Position<Entry<K, V>> parent = parent(walker);
        while (right(parent) == walker) {
            // If parent is the root then we wont be encountering the larger
            // entry we are looking for which means that the given key is larger
            // than all the keys in the tree so we can not find a ceiling entry
            if (parent == root()) return null;
            walker = parent;
            parent = parent(walker);
        }
        // At this point parent made a left turn to get to walker ie left(parent) == walker
        // which means that parent has a key larger than walker's key hence it has the ceiling entry
        return parent.getElement();
    }

    /**
     * Get the entry with key strictly higher than given key
     *
     * @param key key
     * @return higher entry
     */
    @Override
    public Entry<K, V> higherEntry(K key) {
        checkKey(key);
        if (isEmpty()) return null;
        Position<Entry<K, V>> walker = treeSearch(root(), key);
        // if we find a match then the higher key is the smallest key in
        // the right branch (if exists) of subtree rooted at walker
        if (isInternal(walker) && isInternal(right(walker)))
            return subTreeMin(right(walker)).getElement();
        // If we didnt find a match we follow the same process as for
        // ceilingEntry of finding the ancestor entry at which we last
        // made a left turn
        Position<Entry<K, V>> parent = parent(walker);
        while (right(parent) == walker) {
            if (parent == root()) return null;
            walker = parent;
            parent = parent(walker);
        }
        return parent.getElement();
    }

    /**
     * Get entry with key strictly less than given key
     *
     * @param key key
     * @return entry
     */
    public Entry<K, V> lowerEntry(K key) {
        // Similar to floorEntry but without the statement to return
        // walker's element when walker is internal
        checkKey(key);
        // After this if() we can take it for granted that walker is not the root
        if (isEmpty()) return null;
        Position<Entry<K, V>> walker = treeSearch(root(), key);
        // If we have found a match, the next smaller key is
        // in the left branch of subtree rooted at walker
        // If walker does not have a left internal entry, we find the
        // smaller entry from walker's ancestors
        if (isInternal(walker) && isInternal(left(walker)))
            return subTreeMax(left(walker)).getElement();
        // We turn left because the given key is smaller
        // We turn right because the given key is larger
        // We want the position we were at the last time we turned right
        // because at that position the key at that position was less than
        // the key we were given. This is the lower entry we are looking for
        Position<Entry<K, V>> parent = parent(walker);
        // Get the first ancestor of walker that has walker in its right branch
        while (left(parent) == walker) {
            if (root() == parent) return null;
            walker = parent;
            parent = parent(walker);
        }
        // At this point right(parent) == walker
        return parent.getElement();
    }

    /**
     * Get an iterable of all entries in the map
     * Part of the Map interface
     *
     * @return iterable of all entries
     */
    @Override
    public Iterable<Entry<K, V>> entrySet() {
        List<Entry<K, V>> snapshot = new ArrayList<>();
        if (isEmpty()) return snapshot;
        mapInorder(root(), snapshot);
        return snapshot;
    }

    /**
     * Get an iterable of a submap of entries in the map in the range [fromKey, toKey)
     * beginning and including entry with smallest key greater than or equal to beginKey and ending but not including
     * entry with smallest key greater than or equal to stopKey
     *
     * @param fromKey key
     * @param stopKey key
     * @return iterable
     */
    @Override
    public Iterable<Entry<K, V>> subMap(K fromKey, K stopKey) {
        List<Entry<K, V>> snapshot = new ArrayList<>();
        Position<Entry<K, V>> walker = treeSearch(root(), fromKey);
        if (compare(fromKey, stopKey) < 0) {
            subMapRecursive(fromKey, stopKey, root(), snapshot);
        }
        return snapshot;

    }

    public void subMapRecursive(K fromKey, K stopKey, Position<Entry<K, V>> p, List<Entry<K, V>> list) {
        if (isExternal(p)) return;
        if (compare(fromKey, p.getElement()) > 0)
            // Exclude the left branch
            subMapRecursive(fromKey, stopKey, right(p), list);
        else {
            // At this point we have some entries
            // in {the left branch and p} greater than or equal to fromKey
            if (compare(stopKey, p.getElement()) <= 0) {
                // Exclude the right branch
                subMapRecursive(fromKey, stopKey, left(p), list);
            } else {
                // At this point we have some entries
                // in {the right branch and p} strictly less than stopKey.
                // Perform an inorder traversal
                subMapRecursive(fromKey, stopKey, left(p), list);
                list.add(p.getElement());
                subMapRecursive(fromKey, stopKey, right(p), list);
            }
        }
    }


    // -- end of public methods specific to sorted map interface --


    // -- end of public methods --


}







