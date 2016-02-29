package rmugattarov;

import com.filenet.api.collection.RepositoryRowSet;
import com.filenet.api.core.Connection;
import com.filenet.api.core.Domain;
import com.filenet.api.core.Factory;
import com.filenet.api.core.ObjectStore;
import com.filenet.api.property.Properties;
import com.filenet.api.query.RepositoryRow;
import com.filenet.api.query.SearchSQL;
import com.filenet.api.query.SearchScope;
import com.filenet.api.util.UserContext;
import org.junit.Test;

import javax.security.auth.Subject;
import java.util.Date;
import java.util.Iterator;

/**
 * Created by rmugattarov on 29.02.2016.
 */
public class CuratorDepartmentQuery {
    @Test
    public void test() {
        Connection connection = Factory.Connection.getConnection("http://172.28.24.184:9080/wsi/FNCEWS40MTOM/");
        Subject subject = UserContext.createSubject(connection, "rmugattarov@tn.fntst.ru", "o9p0[-]=", null);
        UserContext.get().pushSubject(subject);
        try {
            Domain domain = Factory.Domain.fetchInstance(connection, null, null);
            ObjectStore objectStore = Factory.ObjectStore.fetchInstance(domain, "OST", null);
            SearchScope searchScope = new SearchScope(objectStore);
            Date startTime = new Date();
            System.out.printf("\nStart time : %s\n\n", startTime);
            SearchSQL searchSQL = new SearchSQL(
                    "SELECT DISTINCT Department FROM Employee WHERE Department IS NOT NULL AND Department <> ''"
            );
            RepositoryRowSet repositoryRowSet = searchScope.fetchRows(searchSQL, null, null, true);
            Iterator<RepositoryRow> iterator = repositoryRowSet.iterator();
            int counter = 0;
            while (iterator.hasNext()) {
                RepositoryRow row = iterator.next();
                Properties properties = row.getProperties();
                System.out.printf("%d) Department : %s\n", ++counter, properties.getStringValue("Department"));
            }
            Date finishTime = new Date();
            System.out.printf("\nFinish time : %s\n", finishTime);
            System.out.printf("Total time : %d s\n", (finishTime.getTime() - startTime.getTime()) / 1000);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            UserContext.get().popSubject();
        }
    }
}
