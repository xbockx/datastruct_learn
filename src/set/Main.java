package set;

public class Main {
    public static void main(String[] args) {
        testTreeSet();
    }

    static void testListSet() {
        Set<Integer> set = new ListSet<>();
        set.add(10);
        set.add(11);
        set.add(12);
        set.add(12);
        set.add(13);
        set.traverse(item -> {
            System.out.println(item);
        });
    }

    static void testTreeSet(){
        Set<Integer> set = new TreeSet<>();
        set.add(13);
        set.add(10);
        set.add(11);
        set.add(12);
        set.add(12);
        set.traverse(item -> {
            System.out.println(item);
        });
    }
}
