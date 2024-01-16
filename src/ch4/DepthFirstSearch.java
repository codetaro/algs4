package ch4;

public class DepthFirstSearch implements Search {
    private boolean[] marked;
    private int count;

    public DepthFirstSearch(Graph G, int s) {
        marked = new boolean[G.V()];
        dfs(G, s);
    }

    private void dfs(Graph G, int v) {
        marked[v] = true;
        count++;
        for (int w : G.adj(v))
            if (!marked[w]) dfs(G, w);
    }

    @Override
    public boolean marked(int w) {
        return marked[w];
    }
    @Override
    public int count() {
        return count;
    }
}
