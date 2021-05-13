package trees;


import util.Position;

/**
 * Interface for the binary tree ADT
 * @param <E>
 */
public interface BinaryTree<E> extends Tree<E> {
    Position<E> left(Position<E> p) throws IllegalArgumentException;
    Position<E> right(Position<E> p) throws IllegalArgumentException;
    Position<E> sibling(Position<E> p) throws IllegalArgumentException;
    Position<E> addRoot(E e);
    Position<E> addLeft(Position<E> p, E e);
    Position<E> addRight(Position<E> p, E e);
    E set(Position<E> p, E e);
    void attach(Position<E> p, LinkedBinaryTree<E> t1,LinkedBinaryTree<E> t2);
    E remove(Position<E> p);


}
