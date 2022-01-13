package sort.cmp;

import sort.Sort;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description
 * @Author xbockx
 * @Date 1/13/2022
 */
public class ShellSort extends Sort {
    @Override
    protected void sort() {
        final List<Integer> stepSequence = shellStepSequence();
        for (Integer step : stepSequence) {
            sort(step);
        }
    }

    private void sort(int step) {
        for(int col = 0; col < step; col++) {
            for(int begin = col + step; begin < array.length; begin += step) {
                int cur = begin;
                while(cur > col && cmp(cur, cur - step) < 0) {
                    swap(cur, cur - step);
                    cur -= step;
                }
            }
        }
    }

    private List<Integer> shellStepSequence() {
        List<Integer> stepSequence = new ArrayList<>();
        int step = array.length;
        while((step >>= 1) > 0) {
            stepSequence.add(step);
        }
        return stepSequence;
    }
}
