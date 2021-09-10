package heap;

import java.util.Arrays;
import java.util.Comparator;

/**
 * 二叉堆 《最大堆》
 * @param <E>
 */
public class BinaryHeap<E> extends AbstractHeap<E> {

    private E[] elements;
    private static final int DEFAULT_CAPACITY = 10;

    public BinaryHeap(E[] elements, Comparator comparator) {
        super(comparator);
        if (elements == null || elements.length == 0) {
            this.elements = (E[]) new Object[DEFAULT_CAPACITY];
        } else {
            int capacity = Math.max(DEFAULT_CAPACITY, elements.length);
            this.elements = (E[]) new Object[capacity];
            size = elements.length;
            for (int i = 0; i < elements.length; i++) {
                this.elements[i] = elements[i];
            }
            heapify();
        }
    }

    public BinaryHeap(E[] elements) {
        this(elements, null);
    }

    public BinaryHeap(Comparator comparator) {
        this(null, comparator);
    }

    public BinaryHeap() {
        this(null, null);
    }



    @Override
    public void clear() {
        for (int i = 0; i < size; i++) {
            elements[i] = null;
        }
        size = 0;
    }

    @Override
    public void add(E element) {
        elementNotNullCheck(element);
        ensureCapacity(size + 1);
        elements[size++] = element;
    }

    @Override
    public E get() {
        emptyCheck();
        return elements[0];
    }

    @Override
    public E remove() {
        emptyCheck();
        int lastIndex = --size;
        E top = elements[0];
        elements[0] = elements[lastIndex];
        elements[lastIndex] = null;

        siftDown(0);
        return top;
    }

    @Override
    public E replace(E element) {
        elementNotNullCheck(element);
        E top = null;
        if (size == 0) {
            elements[0] = element;
            size++;
        } else {
            top = elements[0];
            elements[0] = element;
            siftDown(0);
        }
        return top;
    }

    /**
     * 批量建堆
     */
    private void heapify() {
        // 自上而下的上滤
//        for (int i = 0; i < size; i++) {
//            siftUp(i);
//        }
        // 自下而上的下滤
        for (int i = (size >> 1) - 1; i >= 0; i--) {
            siftDown(i);
        }
    }

    private void siftUp(int index) {
        E e = elements[index];
        while(index > 0) {
            int pindex = (index - 1) >> 1;
            E p = elements[pindex];
            if (compare(e, p) <= 0) {
                break;
            }
            elements[index] = elements[pindex];
            index = pindex;
        }
        elements[index] = e;
    }

    private void siftDown(int index) {
        E element = elements[index];
        int half = size >> 1;
        // 第一个叶子结点的索引等于非叶子节点的数量
        // 保证index 位置是非叶子节点
        while(index < half) {
            // 默认左子节点比较
            int cIndex = (index << 1) + 1;
            E child = elements[cIndex];
            // 右子节点
            int rightCIndex = cIndex + 1;
            // 选出左右子结点中做大的
            if (rightCIndex < size && compare(elements[rightCIndex], child) > 0) {
                child = elements[rightCIndex];
                cIndex = rightCIndex;
            }

            if (compare(element, child) >= 0) {
                break;
            }
            // 将子节点放到index 位置
            elements[index] = child;
            // 重新设置index
            index = cIndex;
        }
        elements[index] = element;
    }

    /**
     * 确保数组容量充足
     * @param capacity
     */
    private void ensureCapacity(int capacity) {
        int oldCapacity = elements.length;
        if (oldCapacity >= capacity) return;
        capacity = capacity + (capacity >> 1);
        E[] newElements = (E[]) new Object[capacity];

        for(int i = 0; i < size; i++) {
            newElements[i] = elements[i];
        }

//        System.out.println("扩大容量------> " + capacity);
        elements = newElements;
    }

    private void emptyCheck() {
        if (size == 0) {
            throw new IndexOutOfBoundsException("heap is empty");
        }
    }

    private void elementNotNullCheck(E element) {
        if (element == null) {
            throw new IllegalArgumentException("element cannot be null");
        }
    }

    @Override
    public String toString() {
        return "BinaryHeap{" +
                "elements=" + Arrays.toString(elements) +
                '}';
    }
}
