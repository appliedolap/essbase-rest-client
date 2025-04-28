package com.appliedolap.essbase;

import com.appliedolap.essbase.impl.EssFileImpl;

import java.io.File;
import java.util.List;

public interface EssFolder extends EssObject, EssFile {

    /**
     * Check if this item is a folder (true for <code>EssFolder</code> objects, false for {@link EssFileImpl} objects).
     *
     * @return true if a folder, false if file
     */
    @Override
    boolean isFolder();

    /**
     * Inverse of {@link #isFolder()}.
     *
     * @return true if it's a file, false otherwise
     */
    @Override
    boolean isFile();

    /**
     * Creates a subfolder in this folder.
     *
     * @param subFolderName the name of the subfolder
     */
    void createSubFolder(String subFolderName);

    /**
     * Uploads a file to this folder.
     *
     * @param file the file
     */
    void uploadFile(File file);

    List<EssFile> getFiles();

}