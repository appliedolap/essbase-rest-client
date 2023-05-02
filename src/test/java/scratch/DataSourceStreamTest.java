package scratch;

import com.appliedolap.essbase.EssOutline;
import com.appliedolap.essbase.EssServer;

import java.io.ByteArrayOutputStream;
import java.util.Collections;
import java.util.Map;

public class DataSourceStreamTest {

    public static void main(String[] args) {
        EssServer server = new EssServer("http://docker1:9000/essbase", "admin", "welcome1");
        Map<String, Object> params = Collections.emptyMap();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        //server.streamDataSource("SELECT * FROM TRANS_NO_PARAMS WHERE YEAR_MONTH IN ('Jan')", true, "\t", params, outputStream);
        server.streamDataSource("SELECT * FROM TRANS_NO_PARAMS WHERE YEAR_MONTH IN ('Jan')", true, "\t", params, outputStream);

        String output = outputStream.toString();

        System.out.println("Output:\n" + output);
    }

}