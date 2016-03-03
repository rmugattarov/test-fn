package rmugattarov;

import com.filenet.api.util.Id;
import org.junit.Test;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by rmugattarov on 02.03.2016.
 */
public class _8723_search_for_duplicate_employees {
    private String host = "172.28.24.186";
    private String dbms = "db2";
    private String port = "50000";
    private String db = "OSDB182";

    @Test
    public void test() throws SQLException {
        java.util.Properties connProperties = new java.util.Properties();
        connProperties.put("user", "os1user");
        connProperties.put("password", "o9p0[-]=");
        int batchSize = 10;
        try (java.sql.Connection connection = DriverManager.getConnection("jdbc:" + dbms + "://" + host + ":" + port + "/" + db, connProperties); Statement stmt = connection.createStatement()) {
            ResultSet idSet = stmt.executeQuery("SELECT object_id FROM generic FETCH FIRST " + batchSize + " ROWS ONLY");
            int counter = 0;
            while (idSet.next()) {
                System.out.printf("%d) %s\n", ++counter, new Id(idSet.getBytes(1)));
            }
        }
    }
}
