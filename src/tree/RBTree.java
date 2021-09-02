package tree;

import java.util.Comparator;

/**
 * 红黑树
 * @param <E>
 */
public class RBTree<E> extends BBST<E> {

    private static final boolean RED = false;
    private static final boolean BLACK = true;

    public RBTree() {
        this(null);
    }

    public RBTree(Comparator comparator) {
        super(comparator);
    }

    @Override
    protected void afterAdd(Node<E> node) {
        Node<E> parent = node.parent;

        // node是根节点
        if (parent == null) {
            black(node);
            return;
        }

        if (isBlack(parent)) {
            return;
        }

        // 叔父节点
        Node<E> uncle = parent.sibling();
        // 祖父节点
        Node<E> grand = parent.parent;
        if (isRed(uncle)) {
            black(parent);
            black(uncle);
            red(grand);
            afterAdd(grand);
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

    @Override
    protected void afterRemove(Node<E> node, Node<E> replacement) {
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
        Node<E> parent = node.parent;
        if (parent == null) {
            return;
        }

        // 删除的是黑色叶子节点
        // 判断被删除的节点是左还是右
        boolean left = parent.left == null || node.isLeftChild();
        Node<E> sibling = left ? parent.right : parent.left;
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

    @Override
    protected Node<E> createNode(E element, Node<E> parent) {
        return new RBNode<>(element, parent);
    }

    private Node<E> color(Node<E> node, boolean color) {
        if (node == null) {
            return node;
        }
        ((RBNode)node).color = color;
        return node;
    }

    private Node<E> red(Node<E> node) {
        return color(node, RED);
    }

    private Node<E> black(Node<E> node) {
        return color(node, BLACK);
    }

    private boolean colorOf(Node<E> node) {
        return node == null ? BLACK : ((RBNode)node).color;
    }

    private boolean isBlack(Node<E> node) {
        return colorOf(node) == BLACK;
    }

    private boolean isRed(Node<E> node) {
        return colorOf(node) == RED;
    }

    private static class RBNode<E> extends Node<E> {
        boolean color = RED;
        public RBNode(E element, Node<E> parent) {
            super(element, parent);
        }

        @Override
        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            if (color == RED) {
                stringBuilder.append("R_");
            } else {
                stringBuilder.append("B_");
            }
            stringBuilder.append(element);
            return stringBuilder.toString();
        }
    }

}
