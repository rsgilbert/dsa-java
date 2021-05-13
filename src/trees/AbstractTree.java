package trees;


import util.Position;

import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * An abstract class providing some functionality of the Tree interface
 * Goodrich page 314
 *
 * @param <E>
 */
public abstract class AbstractTree<E> implements Tree<E> {
    @Override
    public boolean isInternal(Position<E> p) {
        return numChildren(p) > 0;
    }

    @Override
    public boolean isExternal(Position<E> p) {
        return numChildren(p) == 0;
    }

    @Override
    public boolean isRoot(Position<E> p) {
        return p == root();
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }


    /**
     * Depth of a position p is the number of ancestors of p other than p itself
     *
     * @param p position
     * @return the number of levels separating Position p from the root
     */
    @Override
    public int depth(Position<E> p) {
        int depth = 0;
        Position<E> walker = p;
        while (walker != null) {
            depth++;
            walker = parent(walker);
        }
        return depth;
    }

    /**
     * Height of a tree is the maximum of the depth of its positions
     * Goodrich pg 316
     *
     * @param p position
     * @return the height of the subtree rooted at Position p
     */
    @Override
    public int height(Position<E> p) {
        int h = 0;
        for (Position<E> c : children(p)) {
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
        public boolean hasNext() {
            return positionIterator.hasNext();
        }

        @Override
        public E next() {
            return positionIterator.next().getElement();
        }

        @Override
        public void remove() {
            positionIterator.remove();
        }
    }

    @Override
    public Iterator<E> iterator() {
        return new ElementIterator();
    }

    /**
     * Defining preorder as the default traversal algorithm
     *
     * @return iterable
     */
    @Override
    public Iterable<Position<E>> positions() {
        return preorder();
    }


    // -- Traversal algorithms --
    // - Preorder traversal -

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
        for (Position<E> c : children(p)) {
            preorderSubtree(c, snapshot);
        }
    }

    /**
     * Returns a preordered iterable collection of all positions of the tree
     *
     * @return iterable
     */
    public Iterable<Position<E>> preorder() {
        List<Position<E>> snapshot = new ArrayList<>();
        if (isEmpty()) return snapshot;
        preorderSubtree(root(), snapshot);
        return snapshot;
    }

    // - End of preorder traversal -

    // - Postorder traversal -

    /**
     * Utility method. Adds positions of the subtree rooted at Position p to the given snapshot
     * Postorder: Add position p after exploring subtrees
     */
    private void postorderSubtree(Position<E> p, List<Position<E>> snapshot) {
        for (Position<E> c : children(p)) {
            postorderSubtree(c, snapshot);
        }
        snapshot.add(p);
    }

    /**
     * Returns a postordered iterable of all positions in the tree
     *
     * @return iterable
     */
    public Iterable<Position<E>> postorder() {
        List<Position<E>> snapshot = new ArrayList<>();
        if (isEmpty()) return snapshot;
        postorderSubtree(root(), snapshot);
        return snapshot;
    }
    // - End of postorder traversal -

    // - Breadth-first traversal -

    /**
     * Returns an iterable collection of positions of a tree in breadth-first order
     * We visit all positions at depth d before we visit positions at depth d+1
     * @return iterable
     */
    public Iterable<Position<E>> breadthfirst() {
        Queue<Position<E>> queue = new ArrayDeque<>();
        List<Position<E>> snapshot = new ArrayList<>();
        if (isEmpty()) return snapshot;
        queue.add(root());
        while (!queue.isEmpty()) {
            Position<E> p = queue.remove();
            snapshot.add(p);
            for(Position<E> c: children(p))
                queue.add(c);
        }
        return snapshot;
    }


}

