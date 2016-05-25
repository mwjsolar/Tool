package base;

import delegete.javaagent.Agent;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * LRU
 * @author hzmawenjun .
 */

public class LRULinkedHashMap<K,V> extends LinkedHashMap<K,V> {
    /**
     * queue size of lru
     */
    private  int capacity = 100;


    public LRULinkedHashMap(int capacity) {
        super();
        this.capacity = capacity;
    }

    @Agent
    public <T> T testAgent(T t)  {
        System.out.println("test agent");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return t;
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry eldest) {
        return size() > capacity;
    }

    public static void main(String[] args) {
        LRULinkedHashMap<String,Object> lru = new LRULinkedHashMap<String,Object>(5);
        lru.put("1",1);
        lru.put("2",2);
        lru.put("3",3);
        lru.put("4",4);
        lru.put("5",5);
        System.out.println(lru);
        lru.put("6",6);
        System.out.println(lru);

        lru.testAgent("1");
    }
}
