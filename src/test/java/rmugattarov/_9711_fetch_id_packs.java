package rmugattarov;

import org.junit.Test;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by rmugattarov on 01.03.2016.
 */
public class _9711_fetch_id_packs {
    private String host = "172.28.24.186";
    private String dbms = "db2";
    private String port = "50000";
    private String db = "OSDB182";

    @Test
    public void test() throws SQLException {
        java.util.Properties connProperties = new java.util.Properties();
        connProperties.put("user", "os1user");
        connProperties.put("password", "o9p0[-]=");
        Set<String> idPacks = new HashSet<>();
        try (java.sql.Connection connection = DriverManager.getConnection("jdbc:" + dbms + "://" + host + ":" + port + "/" + db, connProperties); Statement stmt = connection.createStatement()) {
            ResultSet resultSet = stmt.executeQuery(
                    "SELECT " +
                            "MAX(a.create_date) " +
                            "FROM annotation a " +
                            "INNER JOIN container c ON c.object_id=a.annotated_id " +
                            "GROUP BY c.u5218_idpack"
            );
            int counter = 0;
            while (resultSet.next()) {
                System.out.printf("%d) %s\n", ++counter, resultSet.getString(1));
            }
        }
    }
}
