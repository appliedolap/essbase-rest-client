package scratch;

import com.appliedolap.essbase.EssDataSource;
import com.appliedolap.essbase.testing.ReadOnlyIntegrationTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;

@Category(ReadOnlyIntegrationTest.class)
public class DataSourceIT extends AbstractEssbaseServerTest {

    @Test
    public void dataSources() {
        for (EssDataSource dataSource : server.getDataSources()) {
            System.out.println("Datasource: " + dataSource.getName());
        }
    }

}