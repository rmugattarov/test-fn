package rmugattarov;

import com.filenet.api.core.*;
import com.filenet.api.util.UserContext;
import org.junit.Test;

import javax.security.auth.Subject;
import java.util.Iterator;

/**
 * Created by rmugattarov on 10.05.2016.
 */
public class FolderSubFolders {
    @Test
    public void test() {
        Connection connection = Factory.Connection.getConnection("http://172.28.24.182:9080/wsi/FNCEWS40MTOM/");
        Subject subject = UserContext.createSubject(connection, "rmugattarov@tn.fntst.ru", "o9p0[-]=", null);
        UserContext.get().pushSubject(subject);
        try {
            Domain domain = Factory.Domain.fetchInstance(connection, null, null);
            ObjectStore objectStore = Factory.ObjectStore.fetchInstance(domain, "OST", null);
            Folder folder = Factory.Folder.fetchInstance(objectStore, "/АУП", null);
            Iterator subFolderIterator = folder.get_SubFolders().iterator();
            while (subFolderIterator.hasNext()) {
                System.out.println(subFolderIterator.next());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            UserContext.get().popSubject();
        }
    }
}
