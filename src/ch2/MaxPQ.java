package ch2;

public class MaxPQ<Key extends Comparable<Key>> {
    private Key[] pq;  // 基于堆的完全二叉树
    private int N = 0; // 存储于pq[1..N]中，pq[0]没有使用

    public MaxPQ(int maxN) {
        pq = (Key[]) new Comparable[maxN + 1];
    }

    public boolean isEmpty() {
        return N == 0;
    }
    public int size() {
        return N;
    }
    public void insert(Key v) {
        // double size of array if necessary
        if (N == pq.length - 1) resize(2 * pq.length);

        pq[++N] = v;
        swim(N);
    }
    public Key delMax() {
        Key max = pq[1];
        exch(1, N--);
        pq[N+1] = null; // 防止对象游离
        sink(1);

        // half size of array if necessary
        if ((N > 0) && (N == (pq.length - 1) / 4)) resize(pq.length / 2);
        return max;
    }

    // 辅助方法
    private void resize(int capacity) {
        assert capacity > N;
        Key[] temp = (Key[]) new Comparable[capacity];
        for (int i = 1; i <= N; i++) {
            temp[i] = pq[i];
        }
        pq = temp;
    }
    private boolean less(int i, int j) {
        return pq[i].compareTo(pq[j]) < 0;
    }
    private void exch(int i, int j) {
        Key t = pq[i];
        pq[i] = pq[j];
        pq[j] = t;
    }
    private void swim(int k) {
        while (k > 1 && less(k/2, k)) {
            exch(k/2, k);
            k = k/2;
        }
    }
    private void sink(int k) {
        while (2*k <= N) {
            int j = 2*k;
            if (j < N && less(j, j+1)) j++;
            if (!less(k, j)) break;
            exch(k, j);
            k = j;
        }
    }
}
