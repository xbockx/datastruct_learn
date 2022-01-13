package sort.cmp;

import sort.Sort;

/**
 * @Description
 * @Author xbockx
 * @Date 1/10/2022
 */
public class InsertionSort extends Sort {

    // binary search + move
    @Override
    protected void sort() {
        for(int begin = 1; begin < array.length; begin++) {
            int insertIndex = search(begin);
            int insertValue = array[begin];
            for(int i = begin; i > insertIndex; i--) {
                array[i] = array[i - 1];
            }
            array[insertIndex] = insertValue;
        }
    }

    /**
     * 利用二分搜索找到index位置元素的待插入位置
     * 已经排好序的数组区间是[0, index)
     * @param index
     * @return
     */
    private int search(int index) {
        Integer v = array[index];

        int begin = 0;
        int end = index;
        while(begin < end) {
            int mid = (begin + end) >> 1;
            if (v < array[mid]) {
                end = mid;
            } else {
                begin = mid + 1;
            }
        }

        return begin;
    }

    // move
    public void insertionSort2() {
        for (int begin = 1; begin < array.length; begin++) {
            int cur = begin;
            int curValue = array[cur];
            while(cur > 0 && cmpValue(curValue, array[cur - 1]) < 0) {
                array[cur] = array[cur - 1];
                cur--;
            }
            array[cur] = curValue;
        }
    }

    // swap
    public void insertionSort() {
        for(int begin = 1; begin < array.length; begin++) {
            int cur = begin;
            while(cur > 0 && cmp(cur, cur - 1) < 0) {
                swap(cur, cur - 1);
                cur--;
            }
        }
    }

}
