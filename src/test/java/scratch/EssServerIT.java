package scratch;

import com.appliedolap.essbase.EssFile;
import com.appliedolap.essbase.EssServer;
import org.junit.Test;

public class EssServerIT {

    @Test
    public void getFile() {
        EssServer server = new EssServer("http://docker1:9000/essbase", "admin", "welcome1");
        EssFile sampleBasic = server.getFile("gallery/Applications/Demo Samples/Block Storage", "Sample_Basic.xlsx");
    }

}