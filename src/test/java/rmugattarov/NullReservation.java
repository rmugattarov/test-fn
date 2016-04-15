package rmugattarov;

import com.filenet.api.constants.RefreshMode;
import com.filenet.api.constants.ReservationType;
import com.filenet.api.core.*;
import com.filenet.api.property.Properties;
import com.filenet.api.util.UserContext;
import org.junit.Test;

import javax.security.auth.Subject;

/**
 * Created by rmugattarov on 15.04.2016.
 */
public class NullReservation {
    @Test
    public void test() {
        Connection connection = Factory.Connection.getConnection("http://172.28.24.184:9080/wsi/FNCEWS40MTOM/");
        Subject subject = UserContext.createSubject(connection, "rmugattarov@tn.fntst.ru", "o9p0[-]=", null);
        UserContext.get().pushSubject(subject);
        try {
            Domain domain = Factory.Domain.fetchInstance(connection, null, null);
            ObjectStore objectStore = Factory.ObjectStore.fetchInstance(domain, "OST", null);
            Document document = Factory.Document.fetchInstance(objectStore, "/test/rmugattarov/null-reservation/null-reservation", null);
            System.out.println(document.get_MajorVersionNumber() + "." + document.get_MinorVersionNumber());
            System.out.println(document.get_IsCurrentVersion());

            document.checkout(ReservationType.EXCLUSIVE, null, null, null);
            document.save(RefreshMode.NO_REFRESH);

            document = (Document) document.get_Reservation();
            System.out.println(document.get_MajorVersionNumber() + "." + document.get_MinorVersionNumber());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            UserContext.get().popSubject();
        }
    }
}
