package util;

/**
 * @Description
 * @Author xbockx
 * @Date 1/9/2022
 */
public class Integers {

    public static Integer[] random(int count, int min, int max) {
        Integer[] array = new Integer[count];
        try {
            if (count <= 0 || min > max) {
                throw new Exception("invalid args");
            }
            int delta = max - min + 1;
            for(int i = 0; i < count; i++) {
                array[i] = min + (int)(Math.random() * delta);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return array;
    }

    public static void print(Integer[] array) {
        for (Integer integer : array) {
            System.out.print(integer + "\t");
        }
        System.out.println("\n");
    }

    public static Integer[] copy(Integer[] array) {
        Integer[] ret = new Integer[array.length];
        for (int i = 0; i < ret.length; i++) {
            ret[i] = array[i];
        }
        return ret;
    }

    public static Integer[] ascOrder(int count) {
        Integer[] array = new Integer[count];
        for (int i = 0; i < count; i++) {
            array[i] = i+1;
        }
        return array;
    }
}
