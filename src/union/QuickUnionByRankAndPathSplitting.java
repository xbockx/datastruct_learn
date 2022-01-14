package union;

/**
 * @Description
 * @Author xbockx
 * @Date 1/14/2022
 */
public class QuickUnionByRankAndPathSplitting extends QuickUnionByRank{
    public QuickUnionByRankAndPathSplitting(int capacity) {
        super(capacity);
    }

    @Override
    public int find(int v) {
        rangeCheck(v);
        while(v != parents[v]) {
            int parent = parents[v];
            parents[v] = parents[parent];
            v = parent;
        }
        return v;
    }
}
