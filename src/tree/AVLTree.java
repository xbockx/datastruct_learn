package tree;

import java.util.Comparator;

/**
 * AVL树
 * @param <E>
 */
public class AVLTree<E> extends BBST<E> {
    public AVLTree() {
        this(null);
    }

    public AVLTree(Comparator comparator) {
        super(comparator);
    }

    @Override
    protected void afterRotate(Node<E> g, Node<E> p, Node<E> child) {
        super.afterRotate(g, p, child);

        // 更新高度
        updateHeight(g);
        updateHeight(p);
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
    protected void afterRemove(Node<E> node, Node<E> replacement) {
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
