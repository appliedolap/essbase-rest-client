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
            MemberBean memberBean = api.getOutlineViewerApi().outlineGetMemberInfo(cube.getApplication().getName(), cube.getName(), memberName, null, null, null);
            System.out.println();
        } catch (ApiException apiException) {
            apiException.printStackTrace();
        }
    }

    @Override
    public void getMemberSearch(String memberName) {
        try {
            RestCollectionResponse restCollectionResponse = api.getOutlineViewerApi().outlineGetMembers(cube.getApplication().getName(), cube.getName(), null, null, null, true, memberName, null, null, null, 0, 0);
            System.out.println();
        } catch (ApiException apiException) {
            apiException.printStackTrace();
        }
    }

    @Override
    public byte[] downloadXml() {
        return WrapperUtil.doWithWrap(() -> {
            try {
                ExportOptions exportOptions = new ExportOptions();
                String path = "/outline/" + ApiClient.urlEncode(cube.getApplication().getName())
                        + "/" + ApiClient.urlEncode(cube.getName()) + "/xml";
                return GenericDownload.downloadBytes(NativeHttp.send(api.getClient(), NativeHttp.request(api.getClient(), path)
                        .header("Content-Type", "application/json")
                        .POST(NativeHttp.jsonBody(api.getClient(), exportOptions)), "outlineGetOutlineXML"));
            } finally {
                releaseActiveCube();
            }
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
            RestCollectionResponse dimResponse = api.getOutlineViewerApi().outlineGetMembers(getCube().getApplication().getName(), getCube().getName(), null, null, null, null, null, null, null, null, 0, 0);
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

    /**
     * Gives up the server-side active cube that exporting the outline claims and does not let go of.
     *
     * <p>The export sets this cube active on the session and leaves it that way, so the next export
     * of any <em>other</em> cube in the same session is refused with "Cannot set active cube X as
     * already active on cube Y. Clear the active cube and then, retry." - and a refused export does
     * not clear it either, so one export wedges the session for good.
     *
     * <p>There is no endpoint that clears it: nothing in 26.1's spec unsets an active cube, and the
     * server offers no method on /outline/{app}/{cube} but GET. What does clear it is any other
     * Outline Viewer call, all of which release the cube when they finish - measured against 26.1,
     * the XML export is the only one of the seven that leaks. So the export borrows the cheapest of
     * them, asking for a single dimension purely for the release that follows.
     *
     * <p>Failing to release is worth a warning rather than an exception: the export itself succeeded,
     * and the only casualty is that the next export has to be the one that unwedges the session.
     */
    private void releaseActiveCube() {
        String application = cube.getApplication().getName();
        try {
            api.getOutlineViewerApi().outlineGetMembers(application, cube.getName(),
                    null, null, null, null, null, null, null, null, 0, 1);
        } catch (ApiException e) {
            logger.warn("Could not release the active cube after exporting the outline of {}.{}",
                    application, cube.getName(), e);
        }
    }
}
