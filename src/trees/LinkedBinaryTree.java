package trees;

import util.Position;

/**
 * Concrete implementation of the Binary Tree ADT using a node-based linked structure
 *
 * Running time
 * size, isEmpty : O(1)
 * root, parent, left, child, sibling, children, numChildren : O(1)
 * isInternal, isExternal, isRoot : O(1)
 * addRoot, addLeft, addRight, set, attach, remove : O(1)
 * depth(p) : O(d_p + 1)
 * height : O(n)
 * @param <E> type parameter
 */
public class LinkedBinaryTree<E> extends AbstractBinaryTree<E> {

    // -- nested Node class --
    protected static class Node<E> implements Position<E> {
        private E element;
        private Node<E> parent;
        private Node<E> left;
        private Node<E> right;


        /**
         * Constructs a node with the given element and neighbours
         *
         * @param e          element
         * @param parentNode parent
         * @param leftChild  left
         * @param rightChild right
         */
        public Node(E e, Node<E> parentNode, Node<E> leftChild, Node<E> rightChild) {
            element = e;
            parent = parentNode;
            left = leftChild;
            right = rightChild;
        }

        // - accessor methods -
        public E getElement() {
            return element;
        }

        public Node<E> getParent() {
            return parent;
        }

        public Node<E> getLeft() {
            return left;
        }

        public Node<E> getRight() {
            return right;
        }
        // - end of accessor methods -

        // - update methods -
        public void setElement(E e) {
            element = e;
        }

        public void setParent(Node<E> parentNode) {
            parent = parentNode;
        }

        public void setLeft(Node<E> leftChild) {
            left = leftChild;
        }

        public void setRight(Node<E> rightChild) {
            right = rightChild;
        }
        // - end of update methods -

        // - utility methods -
        public boolean hasLeft() {
            return left != null;
        }
        public boolean hasRight() {
            return right != null;
        }
    }
    // -- end of nested Node class --

    /**
     * Factory function to create a new node storing element e
     */
    protected Node<E> createNode(E e, Node<E> parent, Node<E> left, Node<E> right) {
        return new Node<E>(e, parent, left, right);
    }

    // Linked BinaryTree instance variables
    protected Node<E> root = null;
    private int size = 0;

    // constructor
    /**
     * Constructs an empty binary tree
     */
    public LinkedBinaryTree() {}

    // -- nonpublic utilities --

    /**
     * Validate the position and return it as a node
     * @param p position
     * @return node
     * @throws IllegalArgumentException
     */
    protected Node<E> validate(Position<E> p) throws IllegalArgumentException {
        if(!(p instanceof Node))
            throw new IllegalArgumentException("Not valid position type");
        Node<E> node = (Node<E>) p;
        if(node.getParent() == node)
            // Our convention for defunct nodes
            throw new IllegalArgumentException("p is no longer in the tree");
        return node;
    }

    /**
     * Make a position invalid
     * @p position
     * @throws IllegalArgumentException
     */
    protected void makeInvalid(Position<E> p) throws IllegalArgumentException {
        Node<E> node = validate(p);
        // Our convention for defunct node
        node.setParent(node);
        // Help garbage collection
        node.left = null;
        node.right = null;
        node.element = null;
    }
    // -- end of nonpublic utilities --

    // -- accessor methods, not already implemented in AbstractBinaryTree --
    /**
     * Returns the number of nodes in the tree
     * @return number of nodes
     */
    public int size() { return size; }

    /**
     * Returns the root position of the tree
     * @return root position
     */
    public Position<E> root() {
        return root;
    }

    /**
     * Returns the position of p's parent (or null if p is root)
     * @param p position
     * @return position of p's parent
     */
    public Position<E> parent(Position<E> p) throws IllegalArgumentException {
        Node<E> node = validate(p);
        return node.getParent();
    }

    /**
     * Returns the position of p's left child
     * @param p position
     * @return position
     */
    public Position<E> left(Position<E> p) throws IllegalArgumentException {
        return validate(p).getLeft();
    }

    /**
     * Returns the position of p's right child
     * @param p position
     * @return position
     */
    public Position<E> right(Position<E> p) throws IllegalArgumentException {
        return validate(p).getRight();
    }

    // -- end of accessor methods --

    // -- update methods --

    /**
     * Places element e at the root of an empty tree and returns the root's position.
     * The tree now has a size of 1
     * @param e element
     * @return root's position
     * @throws IllegalStateException
     */
    public Position<E> addRoot(E e) throws IllegalStateException {
        if(!isEmpty()) throw new IllegalStateException("Tree must be empty");
        root = createNode(e, null, null, null);
        size = 1;
        return root;
    }

    /**
     * Adds a left child to a position
     * @param p position
     * @param e element
     * @return position of created left child
     * @throws IllegalArgumentException if left child already exists
     */
    public Position<E> addLeft(Position<E> p, E e) throws IllegalArgumentException{
        Node<E> parent = validate(p);
        if(parent.hasLeft()) throw new IllegalArgumentException("p already has a left child");
        Node<E> childNode = new Node<E>(e, parent, null, null);
        parent.setLeft(childNode);
        size++;
        return childNode;
    }

    /**
     * Adds a right child to a position
     * @param p position
     * @param e element
     * @return position of created right child
     * @throws IllegalArgumentException if right child already exists
     */
    public Position<E> addRight(Position<E> p, E e) throws IllegalArgumentException{
        Node<E> parent = validate(p);
        if(parent.hasRight()) throw new IllegalArgumentException("p already has a right child");
        Node<E> childNode = new Node<E>(e, parent, null, null);
        parent.setRight(childNode);
        size++;
        return childNode;
    }

    /**
     * Replaces element at position p and returns the previous element at that position
     * @param p position
     * @param e element
     * @return previous element
     * @throws IllegalArgumentException
     */
    public E set(Position<E> p, E e) throws IllegalArgumentException{
        Node<E> node = validate(p);
        E result = node.getElement();
        node.setElement(e);
        return result;
    }

    /**
     * Attaches trees t1 and t2 as left and right subtrees of external p and then sets
     * the two trees to empty trees
     * @param p external position
     * @param t1 tree 1, will be set as left child of p
     * @param t2 tree 2, will be set as right child of p
     * @throws IllegalArgumentException
     */
    public void attach(Position<E> p, LinkedBinaryTree<E> t1, LinkedBinaryTree<E> t2) throws IllegalArgumentException {
        Node<E> node = validate(p);
        if(isInternal(p))
            throw new IllegalArgumentException("p must be a leaf");
        size += t1.size + t2.size;
        if(!t1.isEmpty()) {
            t1.root.setParent(node);
            node.setLeft(t1.root);
            t1.root = null;
            t1.size = 0;
        }
        if(!t2.isEmpty()) {
            t2.root.setParent(node);
            node.setRight(t2.root);
            t2.root = null;
            t2.size = 0;
        }
    }

    /**
     * Removes the node at Position p and replaces it with its child, if any
     * @param p position
     * @return element
     * @throws IllegalArgumentException
     */
    public E remove(Position<E> p) throws IllegalArgumentException {
        Node<E> node = validate(p);
        if(numChildren(p) == 2)
            throw new IllegalArgumentException("Can not remove position p because it has two children");
        E e = node.getElement();
        Node<E> child = node.hasLeft() ? node.getLeft() : node.getRight();
        if(child != null) {
            Node<E> parentNode = node.getParent();
            if(parentNode != null) {
                if (parentNode.getLeft() == node)
                    parentNode.setLeft(child);
                else parentNode.setRight(child);
            } else {
                root = child;
            }
        }
        size--;
        makeInvalid(node);
        return e;
    }


    // -- end of update methods --
}
