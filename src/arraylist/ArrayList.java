package arraylist;

/**
 * 自定义数组类
 * @param <E>
 */
public class ArrayList<E> {

    // 数组大小
    private int size;
    // 存放数据元素
    private E[] elements;
    // 默认数组容量
    public static final int DEFAULT_CAPACITY = 10;
    // 元素未找到
    public static final int ELEMENT_NOT_FOUND = -1;

    public ArrayList() {
        this(DEFAULT_CAPACITY);
    }

    public ArrayList(int capacity) {
        capacity = capacity < 10 ? DEFAULT_CAPACITY : capacity;
        elements = (E[]) new Object[capacity];
    }

    /**
     * 返回数组大小
     *
     * @return
     */
    public int size() {
        return size;
    }

    ;

    /**
     * 判断数组是否为空
     *
     * @return
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * 判断数组是否包含该元素
     *
     * @param element
     * @return
     */
    public boolean contains(E element) {
        return indexOf(element) != ELEMENT_NOT_FOUND;
    }

    /**
     * 添加元素
     *
     * @param element
     */
    public void add(E element) {
        add(size, element);
    }

    /**
     * 获取该索引的元素
     *
     * @param index
     * @return
     */
    public E get(int index) {
        rangeCheck(index);
        return elements[index];
    }

    /**
     * 设置该索引下的元素并返回原始元素
     *
     * @param index
     * @param element
     * @return
     */
    public E set(int index, E element) {
        rangeCheck(index);
        E ret = elements[index];
        elements[index] = element;
        return ret;
    }

    /**
     * 指定索引位置添加元素
     * @param index
     * @param element
     */
    public void add(int index, E element) {
        rangeCheckForAdd(index);

        ensureCapacity(size + 1);

        for(int i = size - 1; i >= index; i--) {
            elements[i + 1] = elements[i];
        }
        elements[index] = element;
        size++;
    }

    /**
     * 删除该索引所在元素并返回原始元素
     *
     * @param index
     * @return
     */
    public E remove(int index) {
        rangeCheck(index);
        E ret = elements[index];
        for (int i = index + 1; i < size; i++) {
            elements[i - 1] = elements[i];
        }
        elements[--size] = null;
        return ret;
    }

    /**
     * 查询该元素的位置
     *
     * @param element
     * @return
     */
    public int indexOf(E element) {
        if (element == null) {
            for (int i = 0; i < size; i++) {
                if (elements[i] == null) {
                    return i;
                }
            }
        } else {
            for (int i = 0; i < size; i++) {
                if (elements[i].equals(element)) {
                    return i;
                }
            }
        }
        return ELEMENT_NOT_FOUND;
    }

    /**
     * 清空数据元素
     */
    public void clear() {
        for (int i = 0; i < size; i++) {
            elements[i] = null;
        }
        size = 0;
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
        for (int i = 0; i < size; i++) {
            if (i != 0) {
                builder.append(", ");
            }
            builder.append(elements[i]);
        }
        builder.append("]");
        return builder.toString();
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

    /**
     * 索引范围检查
     *
     * @param index
     */
    private void rangeCheck(int index) {
        if (index < 0 || index >= size) {
            outOfBounds(index);
        }
    }

    /**
     * 索引范围检查（for add）
     *
     * @param index
     */
    private void rangeCheckForAdd(int index) {
        if (index < 0 || index > size) {
            outOfBounds(index);
        }
    }

    /**
     * 数组越界统一异常
     *
     * @param index
     */
    private void outOfBounds(int index) {
        throw new IndexOutOfBoundsException("Index: " + index + " Size: " + size);
    }

}
