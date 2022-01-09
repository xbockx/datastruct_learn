package util;

/**
 * @Description
 * @Author xbockx
 * @Date 1/9/2022
 */
public class Asserts {
    public static void test(boolean value) {
        try {
            if (!value) {
                throw new Exception("----test exception----");
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
