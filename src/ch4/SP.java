package ch4;

public interface SP {
    double distTo(int v);                 // 从顶点 s 到 v 的距离，如果不存在 则路径为无穷大
    boolean hasPathTo(int v);             // 是否存在从顶点 s 到 v 的路径
    Iterable<DirectedEdge> pathTo(int v); // 从顶点 s 到 v 的路径，如果不存在 则为 null
}
