package sort;

/**
 * @Description
 * @Author xbockx
 * @Date 1/10/2022
 */
public class BinarySearch {

    public static int indexOf(Integer[] array, Integer v) {
        if (array == null || array.length == 0) {
            return -1;
        }

        // [begin, end)
        int begin = 0;
        int end = array.length;
        while(begin < end) {
            int mid = (begin + end) >> 1;
            if (array[mid] > v) {
                end = mid;
            } else if(array[mid] < v) {
                begin = mid + 1;
            } else {
                return mid;
            }
        }
        // not found
        return -1;
    }

    // for insertion sort
    public static int search(Integer[] array, Integer v) {
        if (array == null || array.length == 0) {
            return -1;
        }
        int begin = 0;
        int end = array.length;
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

}
