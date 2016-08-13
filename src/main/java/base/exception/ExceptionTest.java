package base.exception;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by mwjsolar on 16/8/4.
 */
public class ExceptionTest {
    static class BusinessException extends RuntimeException {

    }

    static class DepositException extends Exception {

    }

    static class Test {
       public void test() throws DepositException {
           throw new DepositException();
       }
    }

    public static void main(String[] args) {
        Test test = new Test();
        try {
            test.test();
        } catch (BusinessException e1) {
            System.out.println(e1);
        }
        catch (Exception e) {
            System.out.println(e);
        }


        try {
            Class aClass =  Test.class;
            Test test1 = (Test) aClass.newInstance();
            Method method =   aClass.getMethod("test");
            method.invoke(test1,null);
        } catch (BusinessException e1) {
            System.out.println(e1);
        }
        catch (Exception e) {
            if (e instanceof InvocationTargetException)
                try {
                    throw  e.getCause();
                } catch (BusinessException throwable) {
                    throwable.printStackTrace();
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            System.out.println(e);
        }
    }
}
