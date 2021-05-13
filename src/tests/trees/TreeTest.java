package tests.trees;

import org.junit.Before;
import org.junit.Test;
import trees.LinkedBinaryTree;
import trees.Tree;
import util.Helpers;
import util.Position;

import java.util.Iterator;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class TreeTest {
    LinkedBinaryTree<Integer> tree;
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
    public void root() {
        assertThat(tree.root().getElement()).isEqualTo(2);
        assertThat(tree.root()).isEqualTo(root);
    }

    @Test
    public void parent() {
        assertThat(tree.parent(root)).isNull();
        assertThat(tree.parent(left)).isEqualTo(root);
        assertThat(tree.parent(right)).isEqualTo(root);
    }

    @Test
    public void children() {
        List<Position<Integer>> list1 = Helpers.toList(tree.children(root));
        assertThat(list1).hasSize(2);
        assertThat(list1.get(0).getElement()).isEqualTo(1);
        assertThat(list1.get(1).getElement()).isEqualTo(5);
        List<Position<Integer>> list2 = Helpers.toList(tree.children(left));
        assertThat(list2).hasSize(1);
        assertThat(list2.get(0).getElement()).isEqualTo(-4);
    }

    @Test
    public void numChildren() {
        assertThat(tree.numChildren(root)).isEqualTo(2);
        assertThat(tree.numChildren(right)).isEqualTo(1);
        assertThat(tree.numChildren(left)).isEqualTo(1);
        assertThat(tree.numChildren(tree.left(left))).isEqualTo(0);
        assertThat(tree.numChildren(tree.right(right))).isEqualTo(0);
    }

    @Test
    public void isInternal() {
        assertThat(tree.isInternal(root)).isTrue();
        assertThat(tree.isInternal(right)).isTrue();
        assertThat(tree.isInternal(left)).isTrue();
        assertThat(tree.isInternal(leftLeaf)).isFalse();
        assertThat(tree.isInternal(rightLeaf)).isFalse();
    }
    @Test
    public void isExternal() {
        assertThat(tree.isExternal(root)).isFalse();
        assertThat(tree.isExternal(right)).isFalse();
        assertThat(tree.isExternal(left)).isFalse();
        assertThat(tree.isExternal(leftLeaf)).isTrue();
        assertThat(tree.isExternal(rightLeaf)).isTrue();
    }

    @Test
    public void isRoot() {
        assertThat(tree.isRoot(root)).isTrue();
        assertThat(tree.isRoot(left)).isFalse();
        assertThat(tree.isRoot(rightLeaf)).isFalse();
    }

    @Test
    public void size() {
        assertThat(tree.size()).isEqualTo(5);
    }

    @Test
    public void isEmpty() {
        assertThat(tree.isEmpty()).isFalse();
        tree = new LinkedBinaryTree<>();
        assertThat(tree.isEmpty()).isTrue();
    }

    @Test
    public void iterator() {
        Iterator<Integer> iterator = tree.iterator();
        assertThat(iterator.hasNext()).isTrue();
        int i = 0;
        while(i++ <= 4) iterator.next();
        assertThat(iterator.hasNext()).isFalse();
    }

    @Test
    public void positions() {
        List<Position<Integer>> list = Helpers.toList(tree.positions());
        assertThat(list).hasSize(5);
        assertThat(list).contains(root);
        assertThat(list).contains(left);
        assertThat(list).contains(right);
        assertThat(list).contains(leftLeaf);
        assertThat(list).contains(rightLeaf);
    }

    @Test
    public void depth() {
        assertThat(tree.depth(root)).isEqualTo(0);
        assertThat(tree.depth(left)).isEqualTo(1);
        assertThat(tree.depth(right)).isEqualTo(1);
        assertThat(tree.depth(leftLeaf)).isEqualTo(2);
        assertThat(tree.depth(rightLeaf)).isEqualTo(2);
    }

    @Test
    public void height() {
        assertThat(tree.height()).isEqualTo(2);
        assertThat(tree.height(left)).isEqualTo(1);
        assertThat(tree.height(rightLeaf)).isEqualTo(0);
    }

    // -- traversal algorithms --
    @Test
    public void preorder() {
        List<Position<Integer>> list = Helpers.toList(tree.preorder());
        assertThat(list.get(0).getElement()).isEqualTo(2);
        assertThat(list.get(1).getElement()).isEqualTo(1);
        assertThat(list.get(2).getElement()).isEqualTo(-4);
        assertThat(list.get(3).getElement()).isEqualTo(5);
        assertThat(list.get(4).getElement()).isEqualTo(10);
    }

    @Test
    public void postorder() {
        List<Position<Integer>> list = Helpers.toList(tree.postorder());
        assertThat(list.get(0).getElement()).isEqualTo(-4);
        assertThat(list.get(1).getElement()).isEqualTo(1);
        assertThat(list.get(2).getElement()).isEqualTo(10);
        assertThat(list.get(3).getElement()).isEqualTo(5);
        assertThat(list.get(4).getElement()).isEqualTo(2);
    }

    @Test
    public void breadthfirst() {
        List<Position<Integer>> list = Helpers.toList(tree.breadthfirst());
        assertThat(list.get(0).getElement()).isEqualTo(2);
        assertThat(list.get(1).getElement()).isEqualTo(1);
        assertThat(list.get(2).getElement()).isEqualTo(5);
        assertThat(list.get(3).getElement()).isEqualTo(-4);
        assertThat(list.get(4).getElement()).isEqualTo(10);
    }


}
