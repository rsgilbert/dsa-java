package trees;


import util.Position;
import java.util.ArrayList;


/**
 * An abstract base class providing some functionality of the BinaryTree interface
 * @param <E>
 */
public abstract class AbstractBinaryTree<E> extends AbstractTree<E> implements BinaryTree<E> {
    /**
     * Returns the position of p's sibling (null if notexists)
     * @param p position
     * @return position
     */
    public Position<E> sibling(Position<E> p) {
        Position<E> parent = parent(p);
        if(parent == null) return null;
        if(p == left(parent))
            return right(parent);
        return left(parent);
    }

    /**
     * Returns the number of children of position p
     * @param p position
     * @return count
     */
    public int numChildren(Position<E> p) {
        int count = 0;
        if(left(p) != null) count++;
        if(right(p) != null) count++;
        return count;
    }

    /**
     * Returns an iterable collection of the Positions representing p's children
     * @param p position
     * @return iterable
     */
    public Iterable<Position<E>> children(Position<E> p) {
        ArrayList<Position<E>> children = new ArrayList<>(2);
        if(left(p) != null) children.add(p);
        if(right(p) != null) children.add(p);
        return children;
    }

}
