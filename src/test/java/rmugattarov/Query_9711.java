package rmugattarov;

import com.filenet.api.collection.RepositoryRowSet;
import com.filenet.api.core.Connection;
import com.filenet.api.core.Domain;
import com.filenet.api.core.Factory;
import com.filenet.api.core.ObjectStore;
import com.filenet.api.property.Properties;
import com.filenet.api.query.RepositoryRow;
import com.filenet.api.query.SearchSQL;
import com.filenet.api.query.SearchScope;
import com.filenet.api.util.UserContext;
import org.junit.Test;

import javax.security.auth.Subject;
import java.util.Date;
import java.util.Iterator;

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
            SearchScope searchScope = new SearchScope(objectStore);
            Date startTime = new Date();
            System.out.printf("\nStart time : %s\n\n", startTime);
            SearchSQL searchSQL = new SearchSQL(
                    "SELECT " +
                            "d.DocTitleCodeExd,d.VersionSeries,h.NewStatus,h.PackStateChangeDate,e.Department " +
                            "FROM ((SzmnDocuments d " +
                            "INNER JOIN DocumentsPack p " +
                            "ON d.IdPack=p.IdPack) " +
                            "INNER JOIN PackChangeHistory h " +
                            "ON p.This=h.AnnotatedObject) " +
                            "INNER JOIN Employee e " +
                            "ON d.CuratorFullLogin=e.Ad_User " +
                            "WHERE " +
                            "(d.DateCreated<=20160229T180000Z) " +
                            "AND " +
                            "(d.DocDate>=20160215T000000Z AND d.DocDate<=20160229T180000Z) " +
                            "AND " +
                            "(h.PackStateChangeDate>=20160215T000000Z AND h.PackStateChangeDate<=20160229T180000Z) " +
                            "AND " +
                            "(e.Department='Департамент тестирования') " +
                            "AND " +
                            "(e.IsCurrentVersion=TRUE) "
            );
            RepositoryRowSet repositoryRowSet = searchScope.fetchRows(searchSQL, null, null, true);
            Iterator<RepositoryRow> iterator = repositoryRowSet.iterator();
            int counter = 0;
            while (iterator.hasNext()) {
                RepositoryRow row = iterator.next();
                Properties properties = row.getProperties();
                System.out.printf("%d) " +
                                "Code : %s " +
                                "VsId : %s " +
                                "PackStateChangeDate : %s " +
                                "NewStatus : %s " +
                                "\n",
                        ++counter,
                        properties.getInteger32Value("DocTitleCodeExd"),
                        properties.getIdValue("VersionSeries"),
                        properties.getDateTimeValue("PackStateChangeDate"),
                        properties.getStringValue("NewStatus")
                );
            }
            Date finishTime = new Date();
            System.out.printf("\nFinish time : %s\n", finishTime);
            System.out.printf("Total time : %d s\n", (finishTime.getTime() - startTime.getTime()) / 1000);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            UserContext.get().popSubject();
        }
    }
}
