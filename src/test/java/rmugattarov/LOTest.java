package rmugattarov;

import com.filenet.api.constants.*;
import com.filenet.api.core.*;
import com.filenet.api.util.UserContext;
import org.junit.Test;

import javax.security.auth.Subject;

/**
 * Created by rmugattarov on 30.06.2016.
 */
public class LOTest {
    @Test
    public void test() {
        Connection connection = Factory.Connection.getConnection("http://172.28.24.182:9080/wsi/FNCEWS40MTOM/");
        Subject subject = UserContext.createSubject(connection, "curator_aup1@tn.fntst.ru", "o9p0[-]=", null);
        UserContext.get().pushSubject(subject);
        try {
            Domain domain = Factory.Domain.fetchInstance(connection, null, null);
            ObjectStore objectStore = Factory.ObjectStore.fetchInstance(domain, "OST", null);
            Folder folder = Factory.Folder.createInstance(objectStore, "Folder");
            folder.set_FolderName("lo-test");
            folder.set_Parent(Factory.Folder.fetchInstance(objectStore, "/test/rmugattarov", null));
            folder.save(RefreshMode.NO_REFRESH);
            Document document = Factory.Document.createInstance(objectStore, "BaseDocuments", null);
//            Document document = Factory.Document.fetchInstance(objectStore, "/test/rmugattarov/lo-test/lo-test", null);
            document.checkin(AutoClassify.DO_NOT_AUTO_CLASSIFY, CheckinType.MAJOR_VERSION);
            document.getProperties().putValue("ScanGUID", String.valueOf(Math.random()));
            document.getProperties().putValue("CustomerCode", String.valueOf(Math.random()));
            document.getProperties().putValue("CustomerKPP", String.valueOf(Math.random()));
            document.getProperties().putValue("CustomerName", String.valueOf(Math.random()));
            document.getProperties().putValue("CustomerINN", String.valueOf(Math.random()));
            document.getProperties().putValue("CustomerGroup", String.valueOf(Math.random()));
            document.save(RefreshMode.NO_REFRESH);
            folder.file(document, AutoUniqueName.AUTO_UNIQUE, "lo-test", DefineSecurityParentage.DO_NOT_DEFINE_SECURITY_PARENTAGE).save(RefreshMode.NO_REFRESH);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            UserContext.get().popSubject();
        }
    }
}
