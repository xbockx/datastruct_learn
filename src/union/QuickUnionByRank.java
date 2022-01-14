package union;

/**
 * @Description
 * @Author xbockx
 * @Date 1/14/2022
 */
public class QuickUnionByRank extends QuickUnion{
    private int[] rank;
    public QuickUnionByRank(int capacity) {
        super(capacity);
        rank = new int[capacity];
        for (int i = 0; i < rank.length; i++) {
            rank[i] = 1;
        }
    }

    @Override
    public void union(int v1, int v2) {
        int p1 = find(v1);
        int p2 = find(v2);

        if (rank[p1] < rank[p2]) {
            parents[p1] = p2;
        } else if (rank[p1] > rank[p2]) {
            parents[p2] = p1;
        } else {
            parents[p1] = p2;
            rank[p2]++;
        }

    }
}
