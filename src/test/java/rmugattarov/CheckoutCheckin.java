package rmugattarov;

import com.filenet.api.constants.AutoClassify;
import com.filenet.api.constants.CheckinType;
import com.filenet.api.constants.RefreshMode;
import com.filenet.api.constants.ReservationType;
import com.filenet.api.core.*;
import com.filenet.api.util.Id;
import com.filenet.api.util.UserContext;
import org.junit.Test;

import javax.security.auth.Subject;

/**
 * Created by rmugattarov on 22.03.2016.
 */
public class CheckoutCheckin {
    @Test
    public void test() {
        Connection connection = Factory.Connection.getConnection("http://172.28.24.182:9080/wsi/FNCEWS40MTOM/");
        Subject subject = UserContext.createSubject(connection, "rmugattarov@tn.fntst.ru", "o9p0[-]=", null);
        UserContext.get().pushSubject(subject);
        try {
            Domain domain = Factory.Domain.fetchInstance(connection, null, null);
            ObjectStore objectStore = Factory.ObjectStore.fetchInstance(domain, "OST", null);
            Document document = Factory.Document.fetchInstance(objectStore, new Id("{C0FD9C53-0000-C7FA-A521-975F88988842}"), null);
            System.out.println(document.get_MajorVersionNumber());

            if (!document.get_IsCurrentVersion()) {
                System.out.println("Not current version");
                document = (Document) document.get_CurrentVersion();
            } else {
                System.out.println("Current version");
            }

            document.checkout(ReservationType.EXCLUSIVE, null, null, null);
            document.save(RefreshMode.REFRESH);

            document = (Document) document.get_Reservation();
            document.checkin(AutoClassify.DO_NOT_AUTO_CLASSIFY, CheckinType.MAJOR_VERSION);
            document.getProperties().putValue("DocumentTitleEXD", "zzz");
            document.save(RefreshMode.REFRESH);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            UserContext.get().popSubject();
        }
    }
}
