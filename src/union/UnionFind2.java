package union;

import java.util.*;

/**
 * @Description 并查集
 * @Author xbockx
 * @Date 3/26/2022
 */
public class UnionFind2<V> {

    // 当前元素对应的ELEMENT
    private HashMap<V, Element<V>> elementMap;
    // 当前ELEMENT指向的头节点
    private HashMap<Element<V>, Element<V>> headMap;
    // 当前ELEMENT包含的子节点个数
    private HashMap<Element<V>, Integer> sizeMap;

    /**
     * 用于包装的内部类
     * @param <V>
     */
    private static class Element<V> {
        V value;
        public Element(V value){
            this.value = value;
        }
    }

    public UnionFind2(List<V> list) {
        elementMap = new HashMap<>();
        headMap = new HashMap<>();
        sizeMap = new HashMap<>();
        for(V v: list) {
            Element<V> element = new Element<>(v);
            elementMap.put(v, element);
            headMap.put(element, element);
            sizeMap.put(element, 1);
        }
    }

    /**
     * 判断两个元素是否为一个集合
     * @param v1
     * @param v2
     * @return
     */
    public boolean isSameSet(V v1, V v2) {
        if (elementMap.containsKey(v1) && elementMap.containsKey(v2)) {
            return findHead(elementMap.get(v1)) == findHead(elementMap.get(v2));
        }
        return false;
    }

    /**
     * 将两个元素放到一个集合
     * @param v1
     * @param v2
     */
    public void union(V v1, V v2) {
        if(elementMap.containsKey(v1) && elementMap.containsKey(v2)) {
            Element<V> v1Head = findHead(elementMap.get(v1));
            Element<V> v2Head = findHead(elementMap.get(v2));
            if (v1Head != v2Head) {
                int sizeV1 = sizeMap.get(v1Head);
                int sizeV2 = sizeMap.get(v2Head);
                Element<V> big = sizeV1 >= sizeV2 ? v1Head : v2Head;
                Element<V> small = big == v1Head ? v2Head : v1Head;
                headMap.put(small, big);
                sizeMap.put(big, sizeMap.get(big) + sizeMap.get(small));
                sizeMap.remove(small);
            }
        }
    }

    /**
     * 找到 element 指向的头节点
     * @param element
     * @return
     */
    private Element<V> findHead(Element<V> element) {
        Queue<Element<V>> queue = new LinkedList<>();
        while (element != headMap.get(element)) {
            queue.offer((element));
            element = headMap.get(element);
        }
        while(!queue.isEmpty()) {
            headMap.put(queue.poll(), element);
        }
        return element;
    }

}
