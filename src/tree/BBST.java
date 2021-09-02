package tree;

import java.util.Comparator;

public class BBST<E> extends BinarySearchTree<E> {

    public BBST() {
        this(null);
    }

    public BBST(Comparator comparator) {
        super(comparator);
    }

    /**
     * 左旋
     * @param g grand 祖父节点
     * @param g parent 父节点
     */
    protected void rotateLeft(Node<E> g) {
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
    protected void rotateRight(Node<E> g) {
        Node<E> p = g.left;
        Node<E> child = p.right;

        g.left = p.right;
        p.right = g;

        afterRotate(g, p, child);
    }

    protected void afterRotate(Node<E> g, Node<E> p, Node<E> child) {
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
}
