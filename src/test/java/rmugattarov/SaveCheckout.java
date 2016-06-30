package rmugattarov;

import com.filenet.api.constants.AutoClassify;
import com.filenet.api.constants.CheckinType;
import com.filenet.api.constants.RefreshMode;
import com.filenet.api.constants.ReservationType;
import com.filenet.api.core.*;
import com.filenet.api.util.UserContext;
import org.junit.Test;

import javax.security.auth.Subject;

/**
 * Created by rmugattarov on 29.06.2016.
 */
public class SaveCheckout {
    @Test
    public void test() {
        Connection connection = Factory.Connection.getConnection("http://172.28.24.182:9080/wsi/FNCEWS40MTOM/");
        Subject subject = UserContext.createSubject(connection, "cpe_service_tst@tn.fntst.ru", "o9p0[-]=", null);
        UserContext.get().pushSubject(subject);
        try {
            Domain domain = Factory.Domain.fetchInstance(connection, null, null);
            ObjectStore objectStore = Factory.ObjectStore.fetchInstance(domain, "OST", null);

//            Document document = Factory.Document.createInstance(objectStore, "Document", null, null, null);
//            document.checkin(AutoClassify.DO_NOT_AUTO_CLASSIFY, CheckinType.MAJOR_VERSION);
//            document.save(RefreshMode.NO_REFRESH);
//            Folder folder = Factory.Folder.fetchInstance(objectStore, "/test/rmugattarov", null);
//            folder.file(document, AutoUniqueName.AUTO_UNIQUE, "test-checkout", DefineSecurityParentage.DO_NOT_DEFINE_SECURITY_PARENTAGE).save(RefreshMode.NO_REFRESH);

            Document document = Factory.Document.fetchInstance(objectStore, "/test/rmugattarov/test-checkout", null);
            document.checkout(ReservationType.EXCLUSIVE, null, null, null);
            Document reservation = (Document) document.get_Reservation();
            reservation.checkin(AutoClassify.DO_NOT_AUTO_CLASSIFY, CheckinType.MAJOR_VERSION);
            reservation.save(RefreshMode.NO_REFRESH);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            UserContext.get().popSubject();
        }
    }
}
