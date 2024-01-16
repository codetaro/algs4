package ch4;

public interface Paths {
    boolean hasPathTo(int v);        // 是否存在从s到v的路径
    Iterable<Integer> pathTo(int v); // s到v的路径，如果不存在则返回null
}
