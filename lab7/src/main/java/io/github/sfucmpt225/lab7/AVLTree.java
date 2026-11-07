import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

/*
 * An AVL tree that stores int keys. Complete the seven methods in the
 * YOUR CODE section at the bottom of this file.
 */
public class AVLTree {

    // =========================================================================
    //  PROVIDED CODE: do not change anything in this section.
    //  I will build my test trees from these Node objects, so if you
    //  change the Node class or the root field, your methods cannot be tested (which means you will receive zero).
    // =========================================================================

    /** One node of the tree. */
    static class Node {
        int key;
        int height;   // the height of the subtree whose top node is this one (a leaf has height 0)
        Node left;    // the left child (smaller keys), or null if there is none
        Node right;   // the right child (larger keys), or null if there is none

        Node(int key) {
            this.key = key;
            this.height = 0;   // a new node is a leaf
        }
    }

    /** The root (top node) of the tree, or null if the tree is empty. */
    Node root;

    /** Creates an empty tree. */
    public AVLTree() {
        root = null;
    }

    /** Returns the height stored in node, or -1 if node is null (an empty subtree). */
    int height(Node node) {
        return node == null ? -1 : node.height;
    }

    /**
     * Adds key to the tree. Your method insert(Node, int) below does the work.
     *
     * @throws IllegalArgumentException if key is already in the tree
     */
    public void insert(int key) {
        root = insert(root, key);
    }

    /**
     * Builds a tree from its level-order listing (README.md explains it), e.g.
     *
     *     AVLTree tree = AVLTree.fromLevelOrder(30, 20, null, 10);
     *
     * Keys are listed level by level, left to right, and null marks a missing
     * child. Every node gets its correct height. The tree does not have to be
     * balanced, so you can also build the trees that rebalance has to fix.
     */
    public static AVLTree fromLevelOrder(Integer... keys) {
        AVLTree tree = new AVLTree();
        if (keys.length == 0 || keys[0] == null) {
            return tree;
        }
        tree.root = new Node(keys[0]);
        List<Node> nodes = new ArrayList<Node>();          // every node, in level order
        nodes.add(tree.root);
        Queue<Node> waiting = new ArrayDeque<Node>();      // nodes whose children come next
        waiting.add(tree.root);
        int i = 1;
        while (i < keys.length) {
            if (waiting.isEmpty()) {
                throw new IllegalArgumentException("The key at position " + i
                        + " has no parent node in " + Arrays.toString(keys));
            }
            Node parent = waiting.remove();
            if (keys[i] != null) {
                parent.left = new Node(keys[i]);
                waiting.add(parent.left);
                nodes.add(parent.left);
            }
            i++;
            if (i < keys.length && keys[i] != null) {
                parent.right = new Node(keys[i]);
                waiting.add(parent.right);
                nodes.add(parent.right);
            }
            i++;
        }
        // In level order every child comes after its parent, so going backwards
        // gives each node its height after its children have theirs.
        for (int j = nodes.size() - 1; j >= 0; j--) {
            Node node = nodes.get(j);
            node.height = Math.max(tree.height(node.left), tree.height(node.right)) + 1;
        }
        return tree;
    }

    /** Returns the tree in level-order notation with heights, e.g. "[20:1, 10:0, 30:0]". */
    @Override
    public String toString() {
        return toString(root);
    }

    /**
     * Returns the subtree whose top node is node, in level-order notation with
     * heights: "[20:1, 10:0, 30:0]" is 20 (height 1) with the leaves 10 and 30.
     * Returns "[]" if node is null. If a wrong link leads to a node that has
     * already been listed, it writes "(20 again)" instead of looping forever.
     */
    static String toString(Node node) {
        List<String> items = new ArrayList<String>();
        Set<Node> listed = Collections.newSetFromMap(new IdentityHashMap<Node, Boolean>());
        Queue<Node> queue = new LinkedList<Node>();   // a LinkedList can hold null
        queue.add(node);
        while (!queue.isEmpty()) {
            Node n = queue.remove();
            if (n == null) {
                items.add("null");
            } else if (!listed.add(n)) {
                items.add("(" + n.key + " again)");
            } else {
                items.add(n.key + ":" + n.height);
                queue.add(n.left);
                queue.add(n.right);
            }
        }
        while (!items.isEmpty() && items.get(items.size() - 1).equals("null")) {
            items.remove(items.size() - 1);   // nulls at the end add nothing
        }
        return "[" + String.join(", ", items) + "]";
    }

    // =========================================================================
    //  YOUR CODE: complete the seven methods below, in this order. Each one
    //  may call the ones before it.
    //
    //  - Replace each "throw new UnsupportedOperationException(...)" line
    //    with your code.
    //  - You may add private helper methods.
    //  - Do not add fields (instance or static variables).
    // =========================================================================

    /**
     * 1. Sets node.height from the heights stored in its two children: one
     *    more than the larger of height(node.left) and height(node.right).
     *    Trust the children's stored heights: do not walk further down the
     *    tree. node is never null.
     */
    void updateHeight(Node node) {
        // TODO
        throw new UnsupportedOperationException("updateHeight() is not implemented yet");
    }

    /**
     * 2. Returns the balance factor of node: height(node.left) - height(node.right),
     *    using the stored heights. It is positive when the left side is
     *    taller. Returns 0 if node is null.
     */
    int balanceFactor(Node node) {
        // TODO
        throw new UnsupportedOperationException("balanceFactor() is not implemented yet");
    }

    /**
     * 3. Rotates right around node, and returns the new top node of this
     *    subtree (node's old left child):
     *
     *            node                child
     *           /    \              /     \
     *        child    C    ==>     A      node
     *        /   \                        /   \
     *       A     B                      B     C
     *
     *    Then updates the heights of node and child, in that order. node and
     *    node.left are never null. Do not change root: the caller links the
     *    returned node into the tree.
     */
    Node rotateRight(Node node) {
        // TODO
        throw new UnsupportedOperationException("rotateRight() is not implemented yet");
    }

    /**
     * 4. Rotates left around node, and returns the new top node of this
     *    subtree (node's old right child):
     *
     *         node                    child
     *        /    \                  /     \
     *       A    child     ==>    node      C
     *            /   \            /   \
     *           B     C          A     B
     *
     *    Then updates the heights of node and child, in that order. node and
     *    node.right are never null. Do not change root.
     */
    Node rotateLeft(Node node) {
        // TODO
        throw new UnsupportedOperationException("rotateLeft() is not implemented yet");
    }

    /**
     * 5. Brings node's height up to date and, if node's balance factor is +2
     *    or -2, fixes it with one or two rotations (see the table in
     *    README.md). Returns the top node of this subtree afterwards: node
     *    itself if no rotation was needed. node is never null, and its two
     *    subtrees are AVL trees with correct heights.
     */
    Node rebalance(Node node) {
        // TODO
        throw new UnsupportedOperationException("rebalance() is not implemented yet");
    }

    /**
     * 6. Adds key to the subtree whose top node is node (null for an empty
     *    subtree), and returns the top node of that subtree afterwards: a
     *    rotation can change it. Every node on the way back up must go
     *    through rebalance.
     *
     * @throws IllegalArgumentException if key is already in the subtree
     */
    Node insert(Node node, int key) {
        // TODO
        throw new UnsupportedOperationException("insert() is not implemented yet");
    }

    /**
     * 7. Returns true if key is in the tree, and false otherwise. Must not
     *    change the tree.
     */
    public boolean contains(int key) {
        // TODO
        throw new UnsupportedOperationException("contains() is not implemented yet");
    }
}
