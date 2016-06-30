package rmugattarov;

import com.filenet.api.constants.RefreshMode;
import com.filenet.api.core.*;
import com.filenet.api.util.UserContext;
import org.junit.Test;

import javax.security.auth.Subject;

/**
 * Created by rmugattarov on 09.06.2016.
 */
public class TestLock {
    @Test
    public void test() {
        Connection connection = Factory.Connection.getConnection("http://172.28.24.182:9080/wsi/FNCEWS40MTOM/");
        Subject subject = UserContext.createSubject(connection, "cpe_service_tst@tn.fntst.ru", "o9p0[-]=", null);
        UserContext.get().pushSubject(subject);
        try {
            Domain domain = Factory.Domain.fetchInstance(connection, null, null);
            ObjectStore objectStore = Factory.ObjectStore.fetchInstance(domain, "OST", null);
            Document document = Factory.Document.fetchInstance(objectStore, "/test/rmugattarov/test-lock/test-lock", null);
            System.out.println("isLocked : " + document.isLocked());

            document.lock(300, "qwerty");
            document.save(RefreshMode.REFRESH);
            System.out.println("isLocked : " + document.isLocked());
//            document.unlock();
//            document.save(RefreshMode.REFRESH);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            UserContext.get().popSubject();
        }
    }
}
