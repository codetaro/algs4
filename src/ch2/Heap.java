package ch2;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Heap {
    // This class should not be instantiated.
    private Heap() { }

    public static void sort(Comparable[] pq) {
        int n = pq.length;

        // heapify phase
        for (int k = n / 2; k >= 1; k--)
            sink(pq, k, n);

        // sortdown phase
        int k = n;
        while (k > 1) {
            exch(pq, 1, k--);
            sink(pq, 1, k);
        }
    }

    private static void sink(Comparable[] pq, int k, int n) {
        while (2 * k <= n) {
            int j = 2 * k;
            if (j < n && less(pq, j, j + 1)) j++;
            if (!less(pq, k, j)) break;
            exch(pq, k, j);
            k = j;
        }
    }

    private static boolean less(Comparable[] pq, int i, int j) {
        return pq[i - 1].compareTo(pq[j - 1]) < 0;
    }

    private static void exch(Object[] pq, int i, int j) {
        Object swap = pq[i - 1];
        pq[i - 1] = pq[j - 1];
        pq[j - 1] = swap;
    }

    private static void show(Comparable[] a) {
        for (int i = 0; i < a.length; i++) {
            StdOut.print(a[i] + " ");
        }
        StdOut.println();
    }

    public static void main(String[] args) {
        String[] a = In.readStrings();
        Heap.sort(a);
        show(a);
    }
}
