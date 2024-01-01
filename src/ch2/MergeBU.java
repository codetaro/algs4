package ch2;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class MergeBU {
    private static Comparable[] aux; // 归并所需的辅助数组

    public static void sort(Comparable[] a) {
        int N = a.length;
        aux = new Comparable[N];
        for (int sz = 1; sz < N; sz = sz+sz)         // sz子数组大小
            for (int lo = 0; lo < N-sz; lo += sz+sz) // lo:子数组索引
                merge(a, lo, lo+sz-1, Math.min(lo+sz+sz-1, N-1));
    }
    public static void merge(Comparable[] a, int lo, int mid, int hi) {
        // 原地归并的抽象方法
        int i = lo, j = mid+1;
        for (int k = 0; k <= hi; k++) aux[k] = a[k];
        for (int k = 0; k <= hi; k++) {
            if      (i > mid)              a[k] = aux[j++]; // 左半边用尽(取右半边的元素)
            else if (j > hi)               a[k] = aux[i++]; // 右半边用尽(取左半边的元素)
            else if (less(aux[j], aux[i])) a[k] = aux[j++]; // 右半边的当前元素小于左半边的当前元素(取右半边的元素)
            else                           a[k] = aux[j++]; // 右半边的当前元素大于等于左半边的当前元素(取左半边的元素)
        }
    }

    private static boolean less(Comparable v, Comparable w) {
        return v.compareTo(w) < 0;
    }

    private static void exch(Comparable[] a, int i, int j) {
        Comparable t = a[i];
        a[i] = a[j];
        a[j] = t;
    }

    private static void show(Comparable[] a) {
        for (int i = 0; i < a.length; i++) {
            StdOut.print(a[i] + " ");
        }
        StdOut.println();
    }

    public static boolean isSorted(Comparable[] a) {
        for (int i = 1; i < a.length; i++) {
            if (less(a[i], a[i-1])) return false;
        }
        return true;
    }

    public static void main(String[] args) {
        String[] a = In.readStrings();
        sort(a);
        assert isSorted(a);
        show(a);
    }
}
