package base.concurrent.forkjoin;

import org.springframework.util.Assert;

import java.util.concurrent.RecursiveTask;

/**
 * 阶乘计算任务
 * Created by mwjsolar on 17/2/18.
 */
public class FactorialCalculateTask extends RecursiveTask<Long> {
    //开始计算值
    private Long start;
    //结束计算值
    private Long end;
    private int THRESHOLD = 1000000;
    public FactorialCalculateTask(Long start, Long end) {
        this.start = start;
        this.end = end;
        Assert.isTrue(start <= end ,"start must be less than end [start="+start+",end="+end+"]");
    }

    @Override
    protected Long compute() {
        if ((end  - start) < THRESHOLD) {
            long redirectResult = 1;
            for (long i = start ; i <= end ; i++ ) {
                redirectResult = redirectResult +i;
            }
            return redirectResult;
        }

        Long middle = (end +start ) / 2;

        FactorialCalculateTask leftCalculateTask = new FactorialCalculateTask(start,middle);
        leftCalculateTask.fork();

        FactorialCalculateTask rightCalculateTask = new FactorialCalculateTask(middle+1,end);
        rightCalculateTask.fork();

        long leftResult = leftCalculateTask.join();
        long rightResult = rightCalculateTask.join();


        return leftResult + rightResult;
    }
}
