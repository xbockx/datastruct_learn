package sort.cmp;

import sort.Sort;

/**
 * @Description
 * @Author xbockx
 * @Date 1/9/2022
 */
public class BubbleSort extends Sort {
    @Override
    protected void sort() {
        for(int end = array.length; end > 0; end--) {
            // 在数组完全有序的时候起作用
            int sortIndex = 0;
            for(int begin = 1; begin < end; begin++) {
                if (cmp(begin, begin - 1) < 0) {
                    swap(begin, begin - 1);
                }
            }
            end = sortIndex + 1;
        }
    }

    // normal
    static void bubbleSort3(Integer[] array) {
        for(int end = array.length; end > 0; end--) {
            for(int begin = 1; begin < end; begin++) {
                if (array[begin] < array[begin - 1]) {
                    int tmp = array[begin];
                    array[begin] = array[begin - 1];
                    array[begin - 1] = tmp;
                }
            }
        }
    }

    // ordered break
    static void bubbleSort2(Integer[] array) {
        for(int end = array.length; end > 0; end--) {
            boolean sorted = true;
            for(int begin = 1; begin < end; begin++) {
                if (array[begin] < array[begin - 1]) {
                    int tmp = array[begin];
                    array[begin] = array[begin - 1];
                    array[begin - 1] = tmp;
                    sorted = false;
                }
            }
            if (sorted) {
                break;
            }
        }
    }

    // record last exchange
    static void bubbleSort(Integer[] array) {
        for(int end = array.length; end > 0; end--) {
            // 在数组完全有序的时候起作用
            int sortIndex = 0;
            for(int begin = 1; begin < end; begin++) {
                if (array[begin] < array[begin - 1]) {
                    int tmp = array[begin];
                    array[begin] = array[begin - 1];
                    array[begin - 1] = tmp;
                    sortIndex = begin;
                }
            }
            end = sortIndex + 1;
        }
    }
}
