package union;

/**
 * @Description
 * @Author xbockx
 * @Date 1/14/2022
 */
public class QuickUnionByRankAndPathCompression extends QuickUnionByRank{
    public QuickUnionByRankAndPathCompression(int capacity) {
        super(capacity);
    }

    @Override
    public int find(int v) {
        rangeCheck(v);
        if (v != parents[v]) {
            parents[v] = find(parents[v]);
        }
        return parents[v];
    }
}
