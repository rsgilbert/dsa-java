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
}
