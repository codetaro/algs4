package ch4;

import ch1.Queue;
import edu.princeton.cs.algs4.IndexMinPQ;

public class PrimMST implements MST {
    private Edge[] edgeTo;         // 距离树最近的边
    private double[] distTo;       // distTo[w]=edgeTo[w].weight()
    private boolean[] marked;      // 如果v在树中则为true
    private IndexMinPQ<Double> pq; // 有效的横切边

    public PrimMST(EdgeWeightedGraph G) {
        edgeTo = new Edge[G.V()];
        distTo = new double[G.V()];
        marked = new boolean[G.V()];
        for (int v = 0; v < G.V(); v++)
            distTo[v] = Double.POSITIVE_INFINITY;
        pq = new IndexMinPQ<>(G.V());

        distTo[0] = 0.0;
        pq.insert(0, 0.0); // 用顶点0和权重0初始化pq
        while(!pq.isEmpty())
            visit(G, pq.delMin()); // 将最近的顶点添加到树中
    }

    private void visit(EdgeWeightedGraph G, int v) {
        // 将顶点v添加到树中，更新数据
        marked[v] = true;
        for (Edge e : G.adj(v)) {
            int w = e.other(v);

            if (marked[w]) continue;
            if (e.weight() < distTo[w]) {
                edgeTo[w] = e;

                distTo[w] = e.weight();
                if (pq.contains(w)) pq.change(w, distTo[w]);
                else                pq.insert(w, distTo[w]);
            }
        }
    }

    @Override
    public Iterable<Edge> edges() {
        Queue<Edge> mst = new Queue<>();
        for (int v = 0; v < edgeTo.length; v++) {
            Edge e = edgeTo[v];
            if (e != null)
                mst.enqueue(e);
        }
        return mst;
    }
    @Override
    public double weight() {
        double weight = 0.0;
        for (Edge e : edges())
            weight += e.weight();
        return weight;
    }
}
