import java.util.Objects;
import java.util.function.Supplier;

/*
 * Runs some checks on the methods in AVLTree.java and prints PASS or FAIL
 * for each one. 
 * Add your own checks: copy a check(...) line  and change the tree 
 * and the answer.
 * Trees are written in level order, and 20:1 means the key 20 with a stored
 * height of 1
 */
public class Main {

    public static void main(String[] args) {
        section("updateHeight(Node)");
        check("[10], stored height 5", "[10:0]", () -> updated(5, 10));
        check("[20, 10], stored height 0", "[20:1, 10:0]", () -> updated(0, 20, 10));
        check("[20, 10, 30, null, null, null, 40], stored height 0", "[20:2, 10:0, 30:1, null, null, null, 40:0]",
                () -> updated(0, 20, 10, 30, null, null, null, 40));

        section("balanceFactor(Node)");
        check("null", 0, () -> new AVLTree().balanceFactor(null));
        check("[10]", 0, () -> balance(10));
        check("[20, 10]", 1, () -> balance(20, 10));
        check("[10, null, 20, null, 30]", -2, () -> balance(10, null, 20, null, 30));
        check("[40, 20, 60, 10, 30]", 1, () -> balance(40, 20, 60, 10, 30));

        section("rotateRight(Node)");
        check("[30, 20, null, 10]", "[20:1, 10:0, 30:0]", () -> rotatedRight(30, 20, null, 10));
        check("[50, 30, 60, 20, 40]", "[30:2, 20:0, 50:1, null, null, 40:0, 60:0]",
                () -> rotatedRight(50, 30, 60, 20, 40));

        section("rotateLeft(Node)");
        check("[10, null, 20, null, 30]", "[20:1, 10:0, 30:0]", () -> rotatedLeft(10, null, 20, null, 30));
        check("[20, 10, 40, null, null, 30, 50]", "[40:2, 20:1, 50:0, 10:0, 30:0]",
                () -> rotatedLeft(20, 10, 40, null, null, 30, 50));

        section("rebalance(Node)");
        check("[20, 10, 30]: balanced, no rotation", "[20:1, 10:0, 30:0]", () -> rebalanced(20, 10, 30));
        check("[30, 20, null, 10]: Left-Left", "[20:1, 10:0, 30:0]", () -> rebalanced(30, 20, null, 10));
        check("[10, null, 20, null, 30]: Right-Right", "[20:1, 10:0, 30:0]", () -> rebalanced(10, null, 20, null, 30));
        check("[30, 10, null, null, 20]: Left-Right", "[20:1, 10:0, 30:0]", () -> rebalanced(30, 10, null, null, 20));
        check("[10, null, 30, 20]: Right-Left", "[20:1, 10:0, 30:0]", () -> rebalanced(10, null, 30, 20));
        check("[50, 30, 60, 20, 40, null, null, 10]: Left-Left", "[30:2, 20:1, 50:1, 10:0, null, 40:0, 60:0]",
                () -> rebalanced(50, 30, 60, 20, 40, null, null, 10));

        section("insert(int)");
        check("42 into an empty tree", "[42:0]", () -> inserted(42));
        check("10, 20, 30", "[20:1, 10:0, 30:0]", () -> inserted(10, 20, 30));
        check("30, 10, 20", "[20:1, 10:0, 30:0]", () -> inserted(30, 10, 20));
        check("50, 30, 70, 20, 10", "[50:2, 20:1, 70:0, 10:0, 30:0]", () -> inserted(50, 30, 70, 20, 10));
        check("1 to 7, in increasing order", "[4:2, 2:1, 6:1, 1:0, 3:0, 5:0, 7:0]", () -> inserted(1, 2, 3, 4, 5, 6, 7));
        checkThrows("20, 10, 30, then 10 again", IllegalArgumentException.class, () -> inserted(20, 10, 30, 10));
        check("20, 10, 30, then 10 again: the tree is unchanged", "[20:1, 10:0, 30:0]", () -> afterDuplicate(20, 10, 30, 10));
        check("1 to 1000, in increasing order", "a valid AVL tree of height 9", () -> describe(insertRange(1, 1000)));

        section("contains(int)");
        check("empty tree, 7", false, () -> new AVLTree().contains(7));
        check("[20, 10, 30], 20", true, () -> AVLTree.fromLevelOrder(20, 10, 30).contains(20));
        check("[20, 10, 30], 10", true, () -> AVLTree.fromLevelOrder(20, 10, 30).contains(10));
        check("[20, 10, 30], 30", true, () -> AVLTree.fromLevelOrder(20, 10, 30).contains(30));
        check("[20, 10, 30], 25", false, () -> AVLTree.fromLevelOrder(20, 10, 30).contains(25));
        check("[20, 10, 30], 5", false, () -> AVLTree.fromLevelOrder(20, 10, 30).contains(5));

        // Add your own checks here. For example:
        // check("2, 1, 3", "[2:1, 1:0, 3:0]", () -> inserted(2, 1, 3));

        System.out.println();
        System.out.println("Passed " + passed + " of " + total + " checks.");
        if (errors > 0) {
            System.out.println("Careful: " + errors + " check(s) threw an exception. When your work is marked,"
                    + " a method that throws an exception the README does not allow scores 0.");
        }
    }

    // -------------------------------------------------------------------------
    //  Small helpers that build a tree, call one method and return the result.
    // -------------------------------------------------------------------------

    /** Builds the tree, gives its root a wrong stored height, calls updateHeight on the root and returns the tree. */
    static String updated(int storedHeight, Integer... levelOrder) {
        AVLTree tree = AVLTree.fromLevelOrder(levelOrder);
        tree.root.height = storedHeight;
        tree.updateHeight(tree.root);
        return tree.toString();
    }

    /** Builds the tree and returns the balance factor of its root. */
    static int balance(Integer... levelOrder) {
        AVLTree tree = AVLTree.fromLevelOrder(levelOrder);
        return tree.balanceFactor(tree.root);
    }

    /** Builds the tree, rotates right around its root and returns the subtree that rotateRight returned. */
    static String rotatedRight(Integer... levelOrder) {
        AVLTree tree = AVLTree.fromLevelOrder(levelOrder);
        return AVLTree.toString(tree.rotateRight(tree.root));
    }

    /** Builds the tree, rotates left around its root and returns the subtree that rotateLeft returned. */
    static String rotatedLeft(Integer... levelOrder) {
        AVLTree tree = AVLTree.fromLevelOrder(levelOrder);
        return AVLTree.toString(tree.rotateLeft(tree.root));
    }

    /**
     * Builds the tree, makes the root's stored height out of date (as it is
     * just after an insert below it), calls rebalance on the root and returns
     * the subtree that rebalance returned.
     */
    static String rebalanced(Integer... levelOrder) {
        AVLTree tree = AVLTree.fromLevelOrder(levelOrder);
        tree.root.height = 0;
        return AVLTree.toString(tree.rebalance(tree.root));
    }

    /** Inserts the keys into an empty tree, in this order, and returns the tree. */
    static String inserted(int... keys) {
        AVLTree tree = new AVLTree();
        for (int key : keys) {
            tree.insert(key);
        }
        return tree.toString();
    }

    /** Inserts first, first + 1, ..., last into an empty tree. */
    static AVLTree insertRange(int first, int last) {
        AVLTree tree = new AVLTree();
        for (int key = first; key <= last; key++) {
            tree.insert(key);
        }
        return tree;
    }

    /** Inserts the keys, then inserts the last one again (a duplicate), and returns the tree afterwards. */
    static String afterDuplicate(int... keys) {
        AVLTree tree = new AVLTree();
        for (int i = 0; i < keys.length - 1; i++) {
            tree.insert(keys[i]);
        }
        try {
            tree.insert(keys[keys.length - 1]);
        } catch (IllegalArgumentException e) {
            // expected: the key is already in the tree
        }
        return tree.toString();
    }

    /** "a valid AVL tree of height h", or what is wrong with the tree. */
    static String describe(AVLTree tree) {
        try {
            return "a valid AVL tree of height " + verify(tree.root, Long.MIN_VALUE, Long.MAX_VALUE);
        } catch (IllegalStateException e) {
            return e.getMessage();
        }
    }

    /**
     * Checks the subtree whose top node is node: its keys are in order and
     * between low and high, every stored height is right and every node is
     * balanced. Returns its real height, or throws IllegalStateException.
     */
    static int verify(AVLTree.Node node, long low, long high) {
        if (node == null) {
            return -1;
        }
        if (node.key <= low || node.key >= high) {
            throw new IllegalStateException("not a binary search tree: " + node.key
                    + " is on the wrong side of a node above it");
        }
        int left = verify(node.left, low, node.key);
        int right = verify(node.right, node.key, high);
        int real = 1 + Math.max(left, right);
        if (node.height != real) {
            throw new IllegalStateException("node " + node.key + " stores height " + node.height
                    + ", but its subtree has height " + real);
        }
        if (Math.abs(left - right) > 1) {
            throw new IllegalStateException("node " + node.key + " is not balanced (balance factor " + (left - right) + ")");
        }
        return real;
    }

    // -------------------------------------------------------------------------
    //  Helpers that run a check and print the result. You do not need to read
    //  them to use them.
    // -------------------------------------------------------------------------

    private static int passed = 0;
    private static int total = 0;
    private static int errors = 0;

    /** Prints a heading for the checks of one method. */
    static void section(String method) {
        System.out.println();
        System.out.println(method);
    }

    /** Runs code and checks that it returns expected. */
    static void check(String what, Object expected, Supplier<Object> code) {
        total++;
        Object actual;
        try {
            actual = code.get();
        } catch (RuntimeException | Error e) {
            reportException(what, e);
            return;
        }
        if (Objects.equals(expected, actual)) {
            passed++;
            print("PASS", what + ": " + actual);
        } else {
            print("FAIL", what + ": expected " + expected + ", got " + actual);
        }
    }

    /** Runs code and checks that it throws an exception of the given type. */
    static void checkThrows(String what, Class<? extends Throwable> expected, Supplier<Object> code) {
        total++;
        Object actual;
        try {
            actual = code.get();
        } catch (RuntimeException | Error e) {
            if (expected.isInstance(e)) {
                passed++;
                print("PASS", what + ": threw " + e.getClass().getSimpleName());
            } else {
                reportException(what, e);
            }
            return;
        }
        print("FAIL", what + ": expected " + expected.getSimpleName() + " to be thrown, but got " + actual);
    }

    private static void reportException(String what, Throwable e) {
        String message = e.getMessage() == null ? "" : e.getMessage();
        if (e instanceof UnsupportedOperationException && message.endsWith("not implemented yet")) {
            print("NOT DONE", what + ": " + message);
            return;
        }
        errors++;
        String text = what + ": threw " + e.getClass().getSimpleName();
        for (StackTraceElement frame : e.getStackTrace()) {
            if (frame.getClassName().startsWith("AVLTree")) {
                text += " at line " + frame.getLineNumber() + " of AVLTree.java (in " + frame.getMethodName() + ")";
                break;
            }
        }
        if (e instanceof StackOverflowError) {
            text += " (infinite recursion? Check your base case)";
        } else if (!message.isEmpty()) {
            text += ": " + message;
        }
        print("ERROR", text);
    }

    private static void print(String status, String text) {
        System.out.printf("  %-9s %s%n", status, text);
    }
}
