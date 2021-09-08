package map;

import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;

public class HashMap<K, V> implements Map<K, V> {

    private static final boolean RED = false;
    private static final boolean BLACK = true;
    private int size;
    private Node<K, V>[] table;
    private final int DEFAULT_CAPACITY = 1 << 4;

    public HashMap() {
        this.table = new Node[DEFAULT_CAPACITY];
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
        if (size == 0) {
            return;
        }
        for (int i = 0; i < table.length; i++) {
            table[i] = null;
        }
        size = 0;
    }

    @Override
    public V put(K key, V value) {
        int index = index(key);
        Node<K, V> root = table[index];
        if (root == null) {
            root = new Node<>(key, value, null);
            table[index] = root;
            size++;
            afterPut(root);
            return null;
        }

        Node<K, V> parent = root;
        Node<K, V> node = root;
        int cmp = 0;
        K k1 = key;
        Node<K, V> result = null;
        int h1 = key == null ? 0 : key.hashCode();
        do {
            parent = node;
            K k2 = node.key;
            int h2 = node.hash;

            if (h1 > h2) {
                cmp = 1;
            } else if (h1 < h2) {
                cmp = -1;
            } else if (Objects.equals(k1, k2)) {
                cmp = 0;
            } else if (k1 != null && k2 != null
                    && k1.getClass() == k2.getClass()
                    && k1 instanceof Comparable) {
                cmp = ((Comparable) k1).compareTo(k2);
            } else {
                if ((node.right != null && (result = node(node.right, k1)) != null)
                        || (node.left != null && (result = node(node.left, k1)) != null)) {
                    node = result;
                    cmp = 0;
                } else {
                    cmp = System.identityHashCode(k1) - System.identityHashCode(k2);
                }
            }

            if (cmp > 0) {
                node = node.right;
            } else if (cmp < 0) {
                node = node.left;
            } else {
                node.key = key;
                V oldValue = node.value;
                node.value = value;
                return oldValue;
            }
        } while (node != null);

        Node<K, V> newNode = new Node<>(key, value, parent);
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
        return node == null ? null : node.value;
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
        if (size == 0) {
            return false;
        }
        Queue<Node<K, V>> queue = new LinkedList<>();
        for (int i = 0; i < table.length; i++) {
            if (table[i] == null) {
                continue;
            }

            queue.offer(table[i]);
            while (!queue.isEmpty()) {
                Node<K, V> node = queue.poll();
                if (Objects.equals(value, node.value)) {
                    return true;
                }
                if (node.left != null) {
                    queue.offer(node.left);
                }
                if (node.right != null) {
                    queue.offer(node.right);
                }
            }
        }
        return false;
    }

    @Override
    public void traversal(Visitor<K, V> visitor) {
        if (size == 0 || visitor.stop) {
            return;
        }
        Queue<Node<K, V>> queue = new LinkedList<>();
        for (int i = 0; i < table.length; i++) {
            if (table[i] == null) {
                continue;
            }

            queue.offer(table[i]);
            while (!queue.isEmpty()) {
                Node<K, V> node = queue.poll();
                if (visitor.visit(node.key, node.value)) {
                    return;
                }
                if (node.left != null) {
                    queue.offer(node.left);
                }
                if (node.right != null) {
                    queue.offer(node.right);
                }
            }
        }
    }

    private Node<K, V> node(K key) {
        Node<K, V> root = table[index(key)];
        return root == null ? null : node(root, key);
    }

    private Node<K, V> node(Node<K, V> node, K k1) {
        int h1 = k1 == null ? 0 : k1.hashCode();
        Node<K, V> result = null;
        while (node != null) {
            K k2 = node.key;
            int h2 = node.hash;
            if (h1 > h2) {
                node = node.right;
            } else if (h1 < h2) {
                node = node.left;
            } else if (Objects.equals(k1, k2)) {
                return node;
            } else if (k1 != null && k2 != null
                    && k1.getClass() == k2.getClass()
                    && k1 instanceof Comparable) {
                int cmp = ((Comparable) k1).compareTo(k2);
                if (cmp > 0) {
                    node = node.right;
                } else if (cmp < 0) {
                    node = node.left;
                } else {
                    return node;
                }
            } else if (node.right != null && (result = node(node.right, k1)) != null) {
                return result;
            } else if (node.left != null && (result = node(node.left, k1)) != null) {
                return result;
            } else {
                return null;
            }
        }
        return null;
    }

    /**
     * 计算key的索引
     *
     * @param key
     * @return
     */
    private int index(K key) {
        if (key == null) {
            return 0;
        }
        int hash = key.hashCode();
        return (hash ^ (hash >>> 16)) & (table.length - 1);
    }

    private int index(Node<K, V> node) {
        return (node.hash ^ (node.hash >>> 16)) & (table.length - 1);
    }

    /**
     * 查找后继节点
     *
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
            while (node.left != null) {
                node = node.left;
            }
            return node;
        }

        // 没有右子树
        while (node.parent != null && node == node.parent.right) {
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
                table[index(node)] = replacement;
            } else if (node == node.parent.left) {
                node.parent.left = replacement;
            } else {
                node.parent.right = replacement;
            }

            afterRemove(node, replacement);
        } else if (node.parent == null) {       // 叶子节点且是根节点
            table[index(node)] = null;

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

    private int compare(K k1, K k2, int h1, int h2) {
        // 比较哈希值
        int result = h1 - h2;
        if (result != 0) {
            return result;
        }

        // 比较equals
        if (Objects.equals(k1, k2)) {
            return 0;
        }

        // 哈希值相等 不equals
        // 比较类名
        if (k1 != null && k2 != null) {
            String k1ClsName = k1.getClass().getName();
            String k2ClsName = k2.getClass().getName();
            result = k1ClsName.compareTo(k2ClsName);
            if (result != 0) {
                return result;
            }

            if (k1 instanceof Comparable) {
                return ((Comparable<K>) k1).compareTo(k2);
            }
        }

        // 同一种类型 哈希值相等 但不具备可比较性
        // k1不为null k2为null
        // k1为null k2不为null
        return System.identityHashCode(k1) - System.identityHashCode(k2);

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
     *
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
     *
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
            table[index(g.key)] = p;
        }

        // 更新child 父节点
        if (child != null) {
            child.parent = g;
        }

        // 更新 grand 父节点
        g.parent = p;
    }

    private static class Node<K, V> {
        K key;
        V value;
        int hash;
        Node<K, V> left;
        Node<K, V> right;
        Node<K, V> parent;
        boolean color = RED;

        public Node(K key, V value, Node<K, V> parent) {
            this.key = key;
            this.value = value;
            this.parent = parent;
            this.hash = key == null ? 0 : key.hashCode();
        }

        /**
         * 判断是否是叶子节点
         *
         * @return
         */
        public boolean isLeaf() {
            return left == null && right == null;
        }

        /**
         * 判断是否是度为2的节点
         *
         * @return
         */
        public boolean hasTwoChild() {
            return left != null && right != null;
        }

        /**
         * 判断是否为左孩子
         *
         * @return
         */
        public boolean isLeftChild() {
            return parent != null && this == parent.left;
        }

        /**
         * 判断是右孩子
         *
         * @return
         */
        public boolean isRightChild() {
            return parent != null && this == parent.right;
        }

        /**
         * 查找兄弟节点
         *
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
