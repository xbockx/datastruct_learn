package map;

public class LinkedHashMap<K, V> extends HashMap {
    private LinkedNode<K, V> first;
    private LinkedNode<K, V> last;

    @Override
    protected void afterRemove(Node willNode, Node removedNode) {
        LinkedNode<K, V> removedLinkedNode = (LinkedNode<K, V>) removedNode;
        LinkedNode<K, V> willLinkedNode = (LinkedNode<K, V>) willNode;
        if (willLinkedNode != removedLinkedNode) {

            // 交换prev
            LinkedNode<K, V> tmp = willLinkedNode.prev;
            willLinkedNode.prev = removedLinkedNode.prev;
            removedLinkedNode.prev = tmp;
            if (willLinkedNode.prev == null) {
                first = willLinkedNode;
            } else {
                willLinkedNode.prev.next = willLinkedNode;
            }
            if (removedLinkedNode.prev == null) {
                first = removedLinkedNode;
            } else {
                removedLinkedNode.prev.next = removedLinkedNode;
            }

            // 交换next
            tmp = willLinkedNode.next;
            willLinkedNode.next = removedLinkedNode.next;
            removedLinkedNode.next = willLinkedNode.next;
            if (willLinkedNode.next == null) {
                last = willLinkedNode;
            } else {
                willLinkedNode.next.prev = willLinkedNode;
            }
            if (removedLinkedNode.next == null) {
                last = removedLinkedNode;
            } else {
                removedLinkedNode.next.prev = removedLinkedNode;
            }
        }

        LinkedNode<K, V> prev = removedLinkedNode.prev;
        LinkedNode<K, V> next = removedLinkedNode.next;

        if (prev == null) {
            first = next;
        } else {
            prev.next = next;
        }
        if (next == null) {
            last = prev;
        } else {
            next.prev = prev;
        }
    }

    @Override
    public void clear() {
        super.clear();
        first = null;
        last = null;
    }

    @Override
    public void traversal(Visitor visitor) {
        if (visitor == null) {
            return;
        }
        LinkedNode<K, V> node = first;
        while(node != null) {
            if (visitor.visit(node.key, node.value)) {
                return;
            }
            node = node.next;
        }
    }

    @Override
    protected Node createNode(Object key, Object value, Node parent) {
        LinkedNode node = new LinkedNode(key, value, parent);

        if (first == null) {
            first = last = node;
        } else {
            last.next = node;
            node.prev = last;
            last = node;
        }

        return node;
    }

    private static class LinkedNode<K, V> extends Node<K, V> {
        LinkedNode<K, V> prev;
        LinkedNode<K, V> next;
        public LinkedNode(K key, V value, Node<K, V> parent) {
            super(key, value, parent);
        }
    }

}
