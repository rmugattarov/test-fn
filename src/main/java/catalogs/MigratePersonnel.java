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
import java.util.Iterator;

/**
 * Created by rmugattarov on 20.07.2016.
 */
public class MigratePersonnel {
    public static void main(String[] args) {
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
            int counter = 0;
            while (iterator.hasNext()) {
                RepositoryRow row = iterator.next();
                Properties properties = row.getProperties();
                String persNumber = properties.getStringValue("PersNumber");
                String persFio = properties.getStringValue("PersFio");
                Boolean isCatalogElementActive = properties.getBooleanValue("IsCatalogElementActive");
                System.out.printf("%d) %s %s %b\r\n", ++counter, persNumber, persFio, isCatalogElementActive);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            UserContext.get().popSubject();
        }
    }
}
