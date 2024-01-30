package ch5;

import edu.princeton.cs.algs4.StdOut;

import java.math.BigInteger;
import java.util.Random;

public class RabinKarp {
    private String pat;   // 模式字符串(仅拉斯维加斯算法需要)
    private long patHash; // 模式字符串的散列值
    private int M;        // 模式字符串的长度
    private long Q;       // 一个很大的素数
    private int R = 256;  // 字母表的大小
    private long RM;      // R^(M-1) % Q

    public RabinKarp(String pat) {
        this.pat = pat;                  // 保存模式字符串(仅拉斯维加斯算法需要)
        this.M = pat.length();
        Q = longRandomPrime();
        RM = 1;
        for (int i = 1; i <= M - 1; i++) // 计算R^(M-1) % Q
            RM = (R * RM) % Q;           // 用于减去第一个数字时的计算
        patHash = hash(pat, M);
    }

    private static long longRandomPrime() {
        BigInteger prime = BigInteger.probablePrime(31, new Random());
        return prime.longValue();
    }

    public boolean check(int i) {        // 蒙特卡洛算法
        return true;                     // 对于拉斯维加斯算法，检查模式与txt(i..i-M+1)的匹配
    }

    private long hash(String key, int M) {
        // 计算key[0..M-1]的散列值
        long h = 0;
        for (int j = 0; j < M; j++)
            h = (R * h + key.charAt(j)) % Q;
        return h;
    }

    private int search(String txt) {
        // 在文本中查找相等的散列值
        int N = txt.length();
        long txtHash = hash(txt, M);
        if (patHash == txtHash && check(0)) return 0; // 一开始就匹配成功
        for (int i = M; i < N; i++) {
            // 在常数时间内算出 M 个字符的子字符串散列值
            txtHash = (txtHash + Q - RM * txt.charAt(i - M) % Q) % Q; // 减去第一个数字
            txtHash = (txtHash * R + txt.charAt(i)) % Q;              // 加上最后一个数字
            if (patHash == txtHash)                                   // 再次检查匹配
                if (check(i - M + 1))
                    return i - M + 1; // 找到匹配
        }
        return N;                     // 未找到匹配
    }

    public static void main(String[] args) {
        String pat = args[0];
        String txt = args[1];

        RabinKarp searcher = new RabinKarp(pat);
        int offset = searcher.search(txt);

        // print result
        StdOut.println("text:    " + txt);

        // from brute force search method 1
        StdOut.print("pattern: ");
        for (int i = 0; i < offset; i++)
            StdOut.print(" ");
        StdOut.println(pat);
    }
}
