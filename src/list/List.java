package list;

public interface List<E> {

    // 元素未找到
    static final int ELEMENT_NOT_FOUND = -1;

    /**
     * 返回列表大小
     *
     * @return
     */
    int size();

    ;

    /**
     * 判断列表是否为空
     *
     * @return
     */
    boolean isEmpty();

    /**
     * 判断列表是否包含该元素
     *
     * @param element
     * @return
     */
    boolean contains(E element);

    /**
     * 添加元素
     *
     * @param element
     */
    void add(E element);

    /**
     * 获取该索引的元素
     *
     * @param index
     * @return
     */
    E get(int index);

    /**
     * 设置该索引下的元素并返回原始元素
     *
     * @param index
     * @param element
     * @return
     */
    E set(int index, E element);

    /**
     * 指定索引位置添加元素
     * @param index
     * @param element
     */
    void add(int index, E element);

    /**
     * 删除该索引所在元素并返回原始元素
     *
     * @param index
     * @return
     */
    E remove(int index);

    /**
     * 查询该元素的位置
     *
     * @param element
     * @return
     */
    int indexOf(E element);

    /**
     * 清空数据元素
     */
    void clear();

}
