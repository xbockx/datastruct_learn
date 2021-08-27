package tree;

import java.util.Comparator;

/**
 * AVL树
 * @param <E>
 */
public class AVLTree<E> extends BinarySearchTree<E> {
    public AVLTree() {
        this(null);
    }

    public AVLTree(Comparator comparator) {
        super(comparator);
    }

    @Override
    protected void afterAdd(Node<E> node) {
        while((node = node.parent) != null) {
            if (isBalanced(node)) {
                updateHeight(node);
            } else {
                rebalance(node);
                break;
            }
        }
    }

    @Override
    protected void afterRemove(Node<E> node) {
        while((node = node.parent) != null) {
            if (isBalanced(node)) {
                updateHeight(node);
            } else {
                rebalance(node);
            }
        }
    }

    @Override
    protected Node<E> createNode(E element, Node<E> parent) {
        return new AVLNode<>(element, parent);
    }

    private void rebalance(Node<E> grand) {
        Node<E> parent = ((AVLNode)grand).tallerChild();
        Node<E> node = ((AVLNode)parent).tallerChild();
        if (parent.isLeftChild()) {
            if (node.isLeftChild()) {     // LL
                rotateRight(grand);
            } else {        // LR
                rotateLeft(parent);
                rotateRight(grand);
            }
        } else {
            if (node.isLeftChild()) {     // RL
                rotateRight(parent);
                rotateLeft(grand);
            } else {        // RR
                rotateLeft(grand);
            }
        }
    }

    /**
     * 左旋
     * @param g grand 祖父节点
     * @param g parent 父节点
     */
    private void rotateLeft(Node<E> g) {
        Node<E> p = g.right;
        Node<E> child = p.left;

        g.right = p.left;
        p.left = g;

        afterRotate(g, p, child);
    }

    /**
     * 右旋
     * @param g grand 祖父节点
     * @param g parent 父节点
     */
    private void rotateRight(Node<E> g) {
        Node<E> p = g.left;
        Node<E> child = p.right;

        g.left = p.right;
        p.right = g;

        afterRotate(g, p, child);
    }

    private void afterRotate(Node<E> g, Node<E> p, Node<E> child) {
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

        // 更新高度
        updateHeight(g);
        updateHeight(p);
    }

    private boolean isBalanced(Node<E> node) {
        return Math.abs(((AVLNode)node).balanceFactor()) <= 1;
    }

    private void updateHeight(Node<E> node){
        ((AVLNode)node).updateHeight();
    }

    private static class AVLNode<E> extends Node<E> {
        int height = 1;

        public AVLNode(E element, Node<E> parent) {
            super(element, parent);
        }

        public int balanceFactor() {
            int leftHeight = left == null ? 0 : ((AVLNode)left).height;
            int rightHeight = right == null ? 0 : ((AVLNode)right).height;
            return leftHeight - rightHeight;
        }

        public void updateHeight() {
            int leftHeight = left == null ? 0 : ((AVLNode)left).height;
            int rightHeight = right == null ? 0 : ((AVLNode)right).height;
            height =  1 + Math.max(leftHeight, rightHeight);
        }

        public Node<E> tallerChild() {
            int leftHeight = left == null ? 0 : ((AVLNode)left).height;
            int rightHeight = right == null ? 0 : ((AVLNode)right).height;
            if (leftHeight > rightHeight) return left;
            if (leftHeight < rightHeight) return right;
            return isLeftChild() ? left : right;
        }
    }
}
