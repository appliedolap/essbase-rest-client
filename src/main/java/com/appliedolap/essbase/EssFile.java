package com.appliedolap.essbase;

import com.appliedolap.essbase.impl.EssFolderImpl;

import java.io.File;

public interface EssFile extends EssObject {

    /**
     * Returns the filename portion of the file.
     *
     * @return the filename
     */
    String getName();

    @Override
    Type getType();

    /**
     * Returns the full path of the file, i.e. the combination of its path and filename.
     *
     * @return the full path of the file
     */
    String getFullPath();

    /**
     * Convenience method to return the path to the file.
     *
     * @return the path of this file
     */
    String getPath();

    /**
     * Checks if this file object represents a file or a folder. If this method returns <code>true</code>, then this
     * class may be cast to {@link EssFolderImpl}, which is a subclass of this one.
     *
     * @return true if this object represents a folder, false otherwise
     */
    boolean isFolder();

    /**
     * Returns true if this is a file, not a folder.
     *
     * @return true if this object is a file
     */
    boolean isFile();

    /**
     * Returns the owning Essbase server instance for this file.
     *
     * @return the Essbase server parent object
     */
    EssServer getServer();

    /**
     * Downloads this file to the current directory
     *
     * @return a local file system reference to the downloaded file
     */
    File download();

    /**
     * Downloads the file to a particular folder).
     * TODO: check if folder and download there, if not, download as specific file
     *
     * @param folder the folder
     * @return a local filesystem reference to the created file
     */
    File downloadToFolder(File folder);

    /**
     * Delete this file.
     */
    void delete();

    // TODO: move, this shouldn't be in this class
    @Deprecated
    void lcmImport();

    /**
     * Copy a file from source to destination.
     *
     * @param body      File path details.(required)
     * @param overwrite Overwrite existing file. (optional, default to false)
     */
    void copy(EssFilePathDetail body, Boolean overwrite);

    /**
     * Moves a file from source to destination. Moving a folder is not supported
     *
     * @param body      File path details.(required)
     * @param overwrite Overwrite existing file. Only applicable for moving a file. (optional, default to false)
     */
    void move(EssFilePathDetail body, Boolean overwrite);

    /**
     * Renames a file or folder. Renaming a folder is supported only if the folder is not in the applications directory
     *
     * @param body      File path details.(required)
     * @param overwrite Overwrite existing file. Only applicable for renaming a file. (optional, default to false)
     */
    void rename(EssFilePathDetail body, Boolean overwrite);

    /**
     * Extract a zip file on same location. Supported for applications, users, and shared folders.
     *
     * @param body      File path details.(required)
     * @param overwrite Overwrite existing file. Not applicable for folder. (optional, default to false)
     */
    void extract(EssZipFileDetails body, Boolean overwrite);

}