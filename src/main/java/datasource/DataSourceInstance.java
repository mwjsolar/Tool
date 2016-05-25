package datasource;

import com.alibaba.druid.pool.DruidDataSource;
import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * Created by hzmawenjun on 2016/5/22.
 */
public class DataSourceInstance {

    private static String userName = "loan";
    private static String password = "IN3C*brOz";
    private static String jdbcUrl = "jdbc:mysql://127.0.0.1:10034/loan-app-dev";
    private static String driverClass = "com.mysql.jdbc.Driver";
    private static int maxSize = 20;
    private static int initSize = 5;
    private static int minSize = 5;

    public static DruidDataSource getDruidDataSource() {
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setUsername(userName);
        druidDataSource.setPassword(password);
        druidDataSource.setUrl(jdbcUrl);
        druidDataSource.setDriverClassName(driverClass);
        druidDataSource.setMaxActive(maxSize);
        druidDataSource.setInitialSize(initSize);
        druidDataSource.setMinIdle(minSize);
        return druidDataSource;
    }

    public static ComboPooledDataSource getC3p0DataSource() {
        ComboPooledDataSource  dataSource = new ComboPooledDataSource ();
        try {
            dataSource.setUser(userName);
            dataSource.setPassword(password);
            dataSource.setJdbcUrl(jdbcUrl);
            dataSource.setDriverClass(driverClass);
            dataSource.setMinPoolSize(minSize);
            dataSource.setInitialPoolSize(initSize);
            dataSource.setMaxPoolSize(maxSize);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dataSource;
    }
}
