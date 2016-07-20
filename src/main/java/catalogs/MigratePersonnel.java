package catalogs;

import com.filenet.api.core.Connection;
import com.filenet.api.core.Domain;
import com.filenet.api.core.Factory;
import com.filenet.api.core.ObjectStore;
import com.filenet.api.property.Properties;
import com.filenet.api.query.RepositoryRow;
import com.filenet.api.query.SearchSQL;
import com.filenet.api.query.SearchScope;
import com.filenet.api.util.UserContext;

import javax.security.auth.Subject;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Iterator;

/**
 * Created by rmugattarov on 20.07.2016.
 */
public class MigratePersonnel {
    private static final String host = "172.28.24.186";
    private static final String dbms = "db2";
    private static final String port = "50000";
    private static final String db = "REFDB182";

    public static void main(String[] args) throws SQLException {
        Connection connection = Factory.Connection.getConnection("http://172.28.24.184:9080/wsi/FNCEWS40MTOM/");
        Subject subject = UserContext.createSubject(connection, "rmugattarov@tn.fntst.ru", "o9p0[-]=", null);
        UserContext.get().pushSubject(subject);
        try {
            Domain domain = Factory.Domain.fetchInstance(connection, null, null);
            ObjectStore objectStore = Factory.ObjectStore.fetchInstance(domain, "OST", null);
            SearchScope searchScope = new SearchScope(objectStore);
            SearchSQL searchSQL = new SearchSQL();
            searchSQL.setFromClauseInitialValue("Personnel", null, true);
            searchSQL.setSelectList("PersNumber,PersFio,IsCatalogElementActive");
            searchSQL.setWhereClause("PersNumber IS NOT NULL AND PersFio IS NOT NULL");
            Iterator<RepositoryRow> iterator = searchScope.fetchRows(searchSQL, null, null, true).iterator();

            java.util.Properties connProperties = new java.util.Properties();
            connProperties.put("user", "refuser");
            connProperties.put("password", "o9p0[-]=");
            try (java.sql.Connection refDbConn = DriverManager.getConnection("jdbc:" + dbms + "://" + host + ":" + port + "/" + db, connProperties); PreparedStatement stmt = refDbConn.prepareStatement(
                    "INSERT INTO PERSONNEL(NUMBER,FIO,ACTIVE) VALUES(?,?,?)"
            )) {
                while (iterator.hasNext()) {
                    try {
                        RepositoryRow row = iterator.next();
                        Properties properties = row.getProperties();
                        String persNumber = properties.getStringValue("PersNumber");
                        String persFio = properties.getStringValue("PersFio");
                        Boolean isCatalogElementActive = properties.getBooleanValue("IsCatalogElementActive");

                        stmt.setString(1, persNumber);
                        stmt.setString(2, persFio);
                        stmt.setBoolean(3, isCatalogElementActive);

                        stmt.executeUpdate();
                    } catch (SQLException sqlE) {

                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            UserContext.get().popSubject();
        }
    }
}
