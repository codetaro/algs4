package ch5;

// Ternary Search Trie
public class TST<Value> {
    private Node root;

    private class Node {
        char c;
        Node left, mid, right;
        Value val;
    }

    public Value get(String key) {
        if (key == null) throw new IllegalArgumentException("calls get() with null argument");
        if (key.length() == 0) throw new IllegalArgumentException("key must have length >= 1");
        Node x = get(root, key, 0);
        if (x == null) return null;
        return x.val;
    }
    private Node get(Node x, String key, int d) {
        if (x == null) return null;
        char c = key.charAt(d);
        if      (c < x.c) return get(x.left, key, d);
        else if (c > x.c) return get(x.right, key, d);
        else if (d < key.length() - 1) // 如果相等，则选择中链接并继续向下查找
                          return get(x.mid, key, d+1);
        else              return x;
    }

    public void put(String key, Value val) {
        root = put(root, key, val, 0);
    }
    private Node put(Node x, String key, Value val, int d) {
        char c = key.charAt(d);
        if (x == null) { x = new Node(); x.c = c; }
        if      (c < x.c) x.left = put(x.left, key, val, d);
        else if (c > x.c) x.right = put(x.right, key, val, d);
        else if (d < key.length() - 1) // 如果相等，则选择中链接并继续向下查找
                          x.mid = put(x.mid, key, val, d+1);
        else x.val = val;              // 命中并更新结点的值
        return x;
    }
}
