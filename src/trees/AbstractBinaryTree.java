package trees;


import util.Position;
import java.util.ArrayList;
import java.util.List;


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
        Position<E> left = left(p);
        Position<E> right = right(p);
        if(left != null) children.add(left);
        if(right != null) children.add(right);
        return children;
    }

    /**
     * Inorder traversal
     * Visit all the subtrees on the left child, visit child, then visit all subtrees on the
     * right child
     * Applicable only to binary trees.
     * @param p position
     * @param snapshot list
     */
    public void inorderSubtree(Position<E> p, List<Position<E>> snapshot) {
        Position<E> left = left(p);
        Position<E> right = right(p);
        if(left != null)
            inorderSubtree(left, snapshot);
        snapshot.add(p);
        if(right != null)
            inorderSubtree(right,snapshot);
    }

    /**
     * Get iterable of all tree positions by performing inorder traversal on the tree
     * @return iterable
     */
    public Iterable<Position<E>> inorder() {
        List<Position<E>> snapshot = new ArrayList<>();
        if(isEmpty()) return snapshot;
        inorderSubtree(root(), snapshot);
        return snapshot;
    }

    /**
     * Returns an iterable of all positions in the binary tree
     * Since we usually want the positions ordered using inorder traversal,
     * we make the function default to using inorder method
     * @return iterable
     */
    @Override
    public Iterable<Position<E>> positions() {
        return inorder();
    }
}
