package rmugattarov;

import com.filenet.api.constants.*;
import com.filenet.api.core.*;
import com.filenet.api.query.SearchScope;
import com.filenet.api.util.UserContext;
import org.junit.Test;

import javax.security.auth.Subject;

/**
 * Created by rmugattarov on 25.05.2016.
 */
public class TestObjectReference {
    @Test
    public void test() {
        Connection connection = Factory.Connection.getConnection("http://172.28.24.182:9080/wsi/FNCEWS40MTOM/");
        Subject subject = UserContext.createSubject(connection, "rmugattarov@tn.fntst.ru", "o9p0[-]=", null);
        UserContext.get().pushSubject(subject);
        try {
            Domain domain = Factory.Domain.fetchInstance(connection, null, null);
            ObjectStore objectStore = Factory.ObjectStore.fetchInstance(domain, "OST", null);
            SearchScope searchScope = new SearchScope(objectStore);
            Folder folder = Factory.Folder.fetchInstance(objectStore, "/test/rmugattarov/object-reference-test", null);
            Document document = Factory.Document.createInstance(objectStore, "KatStroy", null);
            document.checkin(AutoClassify.DO_NOT_AUTO_CLASSIFY, CheckinType.MAJOR_VERSION);
            Document parent = Factory.Document.fetchInstance(objectStore, "{804AE754-0000-CD5F-96CD-048DDFDB000C}", null);
            document.getProperties().putObjectValue("ParentCatalogItem", parent.get_Id());
            document.save(RefreshMode.NO_REFRESH);
            folder.file(document, AutoUniqueName.AUTO_UNIQUE, null, DefineSecurityParentage.DEFINE_SECURITY_PARENTAGE).save(RefreshMode.NO_REFRESH);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            UserContext.get().popSubject();
        }
    }
}
