package queue;

public class CircleQueue<E> {

    private E[] elements;
    private int front;
    private int size;
    private static final int DEFAULT_CAPACITY = 10;

    public CircleQueue() {
        elements = (E[]) new Object[DEFAULT_CAPACITY];
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void enQueue(E element) {
        ensureCapacity(size + 1);
        elements[index(size)] = element;
        size++;
    }

    public E deQueue() {
        E frontElement = elements[front];
        elements[front] = null;
        front = index(1);
        size--;
        return frontElement;
    }

    public E front() {
        return elements[front];
    }

    public void clear() {
        for(int i = 0; i < size; i++) {
            elements[index(i)] = null;
        }
        size = 0;
        front = 0;
    }

    /**
     * 获取真实索引
     * @param index
     * @return
     */
    private int index(int index) {
        return (front + index) % elements.length;
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
            newElements[i] = elements[(i + front) % elements.length];
        }

//        System.out.println("扩大容量------> " + capacity);
        elements = newElements;

        // 重置front
        front = 0;
    }
}
