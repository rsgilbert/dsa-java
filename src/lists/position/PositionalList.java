package lists.position;


import util.Position;

/**
 * Interface for positional lists
 * @param <E>
 */
public interface PositionalList<E> {
    /**
     * Returns the number of elements in the list
     */
    int size();

    /**
     * Tests whether the list is empty
     */
    boolean isEmpty();

    /**
     * Returns the first position in the list or null if the list is empty
     * @return first position
     */
    Position<E> first();

    /**
     *
     * @return last position or null if list is empty
     */
    Position<E> last();

    /**
     * @return the position imediately before Position p or null if p is first
     */
    Position<E> before(Position<E> p) throws IllegalArgumentException;

    /**
     * @return position immediately after Position p or null if p is last
     */
    Position<E> after(Position<E> p) throws IllegalArgumentException;

    /**
     * Inserts element e at the front of the list and returns its new position
     */
    Position<E> addFirst(E e);

    /**
     * Inserts  element e at the back of the list and returns its new position
     */
    Position<E> addLast(E e);

    /**
     * Inserts element e immediately after Position p and returns its new position
     * @param p
     * @param e
     * @return e's new position
     * @throws IllegalArgumentException
     */
    Position<E> addBefore(Position<E> p, E e) throws IllegalArgumentException;

    /**
     * Inserts element e immediately after Position p and returns its new position
     * @param p
     * @param e
     * @return e's new position
     * @throws IllegalArgumentException
     */
    Position<E> addAfter(Position<E> p, E e) throws IllegalArgumentException;

    /**
     * Replaces the element stored at Position p and returns the replaced element
     * @param p
     * @param e
     * @return replaced element
     * @throws IllegalArgumentException
     */
    E set(Position<E> p, E e) throws IllegalArgumentException;

    /**
     * Removes the element stored at Position p and returns it (invalidating p)
     */
    E remove(Position<E> p) throws IllegalArgumentException;

    /**
     * Returns an iterable representation of the list's positions
     * @return an iterable of positions
     */
    Iterable<Position<E>> positions();
}

