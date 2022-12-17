/* Written by Kyrylo Pervushyn on 1 November 2022 */
import java.util.LinkedList;
import java.util.List;

public class BST <Key extends Comparable<Key>, Value> {

    private Node root;

    private class Node {
        private Key key;
        private Value val;
        private int count;
        private Node left, right;

        public Node(Key key, Value val, int count) {
            this.key = key;
            this.val = val;
            this.count = count;
        }
    }

    public Value get(Key key) {
        Node x = root;
        while (x != null) {
            int cmp = key.compareTo(x.key);
            if (cmp < 0) // go in left subtree
                x = x.left;
            else if (cmp > 0) // go in right subtree
                x = x.right;
            else // key is found
                return x.val;
        }
        return null;
    }

    /* Inserts a key */
    public void put(Key key, Value val) {
        root = put(root, key, val);
    }

    /* Deletes a key */
    public void delete(Key key) {
        root = delete(root, key);
    }

    public Iterable<Key> iterator() {
        final List<Key> list;
        list = new LinkedList<>();
        inorder(root, list);
        return list;
    }

    /* Minimum key */
    public Key min() {
        Node node = min(root);
        if (node == null)
            return null;
        return node.key;
    }

    /* Maximum key */
    public Key max() {
        Node node = max(root);
        if (node == null)
            return null;
        return node.key;
    }

    /* Deletes minimum key */
    public void deleteMin() {
        root = deleteMin(root);
    }

    /* Deletes maximum key */
    public void deleteMax() {
        root = deleteMax(root);
    }

    public boolean isEmpty() {
        return root == null;
    }

    /* Maximum key, less or equal to <key> */
    public Key floor(Key key) {
        Node x = floor(root, key);
        if (x == null)
            return null;
        return x.key;
    }

    /* Minimum key, more or equal to <key> */
    public Key ceiling(Key key) {
        Node x = ceiling(root, key);
        if (x == null)
            return null;
        return x.key;
    }

    /* Number of elements < key. */
    public int rank(Key key) {
        return rank(key, root);
    }

    /* Number of elements. */
    public int size() {
        return size(root);
    }

    /* Puts a value in the tree (recursive function) */
    private Node put(Node x, Key key, Value val) {
        if (x == null)
            return new Node(key, val, 1);
        int cmp = key.compareTo(x.key);
        if (cmp < 0) // recursive in left subtree
            x.left = put(x.left, key, val);
        else if (cmp > 0) // recursive in right subtree
            x.right = put(x.right, key, val);
        else // if <key> found, change value to <val>
            x.val = val;
        x.count = 1 + size(x.left) + size(x.right); // update size
        return x;
    }

    /* Returns node which will replace <x> after
    * <key> will be deleted (recursive function) */
    private Node delete(Node x, Key key) {
        if (x == null)
            return null;
        int cmp = key.compareTo(x.key);
        if (cmp < 0) // recursive in left subtree
            x.left = delete(x.left, key);
        else if (cmp > 0) // recursive in right subtree
            x.right = delete(x.right, key);
        else {
            // single subtree - no problems
            if (x.right == null) {
                x.count--;
                return x.left;
            }
            if (x.left == null) {
                x.count--;
                return x.right;
            }
            // find next to replace this node
            Node t = min(x.right);
            // delete minimum - we have pointer <t>
            x.right = delete(x.right, t.key);
            // and change <x> parameters
            x.key = t.key;
            x.val = t.val;
        }
        x.count = size(x.left) + size(x.right) + 1; // update size
        return x;
    }

    private Node floor(Node x, Key key) {
        if (x == null)
            return null;
        int cmp = key.compareTo(x.key);
        if (cmp == 0) // found equal key
            return x;
        if (cmp < 0)
            return floor(x.left, key);
        Node t = floor(x.right, key);
        if (t != null)
            return t;
        else
            return x;
    }

    private Node ceiling(Node x, Key key) {
        if (x == null)
            return null;
        int cmp = key.compareTo(x.key);
        if (cmp == 0)
            return x;
        if (cmp > 0)
            return ceiling(x.right, key);
        Node t = ceiling(x.left, key);
        if (t != null)
            return t;
        else
            return x;
    }

    /* Returns number of subtree nodes */
    private int size(Node x) {
        if (x == null)
            return 0;
        return x.count;
    }

    private int rank(Key key, Node x) {
        if (x == null)
            return 0;
        int cmp = key.compareTo(x.key);
        if (cmp < 0)
            return rank(key, x.left);
        else if (cmp > 0)
            return 1 + size(x.left) + rank(key, x.right);
        return size(x.left);
    }

    /* Goes to the minimum element */
    private Node min(Node x) {
        if (x == null)
            return null;
        Node c = x;
        while (c.left != null)
            c = c.left;
        return c;
    }

    /* Goes to the maximum element */
    private Node max(Node x) {
        if (x == null)
            return null;
        Node c = x;
        while (c.right != null)
            c = c.right;
        return c;
    }

    private Node deleteMin(Node x) {
        if (x == null)
            return null;
        if (x.left != null) {
            x.left = deleteMin(x.left);
            x.count = size(x.left) + size(x.right) + 1;
            return x;
        } else
            return x.right;
    }

    private Node deleteMax(Node x) {
        if (x == null)
            return null;
        if (x.right != null) {
            x.right = deleteMax(x.right);
            x.count = size(x.left) + size(x.right) + 1;
            return x;
        } else
            return x.left;
    }

    /* Inorder traversal (to form a list) */
    private void inorder(Node node, List<Key> list) {
        if (node == null || list == null)
            return;
        inorder(node.left, list);
        list.add(node.key);
        inorder(node.right, list);
    }
}