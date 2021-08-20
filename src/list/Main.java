package list;

import list.arraylist.ArrayList;
import list.linkedlist.LinkedList;

public class Main {
    public static void main(String[] args) {
//        testArrayList();
        testLinkedList();
    }

    static void testArrayList() {
        ArrayList<Integer> arrayList = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            arrayList.add(i+1);
        }
        arrayList.add(arrayList.size(), 0);
        System.out.println(arrayList);
    }

    static void testLinkedList() {
        List<Integer> list = new LinkedList<>();
        for(int i = 0; i < 10; i++) {
            list.add(i + 1);
        }
        System.out.println(list);
    }
}
