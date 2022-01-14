package union;

/**
 * @Description
 * @Author xbockx
 * @Date 1/14/2022
 */
public class QuickUnionByRankAndPathHalving extends QuickUnionByRank{
    public QuickUnionByRankAndPathHalving(int capacity) {
        super(capacity);
    }

    @Override
    public int find(int v) {
        rangeCheck(v);
        while(v != parents[v]) {
            int parent = parents[v];
            parents[v] = parents[parent];
            v = parents[v];
        }
        return v;
    }
}
