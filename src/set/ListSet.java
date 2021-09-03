package set;

import list.linkedlist.DuLinkedList;
import list.linkedlist.LinkedList;
import tree.BinarySearchTree;

import java.util.function.Consumer;

public class ListSet<E> implements Set<E> {

    private LinkedList<E> list = new LinkedList<E>();

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public boolean contains(E element) {
        return list.contains(element);
    }

    @Override
    public void add(E element) {
        if (list.contains(element)) {
            return;
        }
        list.add(element);
    }

    @Override
    public void remove(E element) {
        int index = list.indexOf(element);
        if (index != list.ELEMENT_NOT_FOUND) {
            list.remove(index);
        }
    }

    @Override
    public void clear() {
        list.clear();
    }

    @Override
    public void traverse(Consumer<E> consumer) {
        if (consumer == null) {
            return;
        }
        for (int i = 0; i < list.size(); i++) {
            consumer.accept(list.get(i));
        }
    }
}
