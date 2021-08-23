package queue;

import java.util.Stack;

public class Main {
    public static void main(String[] args) {
//        testDeque();
        testCircleDeque();
    }

    static void testDeque() {
        Queue<Integer> queue = new Queue<>();
        queue.enQueue(11);
        queue.enQueue(22);
        queue.enQueue(33);
        queue.enQueue(44);

        while (!queue.isEmpty()) {
            System.out.println(queue.deQueue());
        }
    }

    static void testCircleDeque() {
        CircleDeque<Integer> deque = new CircleDeque<>();
        for(int i = 0; i < 10; i++) {
            deque.enQueueFront(i + 1);
            deque.enQueueRear(100 + i);
        }
        System.out.println(deque);
    }
}
