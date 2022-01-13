package sort.cmp;

import sort.Sort;

/**
 * @Description
 * @Author xbockx
 * @Date 1/9/2022
 */
public class SelectionSort extends Sort {
    @Override
    protected void sort() {
        for(int end = array.length - 1; end > 0; end--) {
            int maxIndex = 0;
            for(int begin = 1; begin <= end; begin++) {
                // >= to keep stability
                if (cmp(maxIndex, begin) <= 0) {
                    maxIndex = begin;
                }
            }
            swap(maxIndex, end);
        }
    }

    static void selectionSort(Integer[] array) {
        for(int end = array.length - 1; end > 0; end--) {
            int maxIndex = 0;
            for(int begin = 1; begin <= end; begin++) {
                // >= to keep stability
                if (array[begin] >= array[maxIndex]) {
                    maxIndex = begin;
                }
            }
            int tmp = array[end];
            array[end] = array[maxIndex];
            array[maxIndex] = tmp;
        }
    }
}
