package rmugattarov;

import com.filenet.api.util.Id;
import org.junit.Test;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
        List<Id> ids = new ArrayList<>();
        int batchSize = 10000;
        try (Connection connection = DriverManager.getConnection("jdbc:" + dbms + "://" + host + ":" + port + "/" + db, connProperties); Statement stmt = connection.createStatement()) {
            ResultSet idSet = stmt.executeQuery("SELECT object_id FROM docversion FETCH FIRST " + batchSize + " ROWS ONLY");
            java.util.Date date0 = new Date();
            while (idSet.next()) {
                ids.add(new Id(idSet.getBytes(1)));
            }
            java.util.Date date1 = new Date();
            System.out.printf("Fetched %d ids in %f s\n", batchSize, (date1.getTime() - date0.getTime()) / 1000D);

            StringBuilder docsByIdQuery = new StringBuilder("SELECT object_id FROM docversion WHERE object_id=? ");
            for (int i = 1; i < ids.size(); i++) {
                docsByIdQuery.append("OR object_id=? ");
            }
            PreparedStatement docsByIdsStmt = connection.prepareStatement(docsByIdQuery.toString());
            for (int i = 0; i < ids.size(); i++) {
                docsByIdsStmt.setObject(i + 1, ids.get(i).getBytes());
            }
            ResultSet docsById = docsByIdsStmt.executeQuery();
            int counter = 0;
            while (docsById.next()) {
                System.out.printf("%d) Doc id %s\n", ++counter, new Id(docsById.getBytes(1)));
            }
            Date date2 = new Date();
            System.out.printf("Fetched all docs by id in %f\n", (date2.getTime() - date1.getTime()) / 1000D);
        }
    }
}
