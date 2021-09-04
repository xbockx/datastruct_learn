package map;

public class Main {
    public static void main(String[] args) {
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
}
