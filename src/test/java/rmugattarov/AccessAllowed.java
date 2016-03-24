package rmugattarov;

import com.filenet.api.constants.AccessRight;
import com.filenet.api.core.*;
import com.filenet.api.util.UserContext;
import org.junit.Test;

import javax.security.auth.Subject;

/**
 * Created by rmugattarov on 24.03.2016.
 */
public class AccessAllowed {
    @Test
    public void test() {
        Connection connection = Factory.Connection.getConnection("http://172.28.24.182:9080/wsi/FNCEWS40MTOM/");
        Subject subject = UserContext.createSubject(connection, "rmugattarov@tn.fntst.ru", "o9p0[-]=", null);
        UserContext.get().pushSubject(subject);
        try {
            Domain domain = Factory.Domain.fetchInstance(connection, null, null);
            ObjectStore objectStore = Factory.ObjectStore.fetchInstance(domain, "OST", null);
            Document document = Factory.Document.fetchInstance(objectStore, "{8057A753-0000-CB1E-AD79-85656F7D61A8}", null);
            System.out.println(document.getAccessAllowed());
            System.out.println(document.getAccessAllowed() & AccessRight.WRITE_AS_INT);
            System.out.println(Integer.toBinaryString(document.getAccessAllowed()));
            System.out.println(Integer.toBinaryString(2));
            System.out.println(Integer.parseInt("00000000000000000010", 2));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            UserContext.get().popSubject();
        }
    }
}
