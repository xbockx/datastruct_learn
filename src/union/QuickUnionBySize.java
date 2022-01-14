package union;

/**
 * @Description
 * @Author xbockx
 * @Date 1/14/2022
 */
public class QuickUnionBySize extends QuickUnion{
    private int[] size;
    public QuickUnionBySize(int capacity) {
        super(capacity);
        size = new int[capacity];
        for (int i = 0; i <size.length; i++) {
            size[i] = 1;
        }
    }

    @Override
    public void union(int v1, int v2) {
        int p1 = find(v1);
        int p2 = find(v2);
        if (p1 == p2) {
            return;
        }
        if (size[p1] < size[p2]) {
            parents[p1] = parents[p2];
            size[p2] += size[p1];
        } else {
            parents[p2] = parents[p1];
            size[p1] += size[p2];
        }
    }
}
