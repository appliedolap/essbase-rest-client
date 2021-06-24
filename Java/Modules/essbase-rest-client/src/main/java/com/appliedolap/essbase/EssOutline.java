package com.appliedolap.essbase;

import com.appliedolap.essbase.client.ApiClient;
import com.appliedolap.essbase.client.ApiException;
import com.appliedolap.essbase.client.api.DimensionsApi;
import com.appliedolap.essbase.client.api.OutlineViewerApi;
import com.appliedolap.essbase.client.model.*;
import com.appliedolap.essbase.util.GenericApiCallback;
import com.appliedolap.essbase.util.GenericDownload;
import com.appliedolap.essbase.util.WrapperUtil;
import okhttp3.Call;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class EssOutline extends EssObject {

    private static final Logger logger = LoggerFactory.getLogger(EssOutline.class);

    private final EssCube cube;

    private final OutlineViewerApi outlineViewerApi;

    private final DimensionsApi dimensionsApi;

    public EssOutline(ApiClient client, EssCube cube) {
        super(client);
        this.cube = cube;
        this.outlineViewerApi = new OutlineViewerApi(client);
        this.dimensionsApi = new DimensionsApi(client);
    }

    public EssCube getCube() {
        return cube;
    }

    @Override
    public String getName() {
        return null;
    }

    public void getMember(String memberName) {
        try {
            MemberBean memberBean = outlineViewerApi.outlineGetMemberInfo(cube.getApplication().getName(), cube.getName(), memberName, null);
            System.out.println();
        } catch (ApiException apiException) {
            apiException.printStackTrace();
        }
    }

    public void getMemberSearch(String memberName) {
        try {
            RestCollectionResponse restCollectionResponse = outlineViewerApi.outlineGetMembers(cube.getApplication().getName(), cube.getName(), null, true, memberName, null, null, null, 0, 0);
            System.out.println();
        } catch (ApiException apiException) {
            apiException.printStackTrace();
        }
    }

    public byte[] downloadXml() {
        return WrapperUtil.doWithWrap(() -> {
            ExportOptions exportOptions = new ExportOptions();
            Call call = outlineViewerApi.outlineGetOutlineXMLCall(cube.getApplication().getName(), cube.getName(), exportOptions, new GenericApiCallback());
            return GenericDownload.downloadBytes(call.execute());
        });

//        try {
//            ExportOptions exportOptions = new ExportOptions();
//            Call call = outlineViewerApi.outlineGetOutlineXMLCall(cube.getApplication().getName(), cube.getName(), exportOptions, new GenericApiCallback());
//            return GenericDownload.downloadBytes(call.execute());
//        } catch (ApiException | IOException e) {
//            throw new RuntimeException(e);
//        }
    }

    public File downloadToFolder(File folder) throws IOException {
        if (!folder.isDirectory()) throw new IOException("Must supply a valid directory");
        File outputFile = new File(folder, cube.getName() + ".xml");
        try (OutputStream outputStream = new FileOutputStream(outputFile)) {
            IOUtils.write(downloadXml(), outputStream);
            logger.info("Downloaded outline XML to {}", outputFile.toPath().normalize().toAbsolutePath());
        }

        return outputFile;
    }

    public List<EssDimension> getDimensions() {
        try {
            DimensionList dimensionList = dimensionsApi.dimensionsListDimensions(cube.getApplication().getName(), cube.getName());
            List<EssDimension> dimensions = new ArrayList<>();
            for (DimensionBean dimension : dimensionList.getItems()) {
                EssDimension essDimension = new EssDimension(client, dimension);
                dimensions.add(essDimension);
            }
            return dimensions;
        } catch (ApiException e) {
            throw new RuntimeException(e);
        }
    }

}