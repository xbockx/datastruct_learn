package arraylist;

public class Main {
    public static void main(String[] args) {
        ArrayList<Integer> arrayList = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            arrayList.add(i+1);
        }
        arrayList.add(arrayList.size(), 0);
        System.out.println(arrayList);
    }
}
