package ch4;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class DijkstraAllPairsSP {
    private DijkstraSP[] all;

    public DijkstraAllPairsSP(EdgeWeightedDigraph G) {
        all = new DijkstraSP[G.V()];
        for (int v = 0; v < G.V(); v++)
            all[v] = new DijkstraSP(G, v);
    }

    Iterable<DirectedEdge> path(int s, int t) {
        return all[s].pathTo(t);
    }
    double dist(int s, int t) {
        return all[s].distTo(t);
    }
    boolean hasPath(int s, int t) {
        return dist(s, t) < Double.POSITIVE_INFINITY;
    }

    public static void main(String[] args) {
        In in = new In(args[0]);
        EdgeWeightedDigraph G = new EdgeWeightedDigraph(in);

        // compute shortest paths between all pairs of vertices
        DijkstraAllPairsSP spt = new DijkstraAllPairsSP(G);

        // print all-pairs shortest path distances
        StdOut.printf("  ");
        for (int v = 0; v < G.V(); v++) {
            StdOut.printf("%6d ", v);
        }
        StdOut.println();
        for (int v = 0; v < G.V(); v++) {
            StdOut.printf("%3d: ", v);
            for (int w = 0; w < G.V(); w++) {
                if (spt.hasPath(v, w)) StdOut.printf("%6.2f ", spt.dist(v, w));
                else StdOut.printf("  Inf ");
            }
            StdOut.println();
        }
        StdOut.println();

        // print all-pairs shortest paths
        for (int v = 0; v < G.V(); v++) {
            for (int w = 0; w < G.V(); w++) {
                if (spt.hasPath(v, w)) {
                    StdOut.printf("%d to %d (%5.2f)  ", v, w, spt.dist(v, w));
                    for (DirectedEdge e : spt.path(v, w))
                        StdOut.print(e + "  ");
                    StdOut.println();
                }
                else {
                    StdOut.printf("%d to %d no path\n", v, w);
                }
            }
        }
    }
}
