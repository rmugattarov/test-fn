package rmugattarov;

import com.filenet.api.util.Id;
import org.junit.Test;

import java.sql.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

/**
 * Created by rmugattarov on 01.03.2016.
 */
public class QueryById {
    private String host = "172.28.24.186";
    private String dbms = "db2";
    private String port = "50000";
    private String db = "OSDB182";

    @Test
    public void test() throws SQLException {
        Properties connProperties = new Properties();
        connProperties.put("user", "os1user");
        connProperties.put("password", "o9p0[-]=");
        Set<Id> ids = new HashSet<>();
        try (Connection connection = DriverManager.getConnection("jdbc:" + dbms + "://" + host + ":" + port + "/" + db, connProperties); Statement stmt = connection.createStatement()) {
            ResultSet resultSet = stmt.executeQuery("SELECT object_id FROM docversion FETCH FIRST 1000 ROWS ONLY");
            java.util.Date date0 = new Date();
            while (resultSet.next()) {
                ids.add(new Id(resultSet.getBytes(1)));
            }
            java.util.Date date1 = new Date();
            System.out.printf("Fetched 1000 ids in %f s", (date1.getTime() - date0.getTime()) / 1000D);
        }
    }
}
