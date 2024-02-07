package ch5;

import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;
import edu.princeton.cs.algs4.TST;

public class LZW {
    private static final int R = 256;  // 输入字符数
    private static final int L = 4096; // 编码总数=2^12
    private static final int W = 12;   // 编码宽度

    // 单字符的编码的开头为 0， 其他编码从 100 开始
    public static void compress() {
        String input = BinaryStdIn.readString();
        TST<Integer> st = new TST<>(); // Ternary Search Trie

        for (int i = 0; i < R; i++)
            st.put("" + (char) i, i);
        int code = R + 1;              // R为文件结束(EOF)的编码

        while (input.length() > 0) {
            String s = st.longestPrefixOf(input); // 找到匹配的最长前缀
            BinaryStdOut.write(st.get(s), W);     // 打印出s的编码

            int t = s.length();
            if (t < input.length() && code < L)   // 将s加入符号表
                st.put(input.substring(0, t+1), code++);
            input = input.substring(t);           // 从输入中读取s
        }
        BinaryStdOut.write(R, W);                 // 写入文件结束标记
        BinaryStdOut.close();
    }

    public static void expand() {
        String[] st = new String[L]; // 逆向编译表
        int i; // 下一个待补全的编码值

        for (i = 0; i < R; i++) // 用字符初始化编译表 (和所有单个 ASCII 字符的字符串关联)
            st[i] = "" + (char) i;
        st[i++] = " "; // (未使用)文件结束标记(EOF)的前瞻字符

        int codeword = BinaryStdIn.readInt(W);
        String val = st[codeword];             // 含有第一个字符的字符串
        while (true) {
            BinaryStdOut.write(val);           // 1. 输出当前字符串 val
            codeword = BinaryStdIn.readInt(W); // 2. 从输入中读取一个编码 x
            if (codeword == R) break;
            String s = st[codeword];           // 3. 在符号表中将 s 设为和 x 相关联的值
            if (i == codeword)                 // 如果前瞻字符不可用
                s = val + val.charAt(0);       // 根据上一个字符串的首字母得到编码的字符串
            if (i < L)
                st[i++] = val + s.charAt(0);   // 4. 在符号表中将下一个未分配的编码值设为 val+c，其中 c 为 s 的首字母
            val = s;                           // 5. 将当前字符串 val 设为 s
        }
        BinaryStdOut.close();
    }
}
