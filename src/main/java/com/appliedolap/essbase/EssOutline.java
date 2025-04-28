package com.appliedolap.essbase;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface EssOutline extends EssObject {

    /**
     * Gets the owning cube of this outline.
     *
     * @return the cube for this outline.
     */
    EssCube getCube();

    @Override
    String getName();

    @Override
    Type getType();

    void getMember(String memberName);

    void getMemberSearch(String memberName);

    /**
     * Downloads the outline XML as a byte array.
     *
     * @return the outline XML as bytes
     */
    byte[] downloadXml();

    File downloadToFolder(File folder) throws IOException;

    List<EssMember> getDimensions();

    void insertSibling();

}