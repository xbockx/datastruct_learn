package set;

import tree.RBTree;

import java.util.function.Consumer;

public class TreeSet<E> implements Set<E> {

    private RBTree<E> rbTree = new RBTree<>();

    @Override
    public int size() {
        return rbTree.size();
    }

    @Override
    public boolean isEmpty() {
        return rbTree.isEmpty();
    }

    @Override
    public boolean contains(E element) {
        return rbTree.contains(element);
    }

    @Override
    public void add(E element) {
        if (rbTree.contains(element)) {
            return;
        }
        rbTree.add(element);
    }

    @Override
    public void remove(E element) {
        rbTree.remove(element);
    }

    @Override
    public void clear() {
        rbTree.clear();
    }

    @Override
    public void traverse(Consumer<E> consumer) {
        rbTree.inorderTraversal(consumer);
    }
}
