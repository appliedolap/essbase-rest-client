package scratch;

import com.appliedolap.essbase.*;
import com.appliedolap.essbase.client.ApiException;
import com.appliedolap.essbase.client.model.*;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BatchOutlineEditTest extends AbstractEssbaseServerTest {

    @Test
    public void batchOutlineEdit() {
        EssApplication app = server.getApplication("Sample");
        try {
            ApiContext api = app.getApi();
            EssCube cube = app.getCube("Basic");
            List<EssLock> locks = cube.getLockedObjects(0, 50);
            EssLock lock = locks.get(0);
            cube.unlockObject(lock);
            System.out.println();

            /*List<EssDimension> dimensionList = cube.getDimensions();
            System.out.println();*/
            /*System.out.println("Calling");
            GenerationLevelList genLevel = api.getDimensionsApi().dimensionsListDimLevels("Sample", "Basic", "Year");
            System.out.println("done "+genLevel);
            List<EssGeneration> generations = new ArrayList<>();
            /*for (GenerationLevelList generationLevelList : genLevel) {
                if (generationLevelList.getItems() != null) {
                    for (GenerationLevel generationLevel : generationLevelList.getItems()) {
                       *//* EssGeneration generation = new EssGeneration(api, this, generationLevel);
                        generations.add(generation);*//*
                    }
                }
            }*/
        } catch (Exception e) {
            throw new EssApiException(e);
        }

    }

}