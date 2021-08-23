package stack;

import list.List;
import list.arraylist.ArrayList;

public class Stack<E> {

    private List<E> list = new ArrayList<>();

    public int size() {
        return list.size();
    }

    public boolean isEmpty() {
        return list.isEmpty();
    }

    /**
     * 添加元素
     * @param element
     */
    public void push(E element) {
        list.add(list.size(), element);
    }

    /**
     * 弹出栈顶元素
     * @return
     */
    public E pop() {
        return list.remove(list.size() - 1);
    }

    /**
     * 获取栈顶元素
     * @return
     */
    public E peek() {
        return list.get(list.size() - 1);
    }

}
