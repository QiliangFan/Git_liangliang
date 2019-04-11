import java.util.*;
 class Cache<K, V> {
    private int MAX_SIZE = 0;
    private int size = 0;
    private HashMap<K, V> container;
    private LinkedList<K> orders = new LinkedList<>();

    private boolean delete() {
        K key = orders.getFirst();
        container.remove(key);
        orders.removeFirst();
        size--;
        return true;
    }

    public Cache(int size) {
        this.MAX_SIZE = size;
        container = new HashMap<>();
    }

    public void put(K key, V value) {
        if (size >= MAX_SIZE) {
            delete();
        }
        if (container.get(key)!=null) {
            container.remove(key);
            container.put(key, value);
        } else {
            container.put(key, value);
            size++;
        }
        if (orders.contains(key)) {
            orders.remove(key);
            orders.addLast(key);
        } else {
            orders.addLast(key);
        }
    }

    public V get(K key) {
        if (orders.contains(key)) {
            orders.remove(key);
            orders.addLast(key);
        } else {
            orders.addLast(key);
        }
        return container.get(key);
    }

}
public class thirdproblem{
    public static void main(String[] args) {
        Cache<Integer, Integer> cache = new Cache<>(5);
        cache.put(1, 1); // key:1, value:1
        cache.put(2, 2);
        cache.get(1); // returns 1
        cache.put(3, 3); // evicts key 2
        cache.get(2); // returns -1 (not found)
        cache.put(4, 4); // evicts key 1
        cache.get(1); // returns -1 (not found)
        cache.get(3); // returns 3
        cache.get(4);
        System.out.println(cache);
    }
}