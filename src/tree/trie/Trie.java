package tree.trie;

import java.util.HashMap;

public class Trie<V> {

    private int size;
    private Node<V> root;

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void clear() {
        size = 0;
        root = null;
    }

    public boolean contains(String key) {
        Node<V> node = node(key);
        return  node != null && node.word;
    }

    public V get(String key) {
        Node<V> node = node(key);
        return node != null && node.word ? node.value : null;
    }

    public V add(String key, V value) {
        keyCheck(key);
        if (root == null) {
            root = new Node<>(null);
        }

        Node<V> node = root;
        for (int i = 0; i < key.length(); i++) {
            char c = key.charAt(i);
            boolean emptyChild = node.children == null;
            Node<V> newNode = emptyChild ? null : node.children.get(c);
            if (newNode == null) {
                newNode = new Node<>(node);
                newNode.character = c;
                node.children = emptyChild ? new HashMap<>() : node.children;
                node.children.put(c, newNode);
            }
            node = newNode;
        }

        if (node.word) {        // 已经存在这个单词
            V oldValue = node.value;
            node.value = value;
            return oldValue;
        }

        // 新增一个单词
        node.word = true;
        node.value = value;
        size++;
        return null;
    }

    public V remove(String str) {
        Node<V> node = node(str);
        if (node == null || !node.word) {
            return null;
        }

        size--;
        // 如果还有子节点
        if (node.children != null && !node.children.isEmpty()) {
            V oldValue = node.value;
            node.word = false;
            node.value = null;
            return oldValue;
        }

        // 如果没有子节点
        Node<V> parent = null;
        while((parent = node.parent) != null) {
            parent.children.remove(node.character);
            if (parent.word || !parent.children.isEmpty()) {
                break;
            }
            node = parent;
        }
        return null;
    }

    public boolean startWith(String prefix) {
        return node(prefix) != null;
    }

    private void keyCheck(String key) {
        if (key == null || key.length() == 0) {
            throw new IllegalArgumentException("key cannot be empty");
        }
    }

    private Node<V> node(String key) {
        keyCheck(key);

        Node<V> node = root;
        for (int i = 0; i < key.length(); i++) {
            if (node == null || node.children == null || node.children.isEmpty()) {
                return null;
            }
            char c = key.charAt(i);
            node = node.children.get(c);
        }

        return node;
    }

    private static class Node<V> {
        Node<V> parent;
        HashMap<Character, Node<V>> children;
        Character character;
        V value;
        boolean word;       // 是否为单词结尾

        public Node(Node<V> parent) {
            this.parent = parent;
        }
    }

}
