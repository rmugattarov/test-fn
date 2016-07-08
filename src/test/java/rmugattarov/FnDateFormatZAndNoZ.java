package rmugattarov;

import com.filenet.api.constants.*;
import com.filenet.api.core.*;
import com.filenet.api.query.SearchSQL;
import com.filenet.api.query.SearchScope;
import com.filenet.api.util.UserContext;
import org.junit.Test;

import javax.security.auth.Subject;

/**
 * Created by rmugattarov on 08.07.2016.
 */
public class FnDateFormatZAndNoZ {
    @Test
    public void test() {
        Connection connection = Factory.Connection.getConnection("http://172.28.24.197:9080/wsi/FNCEWS40MTOM/");
        Subject subject = UserContext.createSubject(connection, "rmugattarov@tn.fntst.ru", "o9p0[-]=", null);
        UserContext.get().pushSubject(subject);
        try {
            Domain domain = Factory.Domain.fetchInstance(connection, null, null);
            ObjectStore objectStore = Factory.ObjectStore.fetchInstance(domain, "OST", null);
            SearchScope searchScope = new SearchScope(objectStore);
            String barCode = ((Folder) searchScope.fetchObjects(new SearchSQL("select barcode from folder where accountingdate > 2016-07-06T14:37:00 and accountingdate < 2016-07-06T14:38:00"), 1, null, false).iterator().next()).getProperties().getStringValue("BarCode");
            System.out.println(barCode);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            UserContext.get().popSubject();
        }
    }
}
