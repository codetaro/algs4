package ch6;

public class FlowEdge {
    private final int v;            // 边的起点
    private final int w;            // 边的终点
    private final double capacity;  // 容量
    private double flow;            // 流量

    public FlowEdge(int v, int w, double capacity) {
        this.v = v;
        this.w = w;
        this.capacity = capacity;
        this.flow = 0.0;
    }

    public int from()           { return v; }
    public int to()             { return w; }
    public double capacity()    { return capacity; }
    public double flow()        { return flow; }

    public int other(int vertext) {
        if (vertext == v) return w;
        else if (vertext == w) return v;
        else throw new IllegalArgumentException("invalid endpoint");
    }

    /**
     * v 的剩余容量
     */
    public double residualCapacityTo(int vertex) {
        if      (vertex == v) return flow;              // 反向路径
        else if (vertex == w) return capacity - flow;   // 流向边的终点
        else throw new RuntimeException("Inconsistent edge");
    }

    /**
     * 将 v 的流量增加 delta
     */
    public void addResidualFlowTo(int vertex, double delta) {
        if      (vertex == v) flow -= delta;
        else if (vertex == w) flow += delta;
        else throw new RuntimeException("Inconsistent edge");
    }

    @Override
    public String toString() {
        return v + "->" + w + " " + flow + "/" + capacity;
    }
}
