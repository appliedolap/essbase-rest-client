package com.appliedolap.essbase;

import com.appliedolap.essbase.client.ApiException;
import com.appliedolap.essbase.client.model.Resource;
import com.appliedolap.essbase.util.GenericApiCallback;
import com.appliedolap.essbase.util.GenericDownload;
import okhttp3.Call;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

/**
 * Represents one of the downloadable utilities on the Essbase server.
 */
public class EssUtility extends EssObject {

    private static final Logger logger = LoggerFactory.getLogger(EssUtility.class);

    private final Resource resource;

    EssUtility(ApiContext api, Resource resource) {
        super(api);
        this.resource = resource;
    }

    @Override
    public String getName() {
        return resource.getName();
    }

    /**
     * Checks if the utility is downloadable, as opposed to being a direct link. In reviewing the current response format,
     * the list of utilities includes, for example, a link to the index.html page in the <code>url</code> variable when
     * it's a link to a web page (and no <code>links</code> item), and in the case of an actually downloadable utility,
     * the <code>links</code> item will exist and may look like the following:
     *
     * <pre>
     * "links": [
     *   {
     *     "rel": "download",
     *     "href": "https://129.159.71.17:443/essbase/rest/v1/utilities/cli",
     *     "method": "get",
     *     "type": "application/zip"
     *   }
     * </pre>
     * @return true if the resource is a downloadable file, false if not (such as in the case of a generic link)
     */
    public boolean isDownloadable() {
        return resource.getLinks() != null && !resource.getLinks().isEmpty();
    }

    public void download() {
        if (isDownloadable()) {
            try {
                Call call = api.getTemplatesAndUtilitiesApi().resourcesDownloadUtilityCall(resource.getId(), new GenericApiCallback());
                GenericDownload.download(call.execute());
            } catch (ApiException | IOException e) {
                logger.error("Unable to download: {}", e.getMessage());
                throw new RuntimeException(e);
            }
        } else {
            System.err.println("Not a downloadable type");
        }
    }

//    public void download(File folder) {
//    }

}