package map;

public class Main {
    public static void main(String[] args) {
        test2();
    }

    static void test1() {
        Map<String, Integer> map = new TreeMap<>();
        map.put("a", 6);
        map.put("b", 10);
        map.put("c", 7);
        map.put("a", 3);

        map.traversal(new Map.Visitor<String, Integer>() {
            @Override
            boolean visit(String key, Integer value) {
                System.out.println(key + "_" + value);
                return false;
            }
        });
    }

    static void test2() {
        Map<Object, Integer> map = new HashMap<>();
        Object p1 = new Object();
        map.put(p1, 1);
        map.put("test", 2);
        map.put(null, 3);

        System.out.println(map.get(p1));
        System.out.println(map.get("test"));
        System.out.println(map.get(null));
    }
}
