package queue;

/**
 * 双端队列
 * @param <E>
 */
public class CircleDeque<E> {

    private E[] elements;
    private int front;
    private int size;
    private static final int DEFAULT_CAPACITY = 10;

    public CircleDeque() {
        elements = (E[]) new Object[DEFAULT_CAPACITY];
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void enQueueRear(E element) {
        ensureCapacity(size + 1);
        elements[index(size)] = element;
        size++;
    }


    public void enQueueFront(E element) {
        ensureCapacity(size + 1);
        front = index(-1);
        elements[front] = element;
        size++;
    }

    public E deQueueFront() {
        E frontElement = elements[front];
        elements[front] = null;
        front = index(1);
        size--;
        return frontElement;
    }

    // 0 1 2 3 4 5
    //   x x x x x
    public E deQueueRear() {
        int rearIndex = index(size - 1);
        E rear = elements[rearIndex];
        elements[rearIndex] = null;
        size--;
        return rear;
    }

    public E front() {
        return elements[front];
    }

    public E rear() {
        return elements[index(size - 1)];
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
        index += front;
        if (index < 0) {
            index += elements.length;
        }
        return index % elements.length;
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

        System.out.println("扩大容量------> " + capacity);
        elements = newElements;

        // 重置front
        front = 0;
    }

    /**
     * 重写数组打印
     *
     * @return
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("size: ").append(size).append("\n").append("[");
        for (int i = 0; i < elements.length; i++) {
            if (i != 0) {
                builder.append(", ");
            }
            builder.append(elements[i]);
        }
        builder.append("]");
        return builder.toString();
    }
}
