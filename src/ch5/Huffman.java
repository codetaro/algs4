package ch5;

import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;
import edu.princeton.cs.algs4.MinPQ;

public class Huffman {
    private static int R = 256; // ASCII字母表

    // 霍夫曼单词查找树中的结点
    private static class Node implements Comparable<Node> {
        private char ch;  // 内部结点不会使用该变量
        private int freq; // 展开过程不会使用该变量
        private final Node left, right;

        public Node(char ch, int freq, Node left, Node right) {
            this.ch = ch;
            this.freq = freq;
            this.left = left;
            this.right = right;
        }

        public boolean isLeaf() {
            return left == null && right == null;
        }

        public int compareTo(Node that) {
            return this.freq - that.freq;
        }
    }

    public static void compress() {
        // 读取输入
        String s = BinaryStdIn.readString();
        char[] input = s.toCharArray();
        // 统计频率
        int[] freq = new int[R];
        for (int i = 0; i < input.length; i++)
            freq[input[i]]++;
        // 构造霍夫曼编码树
        Node root = buildTrie(freq);
        // (递归地)构造编译表
        String[] st = new String[R];
        buildCode(st, root, "");
        // (递归地)打印解码用的单词查找树
        writeTrie(root);
        // 打印字符总数
        BinaryStdOut.write(input.length);
        // 使用霍夫曼编码处理输入
        for (int i = 0; i < input.length; i++) {
            String code = st[input[i]];
            for (int j = 0; j < code.length(); j++)
                if (code.charAt(j) == '1')
                    BinaryStdOut.write(true);
                else
                    BinaryStdOut.write(false);
        }
        BinaryStdOut.close();
    }

    private static Node buildTrie(int[] freq) {
        // 使用多棵单结点树初始化优先队列
        MinPQ<Node> pq = new MinPQ<>();
        for (char c = 0; c < R; c++)
            if (freq[c] > 0)
                pq.insert(new Node(c, freq[c], null, null));

        while (pq.size() > 1) {
            // 合并两棵频率最小的树
            Node x = pq.delMin();
            Node y = pq.delMin();
            Node parent = new Node('\0', x.freq + y.freq, x, y);
            pq.insert(parent);
        }
        return pq.delMin();
    }

    // 输出单词查找树的比特字符串
    private static void writeTrie(Node x) {
        if (x.isLeaf()) { // 当访问的是一个叶子结点时
            BinaryStdOut.write(true);
            BinaryStdOut.write(x.ch);
            return;
        }
        BinaryStdOut.write(false); // 当访问的是一个内部结点时
        writeTrie(x.left);
        writeTrie(x.right);
    }

    // 使用单词查找树构造编译表
    private static String[] buildCode(Node root) {
        String[] st = new String[R];
        buildCode(st, root, "");
        return st;
    }

    // 使用单词查找树构造编译表(递归)
    private static void buildCode(String[] st, Node x, String s) {
        if (x.isLeaf()) {
            st[x.ch] = s;
            return;
        }
        buildCode(st, x.left, s + '0');
        buildCode(st, x.right, s + '1');
    }

    public static void expand() {
        Node root = readTrie();
        int N = BinaryStdIn.readInt();
        // 展开第i个编码所对应的字母
        for (int i = 0; i < N; i++) {
            Node x = root;
            while (!x.isLeaf()) {
                if (BinaryStdIn.readBoolean())
                    x = x.right;
                else
                    x = x.left;
            }
            BinaryStdOut.write(x.ch); // 输出叶子结点的字符并重新回到根结点
        }
        BinaryStdOut.close();
    }

    private static Node readTrie() {
        if (BinaryStdIn.readBoolean()) // 叶子结点(比特为 1)
            return new Node(BinaryStdIn.readChar(), 0, null, null);
        return new Node('\0', 0, readTrie(), readTrie()); // 创建一个内部结点并(递归地)继续构造它的左右子树
    }

    public static void main(String[] args) {
        if (args[0].equals("-")) compress();
        else if (args[0].equals("+")) expand();
        else throw new IllegalArgumentException("Illegal command line argument");
    }
}
