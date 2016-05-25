package base;

/**
 * hash 策略
 * @author hzmawenjun .
 */
public interface HashStrategy {
    /**
     * 初始化
     */
    void init();

    /**
     * hash策略
     * @param key
     * @return
     */
    Object hash(String key);
}
