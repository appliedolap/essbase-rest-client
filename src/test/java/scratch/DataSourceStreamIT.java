package scratch;

import com.appliedolap.essbase.testing.ReadOnlyIntegrationTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.io.ByteArrayOutputStream;
import java.util.Collections;
import java.util.Map;

@Category(ReadOnlyIntegrationTest.class)
public class DataSourceStreamIT extends AbstractEssbaseServerTest {

    @Test
    public void dataSourceStream() {
        Map<String, Object> params = Collections.emptyMap();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        server.streamDataSource("SELECT * FROM TRANS_NO_PARAMS WHERE YEAR_MONTH IN ('Jan')", true, "\t", params, outputStream);

        System.out.println("Output:\n" + outputStream);
    }

}