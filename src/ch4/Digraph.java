package ch4;

import ch1.Bag;
import edu.princeton.cs.algs4.In;

public class Digraph {
    private static final String NEWLINE = System.lineSeparator();

    private final int V;
    private int E;
    private Bag<Integer>[] adj;

    public Digraph(int V) {
        this.V = V;
        this.E = 0;
        adj = (Bag<Integer>[]) new Bag[V];
        for (int v = 0; v < V; v++)
            adj[v] = new Bag<>();
    }
    public Digraph(In in) {
        this(in.readInt());
        int E = in.readInt();
        for (int i = 0; i < E; i++) {
            int v = in.readInt();
            int w = in.readInt();
            addEdge(v, w);
        }
    }

    public int V() { return V; }
    public int E() { return E; }
    public void addEdge(int v, int w) {
        adj[v].add(w);
        E++;
    }
    public Iterable<Integer> adj(int v) {
        return adj[v];
    }
    public Digraph reverse() {
        // 可以找出"指向"每个顶点的所有边
        Digraph R = new Digraph(V);
        for (int v = 0; v < V; v++)
            for (int w : adj(v))
                R.addEdge(w, v);
        return R;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(V + " vertices, " + E + " edges " + NEWLINE);
        for (int v = 0; v < V; v++) {
            s.append(String.format("%d: ", v));
            for (int w : this.adj(v))
                s.append(String.format("%d ", w));
            s.append(NEWLINE);
        }
        return s.toString();
    }
}
