package base.concurrent.forkjoin;

/**
 * Created by mwjsolar on 17/2/19.
 */
public class NormalFactorialCalculate {
    public long calculate(long n) {
        long result = 1;
        for (long i = 1; i<= n;i++) {
            result = result + i ;
        }
        return result;
    }
}
