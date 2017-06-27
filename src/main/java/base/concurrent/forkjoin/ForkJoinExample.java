package base.concurrent.forkjoin;

import org.apache.commons.lang3.time.StopWatch;

import java.util.concurrent.ForkJoinPool;

/**
 * 测试forkjoin的性能问题
 * 功能描述:计算n的阶乘n!
 * Created by mwjsolar on 17/2/18.
 */
public class ForkJoinExample {

    public static void main(String[] args) {
                long n = 3000000;

        StopWatch forkJoinWatch = new StopWatch();
        forkJoinWatch.start();
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        long forkJoinResult = forkJoinPool.invoke(new FactorialCalculateTask(1L,n));
        System.out.println(forkJoinResult);
        forkJoinWatch.stop();
        System.out.println("time:"+forkJoinWatch.getTime());
//        //normal calculate

        StopWatch normalWatch = new StopWatch();
        normalWatch.start();
        NormalFactorialCalculate normalFactorialCalculate = new NormalFactorialCalculate();
        long normalResult =normalFactorialCalculate.calculate(n);
        System.out.println(normalResult);
        normalWatch.stop();
        System.out.println("time:"+normalWatch.getTime());
    }
}
