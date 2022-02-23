package sort.cmp;

import sort.Sort;

/**
 * @Description
 * @Author xbockx
 * @Date 1/12/2022
 */
public class QuickSort extends Sort {
    @Override
    protected void sort() {
        sort(0, array.length);
    }

    private void sort(int begin, int end) {
        // 长度小于 2 不再排序
        if (end - begin < 2) {
            return;
        }

        // 确定轴点位置
        int mid = pivot(begin, end);
        // 排序轴点左侧位置
        sort(begin, mid);
        // 排序轴点右侧位置
        sort(mid + 1, end);
    }

    private int pivot(int begin, int end) {
        // 随机选一个轴点
        swap(begin, begin + (int)(Math.random() * (end - begin)));

        // 备份元素
        Integer pivot = array[begin];
        // end 指向最后一个元素
        end--;

        while(begin < end) {
            // 从右往左扫描
            while(begin < end) {
                if (cmpValue(pivot, array[end]) < 0) {
                    end--;
                } else {
                    array[begin++] = array[end];
                    break;
                }
            }
            // 从左往右扫描
            while (begin < end) {
                if (cmpValue(pivot, array[begin]) > 0) {
                    begin++;
                } else {
                    array[end--] = array[begin];
                    break;
                }
            }

        }

        // 将轴点放入最终位置
        array[begin] = pivot;

        return begin;
    }
}
