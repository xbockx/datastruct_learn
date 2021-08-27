package tree;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.Queue;

/**
 * 二叉搜索树
 * @param <E>
 */
public class BinarySearchTree<E> {

    protected int size;
    protected Node<E> root;
    protected Comparator<E> comparator;

    public BinarySearchTree() {
        this(null);
    }

    public BinarySearchTree(Comparator comparator) {
        this.comparator = comparator;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void add(E element) {
        notNullCheck(element);

        if (root == null) {
            root = createNode(element, null);
            size++;
            // 添加节点后处理
            afterAdd(root);
            return;
        }

        Node<E> parent = root;
        Node<E> node = root;
        int cmp = 0;
        while(node != null) {
            cmp = compare(element, node.element);
            parent = node;
            if (cmp > 0) {
                node = node.right;
            } else if(cmp < 0) {
                node = node.left;
            } else {
                return;
            }
        }

        Node<E> newNode = createNode(element, parent);
        if (cmp > 0) {
            parent.right = newNode;
        } else {
            parent.left = newNode;
        }
        size++;
        afterAdd(newNode);

    }

    public boolean contains(E element) {
        return node(element) != null;
    }

    /**
     * 删除元素所在节点
     * @param element
     */
    public void remove(E element) {
        remove(node(element));
    }

    /**
     * 删除指定节点
     * @param node
     */
    private void remove(Node<E> node) {
        if (node == null) {
            return;
        }

        // 处理度为2的节点
        if (node.hasTwoChild()) {
            Node<E> sNode = successor(node);
            node.element = sNode.element;
            node = sNode;
        }

        // 删除度为0或1的节点
        // 要替代的节点
        Node<E> replacement = node.left != null ? node.left : node.right;
        if (replacement != null) {      // 度为1的节点
            replacement.parent = node.parent;
            if (node.parent == null) {
                root = replacement;
            } else if (node == node.parent.left) {
                node.parent.left = replacement;
            } else {
                node.parent.right = replacement;
            }

            afterRemove(node);
        } else if (node.parent == null) {       // 叶子节点且是根节点
            root = null;

            afterRemove(node);
        } else {        // 叶子节点非根节点
            if (node == node.parent.left) {
                node.parent.left = null;
            } else {
                node.parent.right = null;
            }

            afterRemove(node);
        }

    }

    /**
     * 查找元素对应节点
     * @param element
     * @return
     */
    private Node<E> node(E element) {
        if (root == null) {
            return null;
        }
        Node<E> node = root;
        while(node != null) {
            int cmp = compare(node.element, element);
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

    public void clear() {
        root = null;
        size = 0;
    }

    /**
     * 添加节点后处理
     * @param node  新添加节点
     */
    protected void afterAdd(Node<E> node) {};

    /**
     * 删除节点后处理
     * @param node  被删除节点
     */
    protected void afterRemove(Node<E> node) {};

    protected Node<E> createNode(E element, Node<E> parent) {
        return new Node<E>(element, parent);
    }

    /**
     * 前序遍历
     */
    public void preorderTraversal(Visitor<E> visitor) {
        if (visitor == null) return;
        preorderTraversal(root, visitor);
    }

    private void preorderTraversal(Node<E> node, Visitor<E> visitor) {
        if (node == null || visitor.stop) return;
        visitor.stop = visitor.visit(node.element);
        preorderTraversal(node.left, visitor);
        preorderTraversal(node.right, visitor);
    }

    /**
     * 中序遍历
     */
    public void inorderTraversal(Visitor<E> visitor) {
        if (visitor == null) return;
        inorderTraversal(root, visitor);
    }

    private void inorderTraversal(Node<E> node, Visitor<E> visitor) {
        if (node == null || visitor.stop) return;

        inorderTraversal(node.left, visitor);
        if (visitor.stop) return;
        visitor.stop = visitor.visit(node.element);
        inorderTraversal(node.right, visitor);
    }

    /**
     * 后序遍历
     */
    public void postorderTraversal(Visitor<E> visitor) {
        if (visitor == null) return;
        postorderTraversal(root, visitor);
    }

    private void postorderTraversal(Node<E> node, Visitor<E> visitor) {
        if (node == null || visitor.stop) return;

        postorderTraversal(node.left, visitor);
        postorderTraversal(node.right, visitor);
        if (visitor.stop) return;
        visitor.stop = visitor.visit(node.element);
    }

    /**
     * 层序遍历
     */
    public void levelOrderTraversal(Visitor<E> visitor) {
        if (root == null || visitor == null) return;

        Queue<Node<E>> queue = new LinkedList<>();
        queue.offer(root);
        while(!queue.isEmpty()) {
            Node<E> node = queue.poll();
            if (visitor.visit(node.element)) return;
            if (node.left != null) {
                queue.offer(node.left);
            }
            if (node.right != null) {
                queue.offer(node.right);
            }
        }

    }

    /**
     * 判断是否为完全二叉树
     * @return
     */
    public boolean isComplete() {

        if (root == null) {
            return false;
        }

        Queue<Node<E>> queue = new LinkedList<>();
        queue.offer(root);
        boolean isLeaf = false;

        while(!queue.isEmpty()) {
            Node<E> node = queue.poll();

            if (isLeaf && !(node.left == null && node.right == null)) return false;

            if (node.left != null) {
                queue.offer(node.left);
            } else if (node.right != null) {
                return false;
            }

            if (node.right != null) {
                queue.offer(node.right);
            } else {
                isLeaf = true;
            }

//            if (node.left != null && node.right != null) {
//                queue.offer(node.left);
//                queue.offer(node.right);
//            } else if(node.left == null && node.right != null) {
//                return false;
//            } else {
//                isLeaf = true;
//            }
        }

        return true;
    }

    /**
     * 迭代计算树高度
     * @return
     */
    public int height2() {
        if (root == null) {
            return 0;
        }
        Queue<Node<E>> queue = new LinkedList<>();
        int height = 0;
        int levelSize = 1;
        queue.offer(root);

        while (!queue.isEmpty()) {
            Node<E> node = queue.poll();
            levelSize--;
            if (node.left != null) {
                queue.offer(node.left);
            }
            if (node.right != null) {
                queue.offer(node.right);
            }
            if (levelSize == 0) {
                levelSize = queue.size();
                height++;
            }
        }

        return height;
    }

    /**
     * 递归计算树高度
     * @return
     */
    public int height() {
        return height(root);
    }

    private int height (Node<E> node) {
        if (node == null) {
            return 0;
        }
        return 1 + Math.max(height(node.left), height(node.right));
    }

    /**
     * 查找前驱节点
     * @param node
     * @return
     */
    protected Node<E> predecessor(Node<E> node) {
        if (node == null) {
            return null;
        }

        // 有左子树
        if (node.left != null) {
            node = node.left;
            while(node.right != null) {
                node = node.right;
            }
            return node;
        }

        // 没有左子树
        while(node.parent != null && node == node.parent.left) {
            node = node.parent;
        }

        return node.parent;
    }

    /**
     * 查找后继节点
     * @param node
     * @return
     */
    protected Node<E> successor(Node<E> node) {
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

    public static abstract class Visitor<E> {
        boolean stop;
        abstract boolean visit(E element);
    }

    private int compare(E e1, E e2) {
        if (comparator != null) {
            return comparator.compare(e1, e2);
        }
        return ((Comparable<E>)e1).compareTo(e2);
    }

    private void notNullCheck(E element) {
        if (element == null) {
            throw new IllegalArgumentException("Element must not null!");
        }
    }

    protected static class Node<E> {
        public E element;
        public Node<E> left;
        public Node<E> right;
        public Node<E> parent;

        public Node(E element, Node<E> parent) {
            this.element = element;
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
    }

}
