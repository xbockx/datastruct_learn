package set;

import java.util.function.Consumer;

public interface Set<E> {

    /**
     * 返回集合大小
     *
     * @return
     */
    int size();

    /**
     * 判断集合是否为空
     *
     * @return
     */
    boolean isEmpty();

    /**
     * 判断集合是否包含该元素
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
     * 删除该索引所在元素并返回原始元素
     *
     * @param element
     * @return
     */
    void remove(E element);

    /**
     * 清空数据元素
     */
    void clear();

    /**
     * 遍历元素
     * @param consumer
     */
    void traverse(Consumer<E> consumer);

}
