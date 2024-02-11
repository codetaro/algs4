package ch6;

import ch1.Queue;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

// 最短增广路径的最大流量算法 （J.Edmonds 和 R.Karp 在 1972 年发明）
public class FordFulkerson {
    private boolean[] marked;   // 在剩余网络中是否存在从 s 到 v 的路径
    private FlowEdge[] edgeTo;  // 从 s 到 v 的最短路径上的最后一条边
    private double value;       // 当前最大流量

    private boolean hasAugmentingPath(FlowNetwork G, int s, int t) {
        marked = new boolean[G.V()];    // 标记路径已知的顶点
        edgeTo = new FlowEdge[G.V()];   // 路径上的最后一条边
        Queue<Integer> q = new Queue<>();

        marked[s] = true;               // 标记起点
        q.enqueue(s);                   // 并将它入列
        while (!q.isEmpty()) {
            int v = q.dequeue();
            for (FlowEdge e : G.adj(v)) {
                int w = e.other(v);
                // (在剩余网络中)对于任意一条连接到一个未被标记的顶点的边
                if (e.residualCapacityTo(w) > 0 && !marked[w]) {
                    edgeTo[w] = e;      // 保存路径上的最后一条边
                    marked[w] = true;   // 标记w，因为路径现在是已知的了
                    q.enqueue(w);       // 将它入列
                }
            }
        }
        return marked[t];
    }

    public FordFulkerson(FlowNetwork G, int s, int t) {
        while (hasAugmentingPath(G, s, t)) {
            // 利用所有存在的增广路径计算当前的瓶颈容量
            double bottle = Double.POSITIVE_INFINITY;
            for (int v = t; v != s; v = edgeTo[v].other(v))
                bottle = Math.min(bottle, edgeTo[v].residualCapacityTo(v));
            // 增大流量
            for (int v = t; v != s; v = edgeTo[v].other(v))
                edgeTo[v].addResidualFlowTo(v, bottle);
            value += bottle;
        }
    }

    public double value() { return value; }
    public boolean inCut(int v) { return marked[v]; }

    public static void main(String[] args) {
        FlowNetwork G = new FlowNetwork(new In(args[0]));
        int s = 0, t = G.V() - 1;
        FordFulkerson maxflow = new FordFulkerson(G, s, t);

        StdOut.println("Max flow from " + s + " to " + t);
        for (int v = 0; v < G.V(); v++)
            for (FlowEdge e : G.adj(v))
                if ((v == e.from()) && e.flow() > 0)
                    StdOut.println("  " + e);
        StdOut.println("Max flow value = " + maxflow.value());
    }
}
