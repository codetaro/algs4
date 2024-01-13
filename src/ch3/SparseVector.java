package ch3;

public class SparseVector {
    private LinearProbingHashST<Integer, Double> st;

    public SparseVector() {
        st = new LinearProbingHashST<>();
    }

    public int size() {
        return st.size();
    }
    public void put(int i, double x) {
        st.put(i, x);
    }
    public double get(int i) {
        if (!st.contains(i)) return 0.0;
        else                 return st.get(i);
    }
    public double dot(double[] that) {
        double sum = 0.0;
        for (int i : st.keys())
            sum += that[i] * this.get(i);
        return sum;
    }

    public static void main(String[] args) {
        int N = Integer.parseInt(args[0]);
        SparseVector[] a = new SparseVector[N];
        double[] x = new double[N];
        double[] b = new double[N];

        // 初始化 a[] 和 x[]

        for (int i = 0; i < N; i++) {
            b[i] = a[i].dot(x);
        }
    }
}
