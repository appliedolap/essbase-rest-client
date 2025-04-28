package com.appliedolap.essbase;

public interface EssUtility extends EssObject {

    @Override
    String getName();

    @Override
    Type getType();

    /**
     * Checks if the utility is downloadable, as opposed to being a direct link. In reviewing the current response
     * format, the list of utilities includes, for example, a link to the index.html page in the <code>url</code>
     * variable when it's a link to a web page (and no <code>links</code> item), and in the case of an actually
     * downloadable utility, the <code>links</code> item will exist and may look like the following:
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
     *
     * @return true if the resource is a downloadable file, false if not (such as in the case of a generic link)
     */
    boolean isDownloadable();

    void download();

}