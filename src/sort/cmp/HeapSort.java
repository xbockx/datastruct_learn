package sort.cmp;

import sort.Sort;

/**
 * @Description
 * @Author xbockx
 * @Date 1/9/2022
 */
public class HeapSort extends Sort {

    private int heapSize;

    @Override
    protected void sort() {
        // 原地建堆
        // 自下而上的下滤
        heapSize = array.length;
        for (int i = (heapSize >> 1) - 1; i >= 0; i--) {
            siftDown(i);
        }

        while(heapSize > 1) {
            swap(0, --heapSize);
            siftDown(0);
        }
    }

    private void siftDown(int index) {
        Integer element = array[index];
        int half = heapSize >> 1;
        // 第一个叶子结点的索引等于非叶子节点的数量
        // 保证index 位置是非叶子节点
        while(index < half) {
            // 默认左子节点比较
            int cIndex = (index << 1) + 1;
            Integer child = array[cIndex];
            // 右子节点
            int rightCIndex = cIndex + 1;
            // 选出左右子结点中做大的
            if (rightCIndex < heapSize && cmpValue(array[rightCIndex], child) > 0) {
                child = array[rightCIndex];
                cIndex = rightCIndex;
            }

            if (cmpValue(element, child) >= 0) {
                break;
            }
            // 将子节点放到index 位置
            array[index] = child;
            // 重新设置index
            index = cIndex;
        }
        array[index] = element;
    }

}
