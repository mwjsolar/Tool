package cache.ehcache;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

/**
 * Created by hzmawenjun on 2016/5/11.
 */
public class CacheManagerTest {
    public static void main(String[] args) {

        System.out.println(CacheManagerTest.class.getResource("/"));
        CacheManager cacheManager = CacheManagerFacrory.createCacheManager(
                "D://work//code//tool//target//classes//"+"ehcache.xml");
        Cache cache = cacheManager.getCache("sampleCache1");
        cache.put(new Element("key1",1));
        System.out.println(cache.get("key1"));


    }
}
