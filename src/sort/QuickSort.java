package sort;

/**
 * @Description
 * @Author xbockx
 * @Date 1/12/2022
 */
public class QuickSort extends Sort{
    @Override
    protected void sort() {
        sort(0, array.length);
    }

    private void sort(int begin, int end) {
        if (end - begin < 2) {
            return;
        }

        int mid = pivot(begin, end);
        sort(begin, mid);
        sort(mid + 1, end);
    }

    private int pivot(int begin, int end) {
        // 随机选一个轴点
        swap(begin, begin + (int)(Math.random() * (end - begin)));

        Integer pivot = array[begin];
        end--;

        while(begin < end) {
            while(begin < end) {
                if (cmpValue(pivot, array[end]) < 0) {
                    end--;
                } else {
                    array[begin++] = array[end];
                    break;
                }
            }
            while (begin < end) {
                if (cmpValue(pivot, array[begin]) > 0) {
                    begin++;
                } else {
                    array[end--] = array[begin];
                    break;
                }
            }

        }

        array[begin] = pivot;

        return begin;
    }
}
