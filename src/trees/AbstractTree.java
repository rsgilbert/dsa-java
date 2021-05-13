package trees;


import util.Position;

/**
 * An abstract class providing some functionality of the Tree interface
 * Goodrich page 314
 *
 * @param <E>
 */
public abstract class AbstractTree<E> implements Tree<E> {
    public boolean isInternal(Position<E> p) { return numChildren(p) > 0; }
    public boolean isExternal(Position<E> p) { return numChildren(p) == 0; }
    public boolean isRoot(Position<E> p) { return p == root(); }
    public boolean isEmpty() { return size() == 0; }


    /**
     * Depth of a position p is the number of ancestors of p other p itself
     * @return the number of levels separating Position p from the root
     * @param p position
     */
    public int depth(Position<E> p) {
        int depth = 0;
        Position<E> walker = p;
        while(!walker.equals(root())) {
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
    public int height(Position<E> p) {
        int h = 0;
        for(Position<E> c: children(p)) {
            h = Math.max(h, height(c) + 1);
        }
        return h;
    }
}

