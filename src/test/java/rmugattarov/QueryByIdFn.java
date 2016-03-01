package rmugattarov;

import com.filenet.api.core.Connection;
import com.filenet.api.core.Domain;
import com.filenet.api.core.Factory;
import com.filenet.api.core.ObjectStore;
import com.filenet.api.query.RepositoryRow;
import com.filenet.api.query.SearchSQL;
import com.filenet.api.query.SearchScope;
import com.filenet.api.util.Id;
import com.filenet.api.util.UserContext;
import com.google.common.base.Joiner;
import org.junit.Test;

import javax.security.auth.Subject;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * Created by rmugattarov on 01.03.2016.
 */
public class QueryByIdFn {
    @Test
    public void test() {
        Connection connection = Factory.Connection.getConnection("http://172.28.24.182:9080/wsi/FNCEWS40MTOM/");
        Subject subject = UserContext.createSubject(connection, "rmugattarov@tn.fntst.ru", "o9p0[-]=", null);
        UserContext.get().pushSubject(subject);
        try {
            Domain domain = Factory.Domain.fetchInstance(connection, null, null);
            ObjectStore objectStore = Factory.ObjectStore.fetchInstance(domain, "OST", null);
            SearchScope searchScope = new SearchScope(objectStore);
            Date startTime = new Date();
            System.out.printf("\nStart time : %s\n\n", startTime);
            int batchSize = 10000;
            SearchSQL fetchIdsSearchSql = new SearchSQL("SELECT TOP " + batchSize + " VersionSeries FROM Document");
            Iterator<RepositoryRow> rows = searchScope.fetchRows(fetchIdsSearchSql, 5000, null, true).iterator();
            List<Id> ids = new ArrayList<>();
            while (rows.hasNext()) {
                ids.add(rows.next().getProperties().getIdValue("VersionSeries"));
            }
            Date finishedFetchingIds = new Date();
            System.out.printf("Fetched %d ids in %f s\n", batchSize, (finishedFetchingIds.getTime() - startTime.getTime()) / 1000D);

            StringBuilder docsByIdQuery = new StringBuilder("SELECT VersionSeries From Document WHERE VersionSeries=").append(Joiner.on(" OR VersionSeries=").join(ids));
            SearchSQL fetchDocsSearchSql = new SearchSQL(docsByIdQuery.toString());
            Iterator<RepositoryRow> docs = searchScope.fetchRows(fetchDocsSearchSql, 5000, null, true).iterator();
            int counter = 0;
            while (docs.hasNext()) {
                Id docId = docs.next().getProperties().getIdValue("VersionSeries");
                System.out.printf("%d) %s\n", ++counter, docId);
            }
            Date finishedFetchingDocs = new Date();
            System.out.printf("\nFetched %d docs by id in %f s\n", batchSize, (finishedFetchingDocs.getTime() - finishedFetchingIds.getTime()) / 1000D);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            UserContext.get().popSubject();
        }
    }
}
