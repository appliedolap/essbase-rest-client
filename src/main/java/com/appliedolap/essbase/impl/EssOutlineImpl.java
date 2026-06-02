package com.appliedolap.essbase.impl;

import com.appliedolap.essbase.*;
import com.appliedolap.essbase.client.ApiClient;
import com.appliedolap.essbase.client.ApiException;
import com.appliedolap.essbase.client.api.BatchOutlineEditingApi;
import com.appliedolap.essbase.client.model.ExportOptions;
import com.appliedolap.essbase.client.model.MemberBean;
import com.appliedolap.essbase.client.model.OtlEditMain;
import com.appliedolap.essbase.client.model.RestCollectionResponse;
import com.appliedolap.essbase.util.GenericDownload;
import com.appliedolap.essbase.util.NativeHttp;
import com.appliedolap.essbase.util.WrapperUtil;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

/**
 * Models the outline for a particular cube.
 */
public class EssOutlineImpl extends AbstractEssObject implements EssOutline {

    private static final Logger logger = LoggerFactory.getLogger(EssOutlineImpl.class);

    private final EssCube cube;

    EssOutlineImpl(ApiContext api, EssCube cube) {
        super(api);
        this.cube = cube;
    }

    @Override
    public EssCube getCube() {
        return cube;
    }

    @Override
    public String getName() {
        return cube.getName();
    }

    @Override
    public Type getType() {
        return Type.OUTLINE;
    }

    @Override
    public void getMember(String memberName) {
        try {
            MemberBean memberBean = api.getOutlineViewerApi().outlineGetMemberInfo(cube.getApplication().getName(), cube.getName(), memberName, null);
            System.out.println();
        } catch (ApiException apiException) {
            apiException.printStackTrace();
        }
    }

    @Override
    public void getMemberSearch(String memberName) {
        try {
            RestCollectionResponse restCollectionResponse = api.getOutlineViewerApi().outlineGetMembers(cube.getApplication().getName(), cube.getName(), null, true, memberName, null, null, null, 0, 0);
            System.out.println();
        } catch (ApiException apiException) {
            apiException.printStackTrace();
        }
    }

    @Override
    public byte[] downloadXml() {
        return WrapperUtil.doWithWrap(() -> {
            ExportOptions exportOptions = new ExportOptions();
            String path = "/outline/" + ApiClient.urlEncode(cube.getApplication().getName())
                    + "/" + ApiClient.urlEncode(cube.getName()) + "/xml";
            return GenericDownload.downloadBytes(NativeHttp.send(api.getClient(), NativeHttp.request(api.getClient(), path)
                    .header("Content-Type", "application/json")
                    .POST(NativeHttp.jsonBody(api.getClient(), exportOptions)), "outlineGetOutlineXML"));
        });
    }

    @Override
    public File downloadToFolder(File folder) throws IOException {
        if (!folder.isDirectory()) throw new IOException("Must supply a valid directory");
        File outputFile = new File(folder, cube.getName() + ".xml");
        try (OutputStream outputStream = new FileOutputStream(outputFile)) {
            IOUtils.write(downloadXml(), outputStream);
            logger.info("Downloaded outline XML to {}", outputFile.toPath().normalize().toAbsolutePath());
        }
        return outputFile;
    }

    @Override
    public List<EssMember> getDimensions() {
        try {
            RestCollectionResponse dimResponse = api.getOutlineViewerApi().outlineGetMembers(getCube().getApplication().getName(), getCube().getName(), null, null, null, null, null, null, 0, 0);
            return EssMemberImpl.collectionToMembers(api, getCube(), dimResponse);
        } catch (ApiException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void insertSibling() {
        OtlEditMain otlEditMain = new OtlEditMain();
        //otlEditMain.setE
        BatchOutlineEditingApi batch = api.getBatchOutlineEditingApi();
        //batch.
    }

}