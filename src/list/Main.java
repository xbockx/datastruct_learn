package list;

import list.arraylist.ArrayList;
import list.linkedlist.DuLinkedList;
import list.linkedlist.LinkedList;

public class Main {
    public static void main(String[] args) {
//        testArrayList();
//        testLinkedList();
        testDuLinkedList();
    }

    static void testArrayList() {
        ArrayList<Integer> arrayList = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            arrayList.add(i + 1);
        }
        arrayList.add(arrayList.size(), 0);
        System.out.println(arrayList);
    }

    static void testLinkedList() {
        List<Integer> list = new LinkedList<>();
        for (int i = 0; i < 10; i++) {
            list.add(i + 1);
        }
        System.out.println(list);
    }

    static void testDuLinkedList() {
        List<Integer> list = new DuLinkedList<>();
        for (int i = 0; i < 11; i++) {
            list.add(i + 1);
        }
        for(int i = 0; i < 10; i++) {
            list.remove(0);
        }
        System.out.println(list);
    }
}
