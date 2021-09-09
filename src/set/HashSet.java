package set;

import map.HashMap;
import tree.RBTree;

import java.util.function.Consumer;

public class HashSet<E> implements Set<E> {

    private HashMap<E, Object> hashMap = new HashMap<>();

    @Override
    public int size() {
        return hashMap.size();
    }

    @Override
    public boolean isEmpty() {
        return hashMap.isEmpty();
    }

    @Override
    public boolean contains(E element) {
        return hashMap.containsKey(element);
    }

    @Override
    public void add(E element) {
        if (hashMap.containsKey(element)) {
            return;
        }
        hashMap.put(element, null);
    }

    @Override
    public void remove(E element) {
        hashMap.remove(element);
    }

    @Override
    public void clear() {
        hashMap.clear();
    }

    @Override
    public void traverse(Consumer<E> consumer) {
//        hashMap.traversal();
    }
}
