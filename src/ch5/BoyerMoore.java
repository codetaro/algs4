package ch5;

import edu.princeton.cs.algs4.StdOut;

public class BoyerMoore {
    private int[] right;
    private String pat;

    public BoyerMoore(String pat) {
        // 计算跳跃表
        this.pat = pat;
        int M = pat.length();
        int R = 256;
        right = new int[R];
        for (int c = 0; c < R; c++)
            right[c] = -1;            // 不包含在模式字符串中的字符的值为-1
        for (int j = 0; j < M; j++)
            right[pat.charAt(j)] = j; // 包含在模式字符串中的字符的值为它在其中出现的最右位置
    }

    public int search(String txt) {
        // 在txt中查找模式字符串
        int N = txt.length();
        int M = pat.length();
        int skip;
        for (int i = 0; i < N - M; i += skip) {
            // 模式字符串和文本在位置i匹配吗?
            skip = 0;
            for (int j = M - 1; j >= 0; j--)
                if (pat.charAt(j) != txt.charAt(i+j)) {
                    skip = j - right[txt.charAt(i+j)];
                    if (skip < 1) skip = 1;
                    break;
                }
            if (skip == 0) return i;  // 找到匹配
        }
        return N;                     // 未找到匹配
    }

    public static void main(String[] args) {
        String pat = args[0];
        String txt = args[1];

        BoyerMoore boyerMoore = new BoyerMoore(pat);
        int offset = boyerMoore.search(txt);

        // print result
        StdOut.println("text:    " + txt);

        StdOut.print("pattern: ");
        for (int i = 0; i < offset; i++)
            StdOut.print(" ");
        StdOut.println(pat);
    }
}
