package ch5;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class CountSort {
    private Student[] students;
    private final int N;
    private int R;

    public CountSort(In in) {
        N = in.readInt();
        students = new Student[N];
        for (int i = 0; i < N; i++) {
            String name = in.readString();
            int section = in.readInt();
            students[i] = new Student(name, section);

            R = Math.max(R, section);
        }
        sort();
    }

    private void sort() {
        Student[] aux = new Student[N];
        int[] count = new int[R + 1];

        // 计算出现频率
        for (int i = 0; i < N; i++) {
            count[students[i].getSection()]++;
        }
        // 将频率转换为索引
        for (int i = 1; i <= R; i++) {
            count[i] += count[i - 1];
        }
        // 将元素分类
        for (int i = N - 1; i >= 0; i--) {
            aux[count[students[i].getSection()] - 1] = students[i];
            count[students[i].getSection()]--;
        }
        // 回写
        for (int i = 0; i < N; i++) {
            students[i] = aux[i];
        }
    }

    public static void main(String[] args) {
        In in = new In(args[0]);
        CountSort cs = new CountSort(in);
        for (Student s : cs.students)
            StdOut.println(s);
    }

    private class Student {
        private final String name;
        private final int section;

        public Student(String name, int section) {
            this.name = name;
            this.section = section;
        }

        public String getName() {
            return name;
        }
        public int getSection() {
            return section;
        }

        @Override
        public String toString() {
            return String.format("%-8s  %d", name, section);
        }
    }
}
