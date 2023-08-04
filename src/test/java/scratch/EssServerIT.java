package scratch;

import com.appliedolap.essbase.EssFile;
import org.junit.Test;

public class EssServerIT extends AbstractEssbaseServerTest {

    @Test
    public void getFile() {
        EssFile sampleBasic = server.getFile("gallery/Applications/Demo Samples/Block Storage", "Sample_Basic.xlsx");
    }

}