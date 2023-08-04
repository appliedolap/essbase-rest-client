package scratch;

import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.util.Collections;
import java.util.Map;

public class DataSourceStreamTest extends AbstractEssbaseServerTest {

    @Test
    public void dataSourceStream() {
        Map<String, Object> params = Collections.emptyMap();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        //server.streamDataSource("SELECT * FROM TRANS_NO_PARAMS WHERE YEAR_MONTH IN ('Jan')", true, "\t", params, outputStream);
        server.streamDataSource("SELECT * FROM TRANS_NO_PARAMS WHERE YEAR_MONTH IN ('Jan')", true, "\t", params, outputStream);

        String output = outputStream.toString();

        System.out.println("Output:\n" + output);
    }

}