package heap;

import java.util.Comparator;

public abstract class AbstractHeap<E> implements Heap<E> {

    protected int size;
    protected Comparator<E> comparator;

    protected AbstractHeap(Comparator comparator) {
        this.comparator = comparator;
    }

    protected AbstractHeap() {
        this(null);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    protected int compare(E e1, E e2) {
        return comparator != null ? comparator.compare(e1, e2) : ((Comparable)e1).compareTo(e2);
    }

}
