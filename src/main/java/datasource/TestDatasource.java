package datasource;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.time.StopWatch;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by hzmawenjun on 2016/5/19.
 */
public class TestDatasource {

    private static int EXECUTE_NUM = 10000*100;

    private static String Pre_TEST_SQL = "SELECT  u.phone FROM TB_LOAN_APPLY la JOIN TB_USER u ON la.user_id = u.id\n" +
            "         WHERE   NOT EXISTS(SELECT * FROM TB_LOAN l WHERE l.loan_apply_id = la.id) AND u.phone = ?";

    private static String TEST_SQL_1 = "SELECT  u.phone FROM TB_LOAN_APPLY la JOIN TB_USER u ON la.user_id = u.id\n" +
            "         WHERE   NOT EXISTS(SELECT * FROM TB_LOAN l WHERE l.loan_apply_id = la.id) AND u.phone = '15928815136'";


    private static List<String> phoneList = new ArrayList<String>();
    static {
        for (int i = 0 ; i < 100;i ++) {
            phoneList.add(RandomStringUtils.randomAlphanumeric(11));
        }
    }

    private static final ExecutorService executorService = Executors.newFixedThreadPool(70);

    public static void main(String[] args) {
        final DruidDataSource dataSource = DataSourceInstance.getDruidDataSource();

        //final ComboPooledDataSource dataSource = DataSourceInstance.getC3p0DataSource();

        //test druid
        final CountDownLatch downLatch = new CountDownLatch(EXECUTE_NUM);
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        for (int i = 0 ; i <EXECUTE_NUM ; i ++) {
            final int finalI = i;
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    queryDbByStatement(dataSource);
                    downLatch.countDown();
                    System.out.println("querying ..."+ finalI);
                }
            });
        }

        try {
            downLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        stopWatch.stop();

        System.out.println("test time:"+stopWatch.getTime());
    }

    public static void queryDbByPrepareStatement(DataSource dataSource) {
        try {
            Connection connection = dataSource.getConnection();
            PreparedStatement statement1 = connection.prepareStatement(Pre_TEST_SQL);
            statement1.setString(1, phoneList.get(Math.abs(ThreadLocalRandom.current().nextInt() % 100)));
            ResultSet resultSet = statement1.executeQuery();
            System.out.println(toString(resultSet));

            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void queryDbByStatement(DataSource dataSource) {
        try {
            Connection connection = dataSource.getConnection();
            Statement statement = connection.createStatement();
            System.out.println(phoneList.get(Math.abs(ThreadLocalRandom.current().nextInt() % 100)));
            ResultSet resultSet = statement.executeQuery(TEST_SQL_1);
            System.out.println(toString(resultSet));

            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static String toString(ResultSet resultSet) {
        if (resultSet == null)
            return "";

        StringBuilder stringBuilder = new StringBuilder();
        try {
            while (resultSet.next()) {
                stringBuilder.append("phone=")
                        .append(resultSet.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }
}
