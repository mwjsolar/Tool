package base;

import org.apache.http.HttpEntity;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.StreamSupport;

/**
 * Created by hzmawenjun on 2016/5/15.
 */
public class TestTimer {
    //
    public static void main(String[] args) throws InterruptedException {
//        Timer timer = new Timer();
//        timer.schedule(new TimerTask1(),7000);
//
//        timer.schedule(new TimeTask2(),5000);
//
//        timer.schedule(new TimeTask2(),80000);
//
//       Thread thread =  new Thread() {
//            @Override
//            public void run() {
//                while (true) {
//                    try {
//                        Thread.sleep(1000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                    System.out.println("test damea");
//                }
//            }
//        };
//        thread.setDaemon(true);
        //thread.start();

//        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
//        executorService.scheduleAtFixedRate(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                System.out.println("test damea");
//            }
//        },0,2, TimeUnit.SECONDS);

        System.out.println();

        try {
                Arrays.asList(1,2,3,4).parallelStream().map((value) -> {
                if (value == 3)
                    throw new RuntimeException("value exception");
                System.out.println(value);
                return value;
            }).collect(Collectors.toList());
        } catch (Exception e) {
            System.out.println(e);
        }

        System.out.println("Executor Service start");

        ExecutorService executorService = Executors.newFixedThreadPool(2);
        for (int i=1 ; i < 6 ; i++) {
            int finalI = i;
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    if (finalI == 3)
                        throw new RuntimeException("value exception");

                    System.out.println(finalI);
                }
            });
        }

        executorService.awaitTermination(1000L,TimeUnit.MILLISECONDS);
        System.out.println("fork join start");
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        for (int i=1 ; i < 6 ; i++) {
            int finalI = i;
            RecursiveTask forkJoinTask = new RecursiveTask() {
                @Override
                protected Object compute() {
                    if (finalI == 3)
                        throw new RuntimeException("value exception");

                    System.out.println(finalI);
                    return finalI;
                }
            };
            forkJoinPool.execute(forkJoinTask);
            if(forkJoinTask.isCompletedAbnormally()) {
                System.out.println(forkJoinTask.getException());
            }
        }
        //TestThread testThread = new TestThread();
    }

    static private class TestThread {
        static {
            ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
            executorService.scheduleAtFixedRate(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("test damea");
                }
            }, 0, 2, TimeUnit.SECONDS);
        }


    }

    @Test
    public void test() throws InterruptedException {
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
        executorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("test damea");
            }
        }, 0, 2, TimeUnit.SECONDS);
//        Thread.sleep(10000000);
    }


//
//    static class TimerTask1 extends TimerTask {
//        @Override
//        public void run() {
//            System.out.println("this is task1");
//        }
//    }
//
//    static class TimeTask2 extends TimerTask {
//        @Override
//        public void run() {
//            System.out.println("this is task2");
//        }
//    }


//    public static void main(String[] args) {
//        getSaveMoneyDataFromNos();
//    }

    private static String getSaveMoneyDataFromNos() {
        int backDay = 0;
        String hotSearchContent = "";
        Calendar currentCalendar = Calendar.getInstance();
        int currentHours = currentCalendar.get(Calendar.HOUR_OF_DAY);
        int index = -1;

        //从最近到最远的时间
        String nosUrl = String.format("http://haitao.nos.netease.com/self_goods_cut_degree_7d_app_2016101010.csv");
        // 创建默认的httpClient实例.
        CloseableHttpClient httpclient = HttpClients.createDefault();
        // 创建httpget
        HttpGet httpGet = new HttpGet(nosUrl);
//	            httpGet.setHeader("Accept-Encoding", "deflate");
        CloseableHttpResponse response = null;
        try {
            response = httpclient.execute(httpGet);

            //如果nos的响应码是200表示正常
            if (response.getStatusLine().getStatusCode() == 200) {
                HttpEntity entity = response.getEntity();

//	                    System.out.println(entity.isChunked());
//	                    System.out.println(entity.getContentLength());
//
                System.out.println(EntityUtils.toString(entity, "utf-8"));
            }
        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }

        return hotSearchContent;
    }

}
