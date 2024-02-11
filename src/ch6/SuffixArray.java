package ch6;

import ch2.Quick3way;

public class SuffixArray {
    private final String[] suffixes;    // 后缀数组
    private final int N;                // 字符串(和数组)的长度

    public SuffixArray(String s) {
        N = s.length();
        suffixes = new String[N];
        for (int i = 0; i < N; i++)
            suffixes[i] = s.substring(i);
        Quick3way.sort(suffixes);
    }

    /**
     * 文本 text 的长度
     */
    public int length() { return N; }

    /**
     * 后缀数组中的第 i 个元素(i 在 0 到 N-1 之间)
     */
    public String select(int i) { return suffixes[i]; }

    /**
     * select(i) 的索引(i 在 0 到 N-1 之间)
     */
    public int index(int i) { return N - suffixes[i].length(); }

    /**
     * select(i) 和 select(i-1) 的最长公共前缀的长度(i 在 1 到 N-1 之间)
     */
    public int lcp(int i) {
        return lcp(suffixes[i], suffixes[i-1]);
    }
    private static int lcp(String s, String t) {
        int N = Math.min(s.length(), t.length());
        for (int i = 0; i < N; i++)
            if (s.charAt(i) != t.charAt(i)) return i;
        return N;
    }

    /**
     * 小于键 key 的后缀数量
     */
    public int rank(String key) {
        // 二分查找
        int lo = 0, hi = N - 1;
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            int cmp = key.compareTo(suffixes[mid]);
            if      (cmp < 0) hi = mid - 1;
            else if (cmp > 0) lo = mid + 1;
            else return mid;
        }
        return lo;
    }
}
