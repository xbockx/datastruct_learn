package sort;

/**
 * @Description
 * @Author xbockx
 * @Date 1/9/2022
 */
public abstract class Sort {

    protected Integer[] array;
    protected int cmpCount;
    protected int swapCount;

    public void sort(Integer[] array) {
        if (array == null || array.length < 2) {
            return;
        }

        this.array = array;
        sort();
    }

    protected abstract void sort();

    /**
     * compare value of index
     * @param i1 index
     * @param i2 index
     * @return array[i1] == array[i2] return 0;
     *          array[i1] < array[i2] return < 0;
     *          array[i1] > array[i2] return > 0;
     */
    protected int cmp(int i1, int i2) {
        cmpCount++;
        return array[i1] - array[i2];
    }

    /**
     * compare value
     * @param v1 value
     * @param v2 value
     * @return
     */
    protected int cmpValue(Integer v1, Integer v2) {
        cmpCount++;
        return v1 - v2;
    }

    /**
     * swap
     * @param i1 index
     * @param i2 index
     */
    protected void swap(int i1, int i2) {
        swapCount++;
        int tmp = array[i1];
        array[i1] = array[i2];
        array[i2] = tmp;
    }

}
