package ch5;

import edu.princeton.cs.algs4.Alphabet;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

// most significant digit first
public class MSD {
    private Alphabet alpha = Alphabet.EXTENDED_ASCII;
    private int R = 256;      // 基数
    private int M = 15;       // 小数组的切换阈值
    private String[] aux;     // 数据分类的辅助数组

    public MSD() {
    }

    public MSD(Alphabet alpha, int M) {
        this.alpha = alpha;
        this.R = alpha.R();
        this.M = M;
    }

    private int charAt(String s, int d) {
        if (d < s.length()) return alpha.toIndex(s.charAt(d));
        else return -1; // 当指定的位置超过了字符串的末尾时该方法返回 -1
    }

    public void sort(String[] a) {
        int N = a.length;
        aux = new String[N];
        sort(a, 0, N - 1, 0);
    }

    // 从第d个字符开始对a[lo]到a[hi]排序
    private void sort(String[] a, int lo, int hi, int d) {
        if (hi <= lo + M) {
            insertion(a, lo, hi, d);
            return;
        }

        int[] count = new int[R + 2];  // 计算频率
        for (int i = lo; i <= hi; i++)
            count[charAt(a[i], d) + 2]++;
        for (int r = 0; r < R + 1; r++)  // 将频率转换为索引
            count[r + 1] += count[r];
        for (int i = lo; i <= hi; i++) // 数据分类
            aux[count[charAt(a[i], d) + 1]++] = a[i];
        for (int i = lo; i <= hi; i++) // 回写
            a[i] = aux[i - lo];

        // 递归的以每个字符为键进行排序
        for (int r = 0; r < R; r++)
            // 取出同一组的字符串，并按下一位字符计数排序
            sort(a, lo + count[r], lo + count[r + 1] - 1, d + 1);
    }

    // 对前 d 个字符均相同的字符串执行插入排序
    private void insertion(String[] a, int lo, int hi, int d) {
        for (int i = lo; i <= hi; i++)
            for (int j = i; j > lo && less(a[j], a[j - 1], d); j--)
                exch(a, j, j - 1);
    }

    // is v less than w, starting at character d
    private boolean less(String v, String w, int d) {
        for (int i = d; i < Math.min(v.length(), w.length()); i++) {
            if (v.charAt(i) < w.charAt(i)) return true;
            if (v.charAt(i) > w.charAt(i)) return false;
        }
        return v.length() < w.length();
    }

    // exchange a[i] and a[j]
    private void exch(String[] a, int i, int j) {
        String temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }

    public static void main(String[] args) {
        String[] a = StdIn.readAllStrings();
        int n = a.length;
//        MSD msd = new MSD();
        MSD msd = new MSD(Alphabet.LOWERCASE, 0);
        msd.sort(a);
        for (int i = 0; i < n; i++)
            StdOut.println(a[i]);
    }
}
