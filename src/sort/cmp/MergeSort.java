package sort.cmp;

import sort.Sort;
import util.Integers;

/**
 * @Description
 * @Author xbockx
 * @Date 1/11/2022
 */
public class MergeSort extends Sort {
    @Override
    protected void sort() {
        sort(0, array.length );
    }

    /**
     * 对 [begin, end)范围的数据进行排序
     * @param begin
     * @param end
     */
    private void sort(int begin, int end) {
        if (end - begin < 2) return;
        int mid = (begin + end) >> 1;
        sort(begin, mid);
        sort(mid, end);
        merge(begin, mid, end);
    }

    private void merge(int begin, int mid, int end) {
        int li = 0, le = mid - begin;
        int ri = mid, re = end;
        int ai = begin;
        final Integer[] copy = Integers.copy(array, begin, mid);

        while(li < le) {
            if (ri < re && cmpValue(copy[li], array[ri]) > 0) {
                array[ai++] = array[ri++];
            } else {
                array[ai++] = copy[li++];
            }
        }
    }

    // TODO: DEBUG
    private void merge2(int begin, int mid, int end) {
        int li = begin, le = mid;
        int ri = mid, re = end;
        int ai = begin;
        final Integer[] copy = Integers.copy(array, li, le);

        while(li < le && ri < re) {
            if (array[li] > array[ri]) {
                array[ai] = array[ri];
                ri++;
            } else {
                array[ai] = copy[li - begin];
                li++;
            }
            ai++;
        }
        while(li < le) {
            array[ai] = copy[li - begin];
            li++;
            ai++;
        }
//        while(ri < re) {
//            array[begin + ai] = array[ri];
//            ri++;
//            ai++;
//        }

    }
}
