package sort;

import util.Asserts;
import util.Integers;
import util.TimeUtil;

import java.util.Arrays;

/**
 * @Description
 * @Author xbockx
 * @Date 1/9/2022
 */
public class Main {

    public static void main(String[] args) {
//        Integer[] array = Integers.random(20, 7, 99);
//        Integer[] array = Integers.ascOrder(10000);
//        Integer[] array2 = Integers.copy(array);
//        Integer[] array3 = Integers.copy(array);
//        TimeUtil.check("normal", () -> {
//            bubbleSort3(array);
//        });
//
//        TimeUtil.check("optimize", ()-> {
//            bubbleSort2(array2);
//        });
//
//        TimeUtil.check("best", ()->{
//            bubbleSort(array3);
//        });
//
//        Integers.print(array);
//        Integers.print(array2);
//        Integers.print(array3);

//        Integer[] array = Integers.random(10, 1, 100);
//        Integers.print(array);
//        Integers.print(array);
//        selectionSort(array);
//        Integers.print(array);
//        new HeapSort().sort(array);
//        new InsertionSort().sort(array);
//        Integers.print(array);

        Integer[] array = new Integer[] {4, 5, 3, 6, 2, 5, 1};
        new ShellSort().sort(array);
        Integers.print(array);
//        System.out.println(BinarySearch.indexOf(array, 5));
//        System.out.println(BinarySearch.indexOf(array, 3));
//        System.out.println(BinarySearch.indexOf(array, 1));
//        Asserts.test(BinarySearch.indexOf(array, 5) == 5);
//        Asserts.test(BinarySearch.indexOf(array, 3) == 2);
//        Asserts.test(BinarySearch.indexOf(array, 1) == 0);
    }



}
