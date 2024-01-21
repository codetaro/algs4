package ch4;

import edu.princeton.cs.algs4.*;
import edu.princeton.cs.algs4.DirectedEdge;
import edu.princeton.cs.algs4.EdgeWeightedDigraph;

public class CPM {
    public static void main(String[] args) {
        // number of jobs
        int n = StdIn.readInt();

        // source and sink
        int source = 2 * n; // 每个任务都对应着两个顶点(一个起始顶点和一个结束顶点)
        int sink = 2 * n + 1;

        // build network
        EdgeWeightedDigraph G = new EdgeWeightedDigraph(2 * n + 2);
        for (int i = 0; i < n; i++) {
            double duration = StdIn.readDouble();
            G.addEdge(new DirectedEdge(source, i, 0.0));      // 起点到起始顶点
            G.addEdge(new DirectedEdge(i + n, sink, 0.0)); // 起始顶点到结束顶点, 权重为任务所需的时间
            G.addEdge(new DirectedEdge(i, i + n, duration));      // 结束顶点到终点

            // precedence constraints
            int m = StdIn.readInt();
            for (int j = 0; j < m; j++) {
                int precedent = StdIn.readInt();
                G.addEdge(new DirectedEdge(n + i, precedent, 0.0)); // v 的结束顶点指向 w 的起始顶点
            }
        }

        // compute longest path
        AcyclicLP lp = new AcyclicLP(G, source);

        // print results
        StdOut.println(" job   start  finish");
        StdOut.println("--------------------");
        for (int i = 0; i < n; i++)
            StdOut.printf("%4d %7.1f %7.1f\n", i, lp.distTo(i), lp.distTo(i + n));
        StdOut.printf("Finish time: %7.1f\n", lp.distTo(sink));
    }
}
