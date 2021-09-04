package map;

import tree.BinarySearchTree;
import tree.RBTree;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.Queue;

public class TreeMap<K, V> implements Map<K, V> {

    private static final boolean RED = false;
    private static final boolean BLACK = true;

    private int size;
    private Node<K, V> root;
    private Node<K, V> parent;

    private Comparator<K> comparator;

    public TreeMap() {
        this(null);
    }

    public TreeMap(Comparator comparator) {
        this.comparator = comparator;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    @Override
    public V put(K key, V value) {
        notNullCheck(key);

        if (root == null) {
            root = new Node<K, V>(key, value, null);
            size++;
            // 添加节点后处理
            afterPut(root);
            return null;
        }

        Node<K, V> parent = root;
        Node<K, V>  node = root;
        int cmp = 0;
        while(node != null) {
            cmp = compare(key, node.key);
            parent = node;
            if (cmp > 0) {
                node = node.right;
            } else if(cmp < 0) {
                node = node.left;
            } else {
                node.key = key;
                V oldValue = node.value;
                node.value = value;
                return oldValue;
            }
        }

        Node<K, V>  newNode = new Node<>(key, value, parent);
        if (cmp > 0) {
            parent.right = newNode;
        } else {
            parent.left = newNode;
        }
        size++;
        afterPut(newNode);
        return null;
    }

    @Override
    public V get(K key) {
        Node<K, V> node = node(key);
        return node != null ?  node.value : null;
    }

    @Override
    public V remove(K key) {
        return remove(node(key));
    }

    @Override
    public boolean containsKey(K key) {
        return node(key) != null;
    }

    @Override
    public boolean containsValue(V value) {
        if (root == null) {
            return false;
        }

        Queue<Node<K, V>> queue = new LinkedList<>();
        queue.offer(root);
        while(!queue.isEmpty()) {
            Node<K, V> node = queue.poll();
            if (valEquals(node.value, value)) {
                return true;
            }
            if (node.left != null) {
                queue.offer(node.left);
            }
            if (node.right != null) {
                queue.offer(node.right);
            }
        }

        return false;
    }

    @Override
    public void traversal(Visitor<K, V> visitor) {
        if (visitor == null) {
            return;
        }
        traversal(root, visitor);
    }

    private void traversal(Node<K, V> node, Visitor<K, V> visitor) {
        if (node == null || visitor.stop) {
            return;
        }
        traversal(node.left, visitor);
        if (visitor.stop) {
            return;
        }
        visitor.visit(node.key, node.value);
        traversal(node.right, visitor);
    }

    private boolean valEquals(V v1, V v2) {
        return v1 == null ? v2 == null : v1.equals(v2);
    }

    /**
     * 查找后继节点
     * @param node
     * @return
     */
    private Node<K, V> successor(Node<K, V> node) {
        if (node == null) {
            return null;
        }

        // 有右子树
        if (node.right != null) {
            node = node.right;
            while(node.left != null) {
                node = node.left;
            }
            return node;
        }

        // 没有右子树
        while(node.parent != null && node == node.parent.right) {
            node = node.parent;
        }

        return node.parent;
    }

    private V remove(Node<K, V> node) {
        if (node == null) {
            return null;
        }

        size--;

        V oldVulue = node.value;

        // 处理度为2的节点
        if (node.hasTwoChild()) {
            Node<K, V> sNode = successor(node);
            node.key = sNode.key;
            node.value = sNode.value;
            node = sNode;
        }

        // 删除度为0或1的节点
        // 要替代的节点
        Node<K, V> replacement = node.left != null ? node.left : node.right;
        if (replacement != null) {      // 度为1的节点
            replacement.parent = node.parent;
            if (node.parent == null) {
                root = replacement;
            } else if (node == node.parent.left) {
                node.parent.left = replacement;
            } else {
                node.parent.right = replacement;
            }

            afterRemove(node, replacement);
        } else if (node.parent == null) {       // 叶子节点且是根节点
            root = null;

            afterRemove(node, null);
        } else {        // 叶子节点非根节点
            if (node == node.parent.left) {
                node.parent.left = null;
            } else {
                node.parent.right = null;
            }

            afterRemove(node, null);
        }
        return oldVulue;
    }

    private void afterRemove(Node<K, V> node, Node<K, V> replacement) {
        // 如果删除的节点是红色
        if (isRed(node)) {
            return;
        }

        // 如果删除的节点是黑色且只有一个红色子节点
        if (isRed(replacement)) {
            black(replacement);
            return;
        }

        // 删除的是根节点
        Node<K, V> parent = node.parent;
        if (parent == null) {
            return;
        }

        // 删除的是黑色叶子节点
        // 判断被删除的节点是左还是右
        boolean left = parent.left == null || node.isLeftChild();
        Node<K, V> sibling = left ? parent.right : parent.left;
        if (left) {     // 被删除的节点在左边， 兄弟节点在右边
            if (isRed(sibling)) {   // 兄弟节点是红色
                black(sibling);
                red(parent);
                rotateLeft(parent);
                // 更换兄弟
                sibling = parent.right;
            }

            // 兄弟节点必然是黑色
            if (isBlack(parent.left) && isBlack(parent.right)) {        // 兄弟节点没有红色子节点，父节点要向下跟兄弟节点合并
                boolean parentBlack = isBlack(parent);
                black(parent);
                red(sibling);
                if (parentBlack) {
                    afterRemove(parent, null);
                }
            } else {    // 兄弟节点至少有一个红色子节点, 向兄弟节点借元素
                if (isBlack(sibling.right)) {    // 兄弟节点左边是黑色，兄弟要先旋转
                    rotateRight(sibling);
                    sibling = parent.left;
                }
                color(sibling, colorOf(parent));
                black(sibling.right);
                black(parent);
                rotateLeft(parent);
            }
        } else {
            if (isRed(sibling)) {   // 兄弟节点是红色
                black(sibling);
                red(parent);
                rotateRight(parent);
                // 更换兄弟
                sibling = parent.left;
            }

            // 兄弟节点必然是黑色
            if (isBlack(parent.left) && isBlack(parent.right)) {        // 兄弟节点没有红色子节点，父节点要向下跟兄弟节点合并
                boolean parentBlack = isBlack(parent);
                black(parent);
                red(sibling);
                if (parentBlack) {
                    afterRemove(parent, null);
                }
            } else {    // 兄弟节点至少有一个红色子节点, 向兄弟节点借元素
                if (isBlack(sibling.left)) {    // 兄弟节点左边是黑色，兄弟要先旋转
                    rotateLeft(sibling);
                    sibling = parent.left;
                }
                color(sibling, colorOf(parent));
                black(sibling.left);
                black(parent);
                rotateRight(parent);
            }
        }

    }

    private void afterPut(Node<K, V> node) {
        Node<K, V> parent = node.parent;

        // node是根节点
        if (parent == null) {
            black(node);
            return;
        }

        if (isBlack(parent)) {
            return;
        }

        // 叔父节点
        Node<K, V> uncle = parent.sibling();
        // 祖父节点
        Node<K, V> grand = parent.parent;
        if (isRed(uncle)) {
            black(parent);
            black(uncle);
            red(grand);
            afterPut(grand);
            return;
        }

        // 叔父节点不是红色
        if (parent.isLeftChild()) {     // L
            red(grand);
            if (node.isLeftChild()) {       // LL
                black(parent);
            } else {        // LR
                black(node);
                rotateLeft(parent);
            }
            rotateRight(grand);
        } else {        // R
            red(grand);
            if (node.isLeftChild()) {       // RL
                black(node);
                rotateRight(parent);
            } else {        // RR
                black(parent);
            }
            rotateLeft(grand);
        }
    }

    private void notNullCheck(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Key must not null!");
        }
    }

    private int compare(K k1, K k2) {
        if (comparator != null) {
            return comparator.compare(k1, k2);
        }
        return ((Comparable<K>)k1).compareTo(k2);
    }

    private Node<K, V> color(Node<K, V> node, boolean color) {
        if (node == null) {
            return node;
        }
        node.color = color;
        return node;
    }

    private Node<K, V> red(Node<K, V> node) {
        return color(node, RED);
    }

    private Node<K, V> black(Node<K, V> node) {
        return color(node, BLACK);
    }

    private boolean colorOf(Node<K, V> node) {
        return node == null ? BLACK : node.color;
    }

    private boolean isBlack(Node<K, V> node) {
        return colorOf(node) == BLACK;
    }

    private boolean isRed(Node<K, V> node) {
        return colorOf(node) == RED;
    }

    /**
     * 左旋
     * @param g grand 祖父节点
     * @param g parent 父节点
     */
    private void rotateLeft(Node<K, V> g) {
        Node<K, V> p = g.right;
        Node<K, V> child = p.left;

        g.right = p.left;
        p.left = g;

        afterRotate(g, p, child);
    }

    /**
     * 右旋
     * @param g grand 祖父节点
     * @param g parent 父节点
     */
    private void rotateRight(Node<K, V> g) {
        Node<K, V> p = g.left;
        Node<K, V> child = p.right;

        g.left = p.right;
        p.right = g;

        afterRotate(g, p, child);
    }

    private void afterRotate(Node<K, V> g, Node<K, V> p, Node<K, V> child) {
        // 更新 parent父节点 使parent成为子树根节点
        p.parent = g.parent;
        if (g.isLeftChild()) {
            p.parent.left = p;
        } else if (g.isRightChild()) {
            p.parent.right = p;
        } else {
            root = p;
        }

        // 更新child 父节点
        if (child != null) {
            child.parent = g;
        }

        // 更新 grand 父节点
        g.parent = p;
    }

    /**
     * 查找元素对应节点
     * @param key
     * @return
     */
    private Node<K, V> node(K key) {
        if (root == null) {
            return null;
        }
        Node<K, V> node = root;
        while(node != null) {
            int cmp = compare(node.key, key);
            if (cmp == 0) {
                return node;
            } else if (cmp > 0) {
                node = node.left;
            } else {
                node = node.right;
            }
        }
        return null;
    }

    private static class Node<K, V> {
        K key;
        V value;
        Node<K, V> left;
        Node<K, V> right;
        Node<K, V> parent;
        boolean color = RED;

        public Node(K key, V value, Node<K, V> parent) {
            this.key = key;
            this.value = value;
            this.parent = parent;
        }

        /**
         * 判断是否是叶子节点
         * @return
         */
        public boolean isLeaf() {
            return left == null && right == null;
        }

        /**
         * 判断是否是度为2的节点
         * @return
         */
        public boolean hasTwoChild() {
            return left != null && right != null;
        }

        /**
         * 判断是否为左孩子
         * @return
         */
        public boolean isLeftChild() {
            return parent != null && this == parent.left;
        }

        /**
         * 判断是右孩子
         * @return
         */
        public boolean isRightChild() {
            return parent != null && this == parent.right;
        }

        /**
         * 查找兄弟节点
         * @return
         */
        public Node<K, V> sibling() {
            if (isLeftChild()) {
                return parent.right;
            }
            if (isRightChild()) {
                return parent.left;
            }
            return null;
        }
    }
}
