package com.appliedolap.essbase;

/**
 * What an application workbook import should actually do.
 *
 * <p>An application workbook is not only an outline. Oracle's own Sample_Basic.xlsx carries the cube
 * definition, a sheet per dimension, 4,256 rows of data, a calculation script and a sample query - and
 * the import reads as much or as little of that as it is told to. The job reports back exactly four
 * flags it acted on: load data, recreate application, create files, execute script.
 *
 * <p>The defaults load the data and run the scripts, because a workbook that carries both almost
 * certainly means them to be used: an import that skips them leaves a cube whose every upper level
 * reads #Missing, which looks like a failed import rather than a partial one.
 */
public final class EssWorkbookImportOptions {

    private boolean loadData = true;

    private boolean executeScripts = true;

    private boolean createFiles = true;

    private boolean recreateApplication;

    private boolean overwrite = true;

    private boolean deleteWorkbookOnSuccess;

    /** Loads the data and runs the scripts; does not recreate an existing application. */
    public static EssWorkbookImportOptions defaults() {
        return new EssWorkbookImportOptions();
    }

    /** Whether to load the workbook's data sheets. */
    public EssWorkbookImportOptions loadData(boolean loadData) {
        this.loadData = loadData;
        return this;
    }

    /**
     * Whether to run the calculation scripts the workbook carries.
     *
     * <p>This is what makes the difference between a cube with data in it and a cube you can query:
     * loading writes level 0 and aggregates nothing.
     */
    public EssWorkbookImportOptions executeScripts(boolean executeScripts) {
        this.executeScripts = executeScripts;
        return this;
    }

    /** Whether to create the scripts, rules and queries the workbook defines. */
    public EssWorkbookImportOptions createFiles(boolean createFiles) {
        this.createFiles = createFiles;
        return this;
    }

    /**
     * Whether to destroy an existing application of this name and build it again.
     *
     * <p>Off by default, and the only genuinely destructive thing here: it discards the application
     * and everything in it, not just what the workbook describes.
     */
    public EssWorkbookImportOptions recreateApplication(boolean recreateApplication) {
        this.recreateApplication = recreateApplication;
        return this;
    }

    /** Whether the import may overwrite artifacts it finds already present. */
    public EssWorkbookImportOptions overwrite(boolean overwrite) {
        this.overwrite = overwrite;
        return this;
    }

    /** Whether to delete the workbook from the catalogue once the import succeeds. */
    public EssWorkbookImportOptions deleteWorkbookOnSuccess(boolean deleteWorkbookOnSuccess) {
        this.deleteWorkbookOnSuccess = deleteWorkbookOnSuccess;
        return this;
    }

    public boolean isLoadData() {
        return loadData;
    }

    public boolean isExecuteScripts() {
        return executeScripts;
    }

    public boolean isCreateFiles() {
        return createFiles;
    }

    public boolean isRecreateApplication() {
        return recreateApplication;
    }

    public boolean isOverwrite() {
        return overwrite;
    }

    public boolean isDeleteWorkbookOnSuccess() {
        return deleteWorkbookOnSuccess;
    }

}
