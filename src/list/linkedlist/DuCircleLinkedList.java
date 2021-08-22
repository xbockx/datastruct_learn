package list.linkedlist;

import list.AbstractList;

/**
 * 双向循环链表
 * @param <E>
 */
public class DuCircleLinkedList<E> extends AbstractList<E> {

    private Node<E> first;
    private Node<E> last;

    private static class Node<E> {
        E element;
        Node<E> next;
        Node<E> prev;
        public Node(E element, Node<E> prev, Node<E> next){
            this.element = element;
            this.prev = prev;
            this.next = next;
        }
    }

    @Override
    public E get(int index) {
        return null;
    }

    @Override
    public E set(int index, E element) {
        Node<E> node = node(index);
        E old = node.element;
        node.element = element;
        return old;
    }

    @Override
    public void add(int index, E element) {
        rangeCheckForAdd(index);

        if (index == size) {
            last = new Node<>(element, last, first);
            if (last.prev == null) {
                first = last;
                first.next = first;
                first.prev = first;
            } else {
                last.prev.next = last;
                first.prev = last;
            }
        } else {
            Node<E> next = node(index);
            Node<E> prev = next.prev;
            Node<E> node = new Node<>(element, prev, next);
            next.prev = node;
            prev.next = node;

            if (index == 0) {
                first = node;
            }
        }

        size++;
    }

    @Override
    public E remove(int index) {
        rangeCheck(index);

        Node<E> node = first;
        if (size == 1) {
            first = null;
            last = null;
        } else {
            node = node(index);
            Node<E> prev = node.prev;
            Node<E> next = node.next;
            prev.next = next;
            next.prev = prev;

            if (node == first) {
                first = next;
            }

            if (node == last) {
                last = prev;
            }
        }

        size--;
        return node.element;
    }

    @Override
    public int indexOf(E element) {
        Node<E> node = first;
        if (element == null) {
            for (int i = 0; i < size; i++) {
                if (node.element == null) {
                    return i;
                }
                node = node.next;
            }
        } else {
            for (int i = 0; i < size; i++) {
                if (node.element.equals(element)) {
                    return i;
                }
                node = node.next;
            }
        }
        return ELEMENT_NOT_FOUND;
    }

    @Override
    public void clear() {
        size = 0;
        first = null;
        last = null;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        Node<E> node = first;
        builder.append("size: ").append(size).append("\n").append("[");
        for (int i = 0; i < size; i++) {
            if (i != 0) {
                builder.append(", ");
            }
            builder.append(node.element);
            node = node.next;
        }
        builder.append("]");
        return builder.toString();
    }

    private Node<E> node(int index) {
        rangeCheck(index);

        if (index < (size >> 2)) {
            Node<E> node = first;
            for(int i = 0; i < index; i++) {
                node = node.next;
            }
            return node;
        } else {
            Node<E> node = last;
            for(int i = size - 1; i > index; i--) {
                node = node.prev;
            }
            return node;
        }
    }

}
