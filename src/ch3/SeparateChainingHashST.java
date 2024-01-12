package ch3;

import ch1.Queue;

public class SeparateChainingHashST<Key, Value> {
    private static final int INIT_CAPACITY = 4;

    private int N;                               // 键值对总数
    private int M;                               // 散列表的大小
    private SequentialSearchST<Key, Value>[] st; // 存放链表对象的数组

    public SeparateChainingHashST() {
        this(INIT_CAPACITY);
    }
    public SeparateChainingHashST(int M) {
        // 创建M条链表
        this.M = M;
        st = (SequentialSearchST<Key, Value>[]) new SequentialSearchST[M];
        for (int i = 0; i < M; i++) {
            st[i] = new SequentialSearchST<>();
        }
    }

    private void resize(int chains) {
        SeparateChainingHashST<Key, Value> temp = new SeparateChainingHashST<>(chains);
        for (int i = 0; i < M; i++) {
            for (Key key : st[i].keys())
                temp.put(key, st[i].get(key));
        }
        this.M = temp.M;
        this.N = temp.N;
        this.st = temp.st;
    }

    // returns value between 0 and m-1
    private int hash(Key key) {
        return (key.hashCode() & 0x7fffffff) % M; // 将一个 32 位整数变为一个 31 位非负整数
    }
    public Value get(Key key) {
        return st[hash(key)].get(key);
    }
    public void put(Key key, Value val) {
        if (key == null) throw new IllegalArgumentException("first argument to put() is null");
        if (val == null) {
            delete(key);
            return;
        }

        // double table size if average length of list >= 10
        if (N >= 10 * M) resize(2 * M);

        int i = hash(key);
        if (!st[i].contains(key)) N++;
        st[i].put(key, val);
    }
    public Iterable<Key> keys() {
        Queue<Key> queue = new Queue<>();
        for (int i = 0; i < M; i++) {
            for (Key key : st[i].keys())
                queue.enqueue(key);
        }
        return queue;
    }
    public void delete(Key key) {
        if (key  == null) throw new IllegalArgumentException("argument to delete() is null");

        int i = hash(key);
        if (st[i].contains(key)) N--;
        st[i].delete(key);

        // halve table size if average length of list <= 2
        if (M > INIT_CAPACITY && N <= 2 * M) resize(M / 2);
    }

    /**
     * Unit tests the SeparateChainingHashST data type.
     * @param args – the command-line arguments
     */
    public static void main(String[] args) {
        // print keys
    }
}
