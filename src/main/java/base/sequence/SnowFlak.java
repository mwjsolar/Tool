package base.sequence;

import org.apache.http.util.Args;

/**
 * 雪花算法
 * 组成部分为时间戳+数据中心+机器标识+序列号
 * 必须保证单例封装生成的,这样才能保证产生的序列号的唯一性
 * Created by mwjsolar on 17/4/7.
 */
public final class SnowFlak {

    private final long BASE_TIMESTAMP = 1491642426245L;

    private volatile long lastTimeStamp = BASE_TIMESTAMP;

    private final int dataCenterId;
    private final int machineId;

    private  volatile int currentSeq = 0;

    //序列号的位数
    private final static int SEQUENCE_LEFT  = 5;

    private final static int MAX_SEQUENCE = (1 << (SEQUENCE_LEFT+1)) - 1 ;

    //机器号的位数
    private static final int MACHINE_NUMBER_BITS = 6;

    //数据中心的位数
    private static final int DATA_CENTER_BITS = 6;

    private static final int MAX_MACHINE_NUMBER = (1 << (MACHINE_NUMBER_BITS + 1)) -1;

    private static final int MAX_DATA_CENTER = (1 << (DATA_CENTER_BITS + 1)) -1;
    //机器号的偏移位数
    private final  int MACHINE_NUMBER_LIFT = SEQUENCE_LEFT;

    //数据中心的偏移量
    private final  int DATA_CENTER_LIFT = MACHINE_NUMBER_LIFT + MACHINE_NUMBER_BITS;

    //时间戳的偏移量
    private final int TIMESTAMP_LIFT = DATA_CENTER_LIFT + DATA_CENTER_BITS;

    private static SnowFlak INSTANCE;

    public static SnowFlak create(final int dataCenterId,final int machineId) {
        if (INSTANCE == null) {
            synchronized (SnowFlak.class) {
                if (INSTANCE == null) {
                    INSTANCE = new SnowFlak(dataCenterId,machineId);
                }
            }
        }
        return INSTANCE;
    }

    private SnowFlak(int dataCenterId, int machineId) {
        Args.check(dataCenterId <= MAX_DATA_CENTER,"data center exceed max size");
        Args.check(machineId <= MAX_MACHINE_NUMBER,"machine id exceed max size");

        this.dataCenterId = dataCenterId;
        this.machineId = machineId;
    }

    public synchronized long nextId() {
        long currentTimeStamp = System.currentTimeMillis();
        if (currentTimeStamp == lastTimeStamp) { //在同一个ms范围内
            currentSeq = currentSeq + 1;
            lastTimeStamp = currentTimeStamp;
            if (currentSeq > MAX_SEQUENCE) {
                lastTimeStamp = getNextTimestamp();
            }
        } else {
            currentSeq = 0;
            lastTimeStamp = currentTimeStamp;
        }
        return currentSeq | machineId << MACHINE_NUMBER_LIFT
                | dataCenterId << DATA_CENTER_LIFT
                | (lastTimeStamp-BASE_TIMESTAMP) << TIMESTAMP_LIFT;

    }

    private long getNextTimestamp() {
        while (lastTimeStamp <= System.currentTimeMillis())
        lastTimeStamp = System.currentTimeMillis();
        return lastTimeStamp;
    }

    public static void main(String[] args) {
        SnowFlak snowFlak = SnowFlak.create(2,1);
        for (int i = 1 ; i < 10 ; i++) {
            System.out.println(snowFlak.nextId());
        }
    }
}
