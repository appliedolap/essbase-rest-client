package scratch;

import com.appliedolap.essbase.*;
import com.appliedolap.essbase.client.ApiException;
import com.appliedolap.essbase.client.model.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BatchOutlineEditTest {
    public static void main(String[] args) {
        EssServer server = new EssServer("http://localhost:9000/essbase", "admin", "welcome1");
        EssApplication app = server.getApplication("Sample");
        try {
            ApiContext api = app.getApi();
            EssCube cube = app.getCube("Basic");
            /*List<EssDimension> dimenList = cube.getDimensions();
            GenerationLevelList genLevel = api.getDimensionsApi().dimensionsListDimGenerations("Sample", "Basic", "Year");
            EssGeneration generation = new EssGeneration(api, genLevel.getItems().get(0));
            dimenList.get(0).updateGeneration(generation.getNumber(), generation);
            System.out.println("");*/
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