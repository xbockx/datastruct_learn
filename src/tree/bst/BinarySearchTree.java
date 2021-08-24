package tree.bst;

import java.util.Comparator;

/**
 * 二叉搜索树
 * @param <E>
 */
public class BinarySearchTree<E> {

    private int size;
    private Node<E> root;
    private Comparator<E> comparator;

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void add(E element) {
        notNullCheck(element);

        if (root == null) {
            root = new Node<>(element, null);
            size++;
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

        Node<E> newNode = new Node<>(element, parent);
        if (cmp > 0) {
            parent.right = newNode;
        } else {
            parent.left = newNode;
        }
        size++;

    }

    public boolean contains(E element) {
        return false;
    }

    public void remove(E element) {

    }

    public void clear() {
        size = 0;
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

    private static class Node<E> {
        E element;
        Node<E> left;
        Node<E> right;
        Node<E> parent;

        public Node(E element, Node<E> parent) {
            this.element = element;
            this.parent = parent;
        }
    }

}
