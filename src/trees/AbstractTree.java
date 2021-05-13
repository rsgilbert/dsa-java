package trees;


import util.Position;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * An abstract class providing some functionality of the Tree interface
 * Goodrich page 314
 *
 * @param <E>
 */
public abstract class AbstractTree<E> implements Tree<E> {
    @Override
    public boolean isInternal(Position<E> p) { return numChildren(p) > 0; }
    @Override
    public boolean isExternal(Position<E> p) { return numChildren(p) == 0; }
    @Override
    public boolean isRoot(Position<E> p) { return p == root(); }
    @Override
    public boolean isEmpty() { return size() == 0; }


    /**
     * Depth of a position p is the number of ancestors of p other than p itself
     * @return the number of levels separating Position p from the root
     * @param p position
     */
    @Override
    public int depth(Position<E> p) {
        int depth = 0;
        Position<E> walker = p;
        while(walker != null) {
            depth++;
            walker = parent(walker);
        }
        return depth;
    }

    /**
     * Height of a tree is the maximum of the depth of its positions
     * Goodrich pg 316
     * @return the height of the subtree rooted at Position p
     * @param p position
     */
    @Override
    public int height(Position<E> p) {
        int h = 0;
        for(Position<E> c: children(p)) {
            h = Math.max(h, height(c) + 1);
        }
        return h;
    }

    // -- nested ElementIterator class --
    /**
     * This class adapts the iterations produced by positions() to create an iteration of elements
     */
    private class ElementIterator implements Iterator<E> {
        Iterator<Position<E>> positionIterator = positions().iterator();
        @Override
        public boolean hasNext() { return positionIterator.hasNext(); }
        @Override
        public E next() { return positionIterator.next().getElement(); }
        @Override
        public void remove() { positionIterator.remove(); }
    }

    @Override
    public Iterator<E> iterator() { return new ElementIterator(); }

    /**
     * Defining preorder as the default traversal algorithm
     * @return iterable
     */
    @Override
    public Iterable<Position<E>> positions() { return preorder(); }


    // -- Traversal algorithms --
    /**
     * Utility method. Adds positions of the subtree rooted at Position p to the given snapshot
     * Preorder: Add position p before exploring subtrees
     */
    private void preorderSubtree(Position<E> p, List<Position<E>> snapshot) {
        snapshot.add(p);
        /**
         * Implicit base case: The for loop body never executes when the position has
         * no children
         */
        for(Position<E> c: children(p)) {
            preorderSubtree(c, snapshot);
        }
    }

    /**
     * Returns a preordered iterable collection of all positions of the tree
     * @return iterable
     */
    public Iterable<Position<E>> preorder() {
        List<Position<E>> snapshot = new ArrayList<>();
        if(isEmpty()) return snapshot;
        preorderSubtree(root(), snapshot);
        return snapshot;
    }


}

