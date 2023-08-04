package scratch;

import com.appliedolap.essbase.EssDataSource;
import org.junit.Test;

public class DataSourceTest extends AbstractEssbaseServerTest {

    @Test
    public void dataSources() {
        for (EssDataSource dataSource : server.getDataSources()) {
            System.out.println("Datasource: " + dataSource.getName());
        }
    }

}