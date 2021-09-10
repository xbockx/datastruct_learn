package heap;

import java.util.Comparator;

public class Main {
    public static void main(String[] args) {
        // 小顶堆
        BinaryHeap<Integer> minHeap = new BinaryHeap<>(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o2 - o1;
            }
        });

        // TopK问题
        // 找出数组中最大的5个数
        Integer[] data = new Integer[]{85, 19, 69, 3, 7, 99, 95, 2, 1, 70, 44, 58, 11, 21, 14, 93, 57, 4, 56};
        int k = 3;
        for (int i = 0; i < data.length; i++) {
            if (minHeap.size() < k) {
                minHeap.add(data[i]);
            } else if (minHeap.get() < data[i]) {
                minHeap.replace(data[i]);
            }
        }

        System.out.println(minHeap);
    }
}
