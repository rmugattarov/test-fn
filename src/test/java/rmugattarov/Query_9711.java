package rmugattarov;

import com.filenet.api.core.*;
import com.filenet.api.util.UserContext;
import org.junit.Test;

import javax.security.auth.Subject;

/**
 * Created by rmugattarov on 29.02.2016.
 */
public class Query_9711 {
    @Test
    public void test() {
        Connection connection = Factory.Connection.getConnection("http://172.28.24.184:9080/wsi/FNCEWS40MTOM/");
        Subject subject = UserContext.createSubject(connection, "rmugattarov@tn.fntst.ru", "o9p0[-]=", null);
        UserContext.get().pushSubject(subject);
        try {
            Domain domain = Factory.Domain.fetchInstance(connection, null, null);
            ObjectStore objectStore = Factory.ObjectStore.fetchInstance(domain, "OST", null);
            Folder folder = Factory.Folder.fetchInstance(objectStore, "/Справочники/Объекты строительства", null);
            System.out.println(folder.get_Id());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            UserContext.get().popSubject();
        }
    }
}
