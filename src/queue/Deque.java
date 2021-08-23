package queue;

import list.List;
import list.linkedlist.DuLinkedList;

/**
 * 双端队列
 * @param <E>
 */
public class Deque<E> {

    private List<E> list = new DuLinkedList<>();

    public int size() {
        return list.size();
    }

    public boolean isEmpty() {
        return list.isEmpty();
    }

    public void enQueueRear(E element) {
        list.add(element);
    }

    public void enQueueFront(E element) {
        list.add(0, element);
    }

    public E deQueueFront() {
        return list.remove(0);
    }

    public E deQueueRear() {
        return list.remove(list.size() - 1);
    }

    public E front() {
        return list.get(0);
    }

    public E rear() {
        return list.get(list.size());
    }

    public void clear() {
        list.clear();
    }
}
