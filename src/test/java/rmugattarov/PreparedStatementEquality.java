package rmugattarov;

import org.junit.Test;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by rmugattarov on 04.03.2016.
 */
public class PreparedStatementEquality {
    private String host = "172.28.24.186";
    private String dbms = "db2";
    private String port = "50000";
    private String db = "OSDB182";

    @Test
    public void test() throws SQLException {
        java.util.Properties connProperties = new java.util.Properties();
        connProperties.put("user", "os1user");
        connProperties.put("password", "o9p0[-]=");
        try (java.sql.Connection connection = DriverManager.getConnection("jdbc:" + dbms + "://" + host + ":" + port + "/" + db, connProperties);
             PreparedStatement stmt0 = connection.prepareStatement("SELECT d.object_id FROM docversion d FETCH FIRST 10 ROWS ONLY");
             PreparedStatement stmt1 = connection.prepareStatement("SELECT d.object_id FROM docversion d FETCH FIRST 10 ROWS ONLY")
        ) {
            System.out.println(stmt0.equals(stmt1));
        }
    }
}
