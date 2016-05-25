package base;

import encrpt.encode.MD5;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import zk.watcher.ServerListWatch;

import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * 一致性hash
 * @author hzmawenjun .
 */
public class ConsistentHash implements HashStrategy {

    private final static Logger log = LoggerFactory.getLogger(ConsistentHash.class);

    private List<String> serverList;

    private final ServerListWatch serverListWatch = new ServerListWatch();

    private final static int VIRTUAL_NUM =10;

    private final TreeMap<Long,String> nodeMap = new TreeMap<Long, String>();

    @Override
    public void init() {
        serverList = serverListWatch.getServerList();
        updateVirtualNodeMap();
    }

    protected void updateVirtualNodeMap() {
        for (int i = 0 ; i < serverList.size() ; i++) {
            for (int j = 0 ; j < VIRTUAL_NUM ; j++) {
                nodeMap.put(convertMd5("NODE"+i+"VIRTUAL"+j),serverList.get(i));
            }
        }
    }

    public Long convertMd5(String key) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(key.getBytes());
            byte[] encodeBytes = messageDigest.digest();
            return byteToLong(encodeBytes);
        } catch (NoSuchAlgorithmException e) {
            log.error("convert md5 error",e);
        }

        return null;
    }

    private long byteToLong(byte[] hashByte) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(Long.BYTES);
        byteBuffer.put(hashByte);
        byteBuffer.flip();
        return byteBuffer.getLong();
    }

    @Override
    public Object hash(String key) {
        if (StringUtils.isBlank(key))
            return null;

        Long hashKey = convertMd5(key);
        SortedMap<Long,String> sortedMap = nodeMap.tailMap(hashKey);
        if (!sortedMap.isEmpty())
            return sortedMap.firstKey();
        else
            return nodeMap.firstKey();
    }
}
