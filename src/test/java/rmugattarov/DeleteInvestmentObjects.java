package rmugattarov;

import com.filenet.api.collection.IndependentObjectSet;
import com.filenet.api.constants.RefreshMode;
import com.filenet.api.core.*;
import com.filenet.api.property.PropertyFilter;
import com.filenet.api.query.SearchSQL;
import com.filenet.api.query.SearchScope;
import com.filenet.api.util.UserContext;
import org.junit.Test;

import javax.security.auth.Subject;
import java.util.Iterator;

/**
 * Created by rmugattarov on 25.05.2016.
 */
public class DeleteInvestmentObjects {
    @Test
    public void test() {
        Connection connection = Factory.Connection.getConnection("http://172.28.24.182:9080/wsi/FNCEWS40MTOM/");
        Subject subject = UserContext.createSubject(connection, "rmugattarov@tn.fntst.ru", "o9p0[-]=", null);
        UserContext.get().pushSubject(subject);
        try {
            Domain domain = Factory.Domain.fetchInstance(connection, null, null);
            ObjectStore objectStore = Factory.ObjectStore.fetchInstance(domain, "OST", null);
            SearchScope searchScope = new SearchScope(objectStore);
            Iterator<Document> iterator = searchScope.fetchObjects(new SearchSQL("SELECT Id FROM KatStroy WHERE IsCurrentVersion = TRUE AND This INFOLDER '/Справочники/Объекты строительства'"), null, new PropertyFilter(), true).iterator();
            int counter = 0;
            while (iterator.hasNext()) {
                Document document = iterator.next();
                document.delete();
                document.save(RefreshMode.NO_REFRESH);
                counter++;
            }
            System.out.println("Deleted : " + counter);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            UserContext.get().popSubject();
        }
    }
}
