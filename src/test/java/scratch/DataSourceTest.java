package scratch;

import com.appliedolap.essbase.EssDataSource;
import com.appliedolap.essbase.EssDrillthrough;
import com.appliedolap.essbase.EssServer;

import java.util.HashMap;
import java.util.Map;

public class DataSourceTest {

    public static void main(String[] args) {
        EssServer server = new EssServer("http://docker1:9000/essbase", "admin", "welcome1");
        for (EssDataSource dataSource : server.getDataSources()) {
            System.out.println("Datasource: " + dataSource.getName());
        }
    }

}