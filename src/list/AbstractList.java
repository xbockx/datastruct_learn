package list;

public abstract class AbstractList<E> implements List<E> {

    // 数组大小
    protected int size;

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
     * 索引范围检查
     *
     * @param index
     */
    protected void rangeCheck(int index) {
        if (index < 0 || index >= size) {
            outOfBounds(index);
        }
    }

    /**
     * 索引范围检查（for add）
     *
     * @param index
     */
    protected void rangeCheckForAdd(int index) {
        if (index < 0 || index > size) {
            outOfBounds(index);
        }
    }

    /**
     * 数组越界统一异常
     *
     * @param index
     */
    protected void outOfBounds(int index) {
        throw new IndexOutOfBoundsException("Index: " + index + " Size: " + size);
    }

}
