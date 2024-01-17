package ch4;

import ch1.Queue;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class KosarajuSCC {
    private boolean[] marked; // 已访问过的顶点
    private int[] id;         // 强连通分量的标识符
    private int count;        // 强连通分量的数量

    public KosarajuSCC(Digraph G) {
        marked = new boolean[G.V()];
        id = new int[G.V()];
        // 深度优先搜索G的反向图顶点被访问的顺序
        DepthFirstOrder order = new DepthFirstOrder(G.reverse());
        // 按照G的反向图的拓扑顺序访问所有未标记的顶点(逆后序排列)
        for (int s : order.reversePost()) {
            if (!marked[s]) {
                // 所有在同一个递归dfs()调用中被访问到的顶点都在同一个强连通分量中
                dfs(G, s);
                count++;
            }
        }
    }

    private void dfs(Digraph G, int v) {
        marked[v] = true;
        id[v] = count;
        for (int w : G.adj(v)) {
            if (!marked[w]) dfs (G, w);
        }
    }
    public boolean stronglyConnected(int v, int w) {
        return id[v] == id[w];
    }
    public int id(int v) {
        return id[v];
    }
    public int count() {
        return count;
    }

    public static void main(String[] args) {
        In in = new In(args[0]);
        Digraph G = new Digraph(in);
        KosarajuSCC scc = new KosarajuSCC(G);

        // number of connected components
        int m = scc.count();
        StdOut.println(m + " strong components");

        // compute list of vertices in each strong component
        Queue<Integer>[] components = (Queue<Integer>[]) new Queue[m];
        for (int i = 0; i < m; i++) {
            components[i] = new Queue<>();
        }
        for (int v = 0; v < G.V(); v++) {
            components[scc.id(v)].enqueue(v);
        }

        // print results
        for (int i = 0; i < m; i++) {
            for (int v : components[i]) {
                StdOut.print(v + " ");
            }
            StdOut.println();
        }
    }
}
