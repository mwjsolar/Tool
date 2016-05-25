package datasource;

import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.parser.SQLParserUtils;
import com.alibaba.druid.sql.parser.SQLStatementParser;
import com.alibaba.druid.util.JdbcUtils;

import java.util.List;

/**
 * Created by hzmawenjun on 2016/5/22.
 */
public class SqlParseTest {
    private static String TEST_SQL_1 = "SELECT  u.phone FROM TB_LOAN_APPLY la JOIN TB_USER u ON la.user_id = u.id\n" +
            "         WHERE   NOT EXISTS(SELECT * FROM TB_LOAN l WHERE l.loan_apply_id = la.id) AND u.phone = '15928815136'";


    public static void main(String[] args) {
        SQLStatementParser sqlStatementParser = SQLParserUtils.createSQLStatementParser(TEST_SQL_1, JdbcUtils.MYSQL);
        List<SQLStatement> stmtList = sqlStatementParser.parseStatementList();
        System.out.println(stmtList);
    }
}
