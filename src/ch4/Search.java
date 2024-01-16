package ch4;

public interface Search {
    boolean marked(int v); // v 和 s 是连通的吗
    int count();           // 与 s 连通的顶点总数
}
