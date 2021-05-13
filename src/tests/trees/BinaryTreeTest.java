package tests.trees;

import org.junit.Before;
import org.junit.Test;
import trees.BinaryTree;
import trees.LinkedBinaryTree;
import util.Helpers;
import util.Position;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class BinaryTreeTest {
    BinaryTree<Integer> tree;
    Position<Integer> root;
    Position<Integer> left;
    Position<Integer> right;
    Position<Integer> leftLeaf;
    Position<Integer> rightLeaf;

    @Before
    public void createTree() {
        tree = new LinkedBinaryTree<>();
        root = tree.addRoot(2);
        left = tree.addLeft(root, 1);
        right = tree.addRight(root,5);
        leftLeaf = tree.addLeft(left, -4);
        rightLeaf = tree.addRight(right, 10);
        /**
         * representation
         *        2
         *       / \
         *      1   5
         *     /     \
         *    -4      10
         */
    }

    @Test
    public void left() {
        assertThat(tree.left(root)).isEqualTo(left);
        assertThat(tree.left(left)).isEqualTo(leftLeaf);
        assertThat(tree.left(right)).isNull();
        assertThat(tree.left(leftLeaf)).isNull();
    }

    @Test
    public void right() {
        assertThat(tree.right(root)).isEqualTo(right);
        assertThat(tree.right(right)).isEqualTo(rightLeaf);
        assertThat(tree.right(left)).isNull();
        assertThat(tree.right(rightLeaf)).isNull();
    }

    @Test
    public void sibling(){
        assertThat(tree.sibling(left)).isEqualTo(right);
        assertThat(tree.sibling(leftLeaf)).isNull();
        assertThat(tree.sibling(root)).isNull();
    }

    @Test
    public void addRoot() {
        BinaryTree<String> myTree = new LinkedBinaryTree<>();
        Position<String> myRoot = myTree.addRoot("Good");
        assertThat(myRoot).isEqualTo(myTree.root());
        assertThat(myTree.size()).isEqualTo(1);
    }

    @Test
    public void addLeft() {
        int size = tree.size();
        Position<Integer> right_left = tree.addLeft(right, 8);
        assertThat(tree.size()).isEqualTo(size + 1);
        assertThat(right_left.getElement()).isEqualTo(8);
        assertThat(tree.parent(right_left)).isEqualTo(right);
        assertThat(tree.left(right)).isEqualTo(right_left);
    }

    @Test
    public void addRight() {
        int size = tree.size();
        Position<Integer> left_left_right = tree.addRight(leftLeaf, 5);
        assertThat(tree.size()).isEqualTo(size + 1);
        assertThat(left_left_right.getElement()).isEqualTo(5);
        assertThat(tree.parent(left_left_right)).isEqualTo(leftLeaf);
        assertThat(tree.right(leftLeaf)).isEqualTo(left_left_right);
    }

    @Test
    public void set() {
        int old = tree.set(right, 100);
        assertThat(old).isEqualTo(5);
        assertThat(right.getElement()).isEqualTo(100);
    }

    @Test
    public void attach() {
        LinkedBinaryTree<Integer> t1 = new LinkedBinaryTree<>();
        Position<Integer> t1Root = t1.addRoot(100);
        Position<Integer> t1Left = t1.addLeft(t1Root, 50);
        Position<Integer> t1Right = t1.addRight(t1Root, 150);

        LinkedBinaryTree<Integer> t2 = new LinkedBinaryTree<>();
        Position<Integer> t2Root = t2.addRoot(800);
        Position<Integer> t2Left = t2.addLeft(t2Root, 850);
        Position<Integer> t2Right = t2.addRight(t2Root, 950);

        tree.attach(rightLeaf, t1, t2);
        assertThat(tree.size()).isEqualTo(11);
        assertThat(tree.parent(t1Root)).isEqualTo(rightLeaf);
        assertThat(tree.parent(t2Root)).isEqualTo(rightLeaf);
        assertThat(tree.left(rightLeaf)).isEqualTo(t1Root);
        assertThat(tree.right(rightLeaf)).isEqualTo(t2Root);
        assertThat(tree.height()).isEqualTo(4);
        assertThat(tree.depth(t2Left)).isEqualTo(4);
        assertThat(tree.depth(t1Right)).isEqualTo(4);
        assertThat(tree.children(t1Root)).contains(t1Left, t1Right);
        assertThat(tree.children(t2Root)).contains(t2Left, t2Right);
    }

    @Test
    public void remove() {
        // test removeing leaf
        int size = tree.size();
        int p = tree.remove(leftLeaf);
        assertThat(p).isEqualTo(-4);
        assertThat(tree.size()).isEqualTo(--size);
        assertThat(tree.left(left)).isNull();

        // test removing internal
        p = tree.remove(right);
        assertThat(p).isEqualTo(5);
        assertThat(tree.size()).isEqualTo(--size);
        assertThat(tree.right(root)).isEqualTo(rightLeaf);
        assertThat(tree.parent(rightLeaf)).isEqualTo(root);

        // test removing root
        tree.remove(rightLeaf);
        tree.remove(root);
        assertThat(tree.root()).isEqualTo(left);
        assertThat(tree.size()).isEqualTo(size - 2);
        assertThat(tree.right(tree.root())).isNull();

    }

    @Test
    public void positions() {
        // Binary tree defaults to inorder traversal for positions iterable
        inorder();
    }

    // -- inorder traversal --
    @Test
    public void inorder() {
        // Binary tree defaults to inorder traversal for positions iterable
        List<Position<Integer>> list = Helpers.toList(((LinkedBinaryTree<Integer>) tree).inorder());
        assertThat(list.get(0).getElement()).isEqualTo(-4);
        assertThat(list.get(1).getElement()).isEqualTo(1);
        assertThat(list.get(2).getElement()).isEqualTo(2);
        assertThat(list.get(3).getElement()).isEqualTo(5);
        assertThat(list.get(4).getElement()).isEqualTo(10);
    }


}
