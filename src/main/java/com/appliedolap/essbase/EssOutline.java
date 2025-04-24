package com.appliedolap.essbase;

import com.appliedolap.essbase.client.ApiException;
import com.appliedolap.essbase.client.api.BatchOutlineEditingApi;
import com.appliedolap.essbase.client.model.ExportOptions;
import com.appliedolap.essbase.client.model.MemberBean;
import com.appliedolap.essbase.client.model.OtlEditMain;
import com.appliedolap.essbase.client.model.RestCollectionResponse;
import com.appliedolap.essbase.util.GenericApiCallback;
import com.appliedolap.essbase.util.GenericDownload;
import com.appliedolap.essbase.util.WrapperUtil;
import okhttp3.Call;
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
public class EssOutline extends AbstractEssObject {

    private static final Logger logger = LoggerFactory.getLogger(EssOutline.class);

    private final EssCube cube;

    EssOutline(ApiContext api, EssCube cube) {
        super(api);
        this.cube = cube;
    }

    /**
     * Gets the owning cube of this outline.
     *
     * @return the cube for this outline.
     */
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

    public void getMember(String memberName) {
        try {
            MemberBean memberBean = api.getOutlineViewerApi().outlineGetMemberInfo(cube.getApplication().getName(), cube.getName(), memberName, null);
            System.out.println();
        } catch (ApiException apiException) {
            apiException.printStackTrace();
        }
    }

    public void getMemberSearch(String memberName) {
        try {
            RestCollectionResponse restCollectionResponse = api.getOutlineViewerApi().outlineGetMembers(cube.getApplication().getName(), cube.getName(), null, true, memberName, null, null, null, 0, 0);
            System.out.println();
        } catch (ApiException apiException) {
            apiException.printStackTrace();
        }
    }

    /**
     * Downloads the outline XML as a byte array.
     *
     * @return the outline XML as bytes
     */
    public byte[] downloadXml() {
        return WrapperUtil.doWithWrap(() -> {
            ExportOptions exportOptions = new ExportOptions();
            Call call = api.getOutlineViewerApi().outlineGetOutlineXMLCall(cube.getApplication().getName(), cube.getName(), exportOptions, new GenericApiCallback());
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

    public List<EssMember> getDimensions() {
        try {
            RestCollectionResponse dimResponse = api.getOutlineViewerApi().outlineGetMembers(getCube().getApplication().getName(), getCube().getName(), null, null, null, null, null, null, 0, 0);
            return EssMember.collectionToMembers(api, getCube(), dimResponse);
        } catch (ApiException e) {
            throw new RuntimeException(e);
        }
    }

    public void insertSibling() {
        OtlEditMain otlEditMain = new OtlEditMain();
        //otlEditMain.setE
        BatchOutlineEditingApi batch = api.getBatchOutlineEditingApi();
        //batch.
    }

}