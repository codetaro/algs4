package ch4;

import ch1.Queue;
import edu.princeton.cs.algs4.MinPQ;

// 这种延时实现会在优先队列中保留失效的边
public class LazyPrimMST implements MST {
    private boolean[] marked; // 最小生成树的顶点
    private Queue<Edge> mst;  // 最小生成树的边
    private MinPQ<Edge> pq;   // 横切边(包括失效的边)
    private double weight;

    public LazyPrimMST(EdgeWeightedGraph G) {
        pq = new MinPQ<>();
        marked = new boolean[G.V()];
        mst = new Queue<>();

        visit(G, 0);          // 假设G是连通的
        while (!pq.isEmpty()) {
            Edge e = pq.delMin();                 // 从pq中得到权重最小的边

            int v = e.either(), w = e.other(v);
            if (marked[v] && marked[w]) continue; // 跳过失效的边
            mst.enqueue(e);                       // 将边添加到树中
            weight += e.weight();
            if (!marked[v]) visit(G, v);          // 将顶点(v或w)添加到树中
            if (!marked[w]) visit(G, w);
        }
    }

    private void visit(EdgeWeightedGraph G, int v) {
        // 标记顶点v并将所有连接v和未被标记顶点的边加入pq
        marked[v] = true;
        for (Edge e : G.adj(v))
            if (!marked[e.other(v)]) pq.insert(e);
    }
    @Override
    public Iterable<Edge> edges() {
        return mst;
    }
    @Override
    public double weight() {
        return weight;
    }
}
