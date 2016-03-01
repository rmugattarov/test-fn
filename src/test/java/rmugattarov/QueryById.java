package rmugattarov;

import com.filenet.api.util.Id;
import org.junit.Test;

import java.sql.*;
import java.util.Properties;

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
        try (Connection connection = DriverManager.getConnection("jdbc:" + dbms + "://" + host + ":" + port + "/" + db, connProperties); Statement stmt = connection.createStatement()) {
            ResultSet resultSet = stmt.executeQuery("SELECT object_id FROM docversion");
            int counter = 0;
            while (resultSet.next()) {
                System.out.printf("%d) %s\n", ++counter, new Id(resultSet.getBytes(1)));
            }
        }
    }
}
