package cache.ehcache;

import net.sf.ehcache.CacheManager;
import org.apache.commons.lang.StringUtils;

/**
 * Created by hzmawenjun on 2016/5/11.
 */
public class CacheManagerFacrory {

    public static CacheManager createCacheManager(String settingConfig) {
        if (StringUtils.isBlank(settingConfig))
            return null;

        CacheManager cacheManager = CacheManager.create(settingConfig);
        return cacheManager;
    }
}
