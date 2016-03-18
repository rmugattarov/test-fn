package rmugattarov;

import com.filenet.api.constants.AutoUniqueName;
import com.filenet.api.constants.DefineSecurityParentage;
import com.filenet.api.constants.RefreshMode;
import com.filenet.api.core.*;
import com.filenet.api.property.Properties;
import com.filenet.api.util.UserContext;
import org.junit.Test;

import javax.security.auth.Subject;

/**
 * Created by rmugattarov on 17.03.2016.
 */
public class _10151_create_reference_file {
    @Test
    public void test() {
        Connection connection = Factory.Connection.getConnection("http://172.28.24.182:9080/wsi/FNCEWS40MTOM/");
        Subject subject = UserContext.createSubject(connection, "rmugattarov@tn.fntst.ru", "o9p0[-]=", null);
        UserContext.get().pushSubject(subject);
        try {
            Domain domain = Factory.Domain.fetchInstance(connection, null, null);
            ObjectStore objectStore = Factory.ObjectStore.fetchInstance(domain, "OST", null);
            Folder pack = Factory.Folder.fetchInstance(objectStore, "/test/rmugattarov/TNFDEV-10151", null);
//            Folder rootRefFile = Factory.Folder.fetchInstance(objectStore, "/Оперативный архив/Оперативное хранение/Номенклатура дел", null);

//            Folder referenceFile = Factory.Folder.createInstance(objectStore, "ReferenceFile");
//            Properties referenceFileProperties = referenceFile.getProperties();
//            referenceFileProperties.putValue("DocFlowYear", 2016);
//            referenceFileProperties.putValue("SavingType", 0);
//            referenceFileProperties.putValue("SavingTime", 1);
//            referenceFileProperties.putValue("ArticleNum", "10151");
//            referenceFileProperties.putValue("DocFlowIndex", "10151");
//            referenceFileProperties.putValue("OArchiveFileName", "10151");
//            referenceFileProperties.putValue("FolderName", "10151reffile");
//            referenceFileProperties.putValue("Parent", rootRefFile);
//            referenceFile.save(RefreshMode.REFRESH);
//            rootRefFile.file(referenceFile, AutoUniqueName.AUTO_UNIQUE, null, DefineSecurityParentage.DO_NOT_DEFINE_SECURITY_PARENTAGE).save(RefreshMode.REFRESH);

            Folder referenceFile = Factory.Folder.fetchInstance(objectStore, "/Оперативный архив/Оперативное хранение/Номенклатура дел/10151reffile", null);
            pack.getProperties().putValue("ReferenceFileId", referenceFile.get_Id().toString());
            pack.save(RefreshMode.REFRESH);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            UserContext.get().popSubject();
        }
    }
}
