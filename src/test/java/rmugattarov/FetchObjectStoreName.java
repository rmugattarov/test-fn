package rmugattarov;

import com.filenet.api.constants.PropertyNames;
import com.filenet.api.core.*;
import com.filenet.api.property.PropertyFilter;
import com.filenet.api.util.UserContext;
import org.junit.Test;

import javax.security.auth.Subject;

/**
 * Created by rmugattarov on 18.03.2016.
 */
public class FetchObjectStoreName {
    @Test
    public void test() {
        Connection connection = Factory.Connection.getConnection("http://172.28.24.182:9080/wsi/FNCEWS40MTOM/");
        Subject subject = UserContext.createSubject(connection, "rmugattarov@tn.fntst.ru", "o9p0[-]=", null);
        UserContext.get().pushSubject(subject);
        try {
            Domain domain = Factory.Domain.fetchInstance(connection, null, null);
            ObjectStore objectStore = Factory.ObjectStore.fetchInstance(domain, "OST", null);
            PropertyFilter filter = new PropertyFilter();
            filter.addIncludeProperty(0, null, null, "Name", 1);
            Folder pack = Factory.Folder.fetchInstance(objectStore, "/test/rmugattarov/TNFDEV-10151", filter);
            pack.fetchProperties(new String[] {PropertyNames.ID});
            ObjectStore objectStoreFromPack = pack.getObjectStore();
            objectStoreFromPack.fetchProperties(new String[]{PropertyNames.SYMBOLIC_NAME});
            String objectStoreName = objectStoreFromPack.get_SymbolicName();
            System.out.println(objectStoreName);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            UserContext.get().popSubject();
        }
    }
}
