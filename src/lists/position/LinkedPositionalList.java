package lists.position;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Implementation of a positional list stored as a doubly linked list
 *
 * Performance:
 * ---------------------------------------------------------------
 * | Method                          | Running Time              |
 * ---------------------------------------------------------------
 * | size()                          | O(1)                      |
 * | isEmpty()                       | O(1)                      |
 * | first(), last()                 | O(1)                      |
 * | before(p), after(p)             | O(1)                      |
 * | addFirst(e), addLast(e)         | O(1)                      |
 * | addBefore(p, e), addAfter(p, e) | O(1)                      |
 * | set(p, e)                       | O(1)                      |
 * | remove(p)                       | O(1)                      |
 * ---------------------------------------------------------------
 *
 * @param <E> element type
 */
public class LinkedPositionalList<E> implements PositionalList<E> {
    // -------------- nested Node class --------------
    private static class Node<E> implements Position<E> {
        /**
         * Reference to the element stored at this node
         */
        private E element;

        /**
         * Reference to the previous node in the list
         */
        private Node<E> prev;

        /**
         * Reference to the subsequent node in the list
         */
        private Node<E> next;

        public Node(E e, Node<E> p, Node<E> n) {
            element = e;
            prev = p;
            next = n;
        }

        public E getElement() throws IllegalStateException {
            /**
             * Convention for defunct node
             */
            if(next == null) {
                throw new IllegalStateException("Position is no longer valid");
            }
            return element;
        }

        public Node<E> getPrev() {
            return prev;
        }
        public Node<E> getNext() {
            return next;
        }
        public void setElement(E e) {
            element = e;
        }
        public void setPrev(Node<E> p) {
            prev = p;
        }
        public void setNext(Node<E> n) {
            next = n;
        }
    }
    // -------------- end of nested Node class ----------------

    /**
     * Instance variables of the LinkedPositionalList
     */
    // header sentinel
    private Node<E> header;
    // trailer sentinel
    private Node<E> trailer;
    // number of elements in the list
    private int size = 0;

    /**
     * Constructs a new empty list
     */
    public LinkedPositionalList() {
        // create header
        header = new Node<>(null, null, null);
        // trailer is preceded by header
        trailer = new Node<>(null, header, null);
        // header is followed by trailer
        header.setNext(trailer);
    }

    // ----- private utilities -----------

    /**
     * Validates the position and returns it as a node
     * @param p
     * @return node for given position
     * @throws IllegalArgumentException
     */
    private Node<E> validate(Position<E> p) throws IllegalArgumentException {
        if(!(p instanceof Node)) throw new IllegalArgumentException("Invalid p");
        // safe cast
        Node<E> node = (Node<E>) p;
        // convention for defunct node
        if(node.getNext() == null){
            throw new IllegalArgumentException("p is no longer in the list");
        }
        return node;
    }

    private Position<E> position(Node<E> node) {
        if(node == header || node == trailer) {
            // do not expose user to the sentinels
            return null;
        }
        return node;
    }

    // ---------------- end of private utilities ----------------------

    // ---------------- public accessor methods -----------------------

    /**
     * Returns the number of elements in the linked list
     * @return size of linked list
     */
    public int size() {
        return size;
    }

    /**
     * Tests whether the linked list is empty
     * @return whether the linked list is empty
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Returns the first position in the linked list or null if empty
     * @return first position in linked list
     */
    public Position<E> first() {
        return position(header.getNext());
    }

    /**
     * Returns the last position in the linked list or null if empty
     * @return last position in linked list
     */
    public Position<E> last() {
        return position(trailer.getPrev());
    }

    /**
     * Returns the position immediately before Position p or null if p is first
     * @param p
     * @return position immediately before p
     * @throws IllegalArgumentException
     */
    public Position<E> before(Position<E> p) throws IllegalArgumentException {
        Node<E> node = validate(p);
        return position(node.getPrev());
    }

    /**
     * Returns position immediately after Postion p or null if p is last
     * @param p
     * @return
     * @throws IllegalArgumentException
     */
    public Position<E> after(Position<E> p) throws IllegalArgumentException {
        Node<E> node = validate(p);
        return position(node.getNext());
    }

    // ----------- end of public accessor utilities ----------------

    // ----------- private utilities -------------------------------

    /**
     * Addes element e to the linked list between the given nodes
     * @param e
     * @param predecessor
     * @param successor
     * @return position of element e
     */
    private Position<E> addBetween(E e, Node<E> predecessor, Node<E> successor) {
        // Create and link a new node
        Node<E> newest = new Node<E>(e, predecessor, successor);
        predecessor.setNext(newest);
        successor.setPrev(newest);
        size ++;
        return newest;
    }

    // ------------ end of private utilities -----------------------

    // ------------ public update methods --------------------------

    /**
     * Inserts element e at the front of the linked list and returns its new Position
     * @param e
     * @return Position of e
     */
    public Position<E> addFirst(E e) {
        return addBetween(e, header, header.getNext());
    }

    /**
     * Inserts element e at the back of the linked list and returns its new Position
     * @param e
     * @return Position of e
     */
    public Position<E> addLast(E e) {
        return addBetween(e, trailer.getPrev(), trailer);
    }

    /**
     * Inserts element e immediately before position p and returns position of e
     * @param p
     * @param e
     * @return position of e
     */
    public Position<E> addBefore(Position<E> p, E e) {
        Node<E> node = validate(p);
        return addBetween(e, node.getPrev(), node);
    }

    /**
     * Inserts element e immediately after e and returns position of e
     * @param p
     * @param e
     * @return
     */
    public Position<E> addAfter(Position<E> p, E e) {
        Node<E> node = validate(p);
        return addBetween(e, node, node.getNext());
    }

    /**
     * Sets element at position p and returns replaced element
     * @param p
     * @param e
     * @return replaced element
     */
    public E set(Position<E> p, E e) {
        Node<E> node = validate(p);
        E replaced = node.getElement();
        node.setElement(e);
        return replaced;
    }

    /**
     * Removes element at position p from linked list and returns the element
     * @param p
     * @return element at removed position
     */
    public E remove(Position<E> p) {
        Node<E> node = validate(p);
        Node<E> prevNode = node.getPrev();
        Node<E> nextNode = node.getNext();
        prevNode.setNext(nextNode);
        nextNode.setPrev(prevNode);
        // get removed node
        E removedElement = node.getElement();
        // convention for defunct node
        node.setNext(null);
        // help with garbage collection
        node.setElement(null);
        node.setPrev(null);
        size--;
        return removedElement;
    }

    // ---------------- nested PositionIterator class ---------------------------
    private class PositionIterator implements Iterator<Position<E>> {
        // position of the next element to report
        private Position<E> cursor = first();
        // position of last reported element
        private Position<E> recent = null;

        /**
         * Tests whether the iterator has a next object
         */
        public boolean hasNext() { return (cursor != null); }
        /**
         * Returns the next position in the iterator
         */
        public Position<E> next() throws NoSuchElementException {
            if(cursor == null) throw new NoSuchElementException("Nothing left");
            // element at this position might later be removed
            recent = cursor;
            cursor = after(cursor);
            return recent;
        }
        /**
         * Remove the element returned by the most recent call to next
         */
        public void remove() throws IllegalStateException {
            if(recent == null) throw new IllegalStateException("Nothing to remove");
            // remove from outer list
            LinkedPositionalList.this.remove(recent);
            // Do not allow remove again until next is called
            recent = null;
        }
    }
    // ---------------------- end of nested PositionIterator class -------------------

    // --------------------- nested PositionIterable class --------------------------
    private class PositionIterable implements Iterable<Position<E>> {
        public Iterator<Position<E>> iterator() { return new PositionIterator(); }
    }
    // -------------------- end of nested PositionIterable class --------------------

    /**
     * Returns an iterable representation of the list's positions
     * @return new instance of PositionIterable
     */
    public Iterable<Position<E>> positions() {
        // creates a new instance of the new PositionIterable class
        return new PositionIterable();
    }

    //  ---------------- nested ElementIterator class ----------
    /**
     * This class adapts the iteration produced by positions to return elements
     */
    private class ElementIterator implements Iterator<E> {
        Iterator<Position<E>> positionIterator = new PositionIterator();
        public boolean hasNext() { return positionIterator.hasNext(); }
        public E next() { return positionIterator.next().getElement(); }
        public void remove() {
            positionIterator.remove();
        }
    }

    // ------------- end of nested ElementIterator class ---------

    /**
     * Returns an iterator that iterates over elements
     * @return an iterator that iterates over elements
     */
    public Iterator<E> iterator() { return new ElementIterator(); }
}
