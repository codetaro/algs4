package ch3;

import ch1.Queue;

public class SequentialSearchST<Key, Value> {
    private int n;      // number of key-value pairs
    private Node first; // 链表首结点

    private class Node {
        // 链表结点的定义
        Key key;
        Value val;
        Node next;

        public Node(Key key, Value val, Node next) {
            this.key = key;
            this.val = val;
            this.next = next;
        }
    }

    public boolean contains(Key key) {
        if (key == null) throw new IllegalArgumentException("argument to contains() is null");
        return get(key) != null;
    }

    public Value get(Key key) {
        // 查找给定的键，返回相关联的值
        for (Node x = first; x != null; x = x.next)
            if (key.equals(x.key)) return x.val; // 命中
        return null;                             // 未命中
    }

    public void put(Key key, Value val) {
        // 查找给定的键，找到则更新其值，否则在表中新建结点
        for (Node x = first; x != null; x = x.next)
            if (key.equals(x.key)) { x.val = val; return; } // 命中，更新
        first = new Node(key, val, first);                  // 未命中，新建结点
        n++;
    }

    public int size() {
        return n;
    }

    public Iterable<Key> keys() {
        Queue<Key> queue = new Queue<>();
        for (Node x = first; x != null; x = x.next)
            queue.enqueue(x.key);
        return queue;
    }

    public void delete(Key key) {
        if (key == null) throw new IllegalArgumentException("argument to delete() is null");
        first = delete(first, key);
    }

    private Node delete(Node x, Key key) {
        if (x == null) return null; // 结束条件
        if (key.equals(x.key)) {
            n--;
            return x.next; // 命中（删除结点，重新建立链接）
        }
        x.next = delete(x.next, key); // 未命中（检查下一结点，递归）
        return x;
    }
}
