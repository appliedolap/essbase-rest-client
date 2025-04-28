package com.appliedolap.essbase;

import com.appliedolap.essbase.impl.EssCubeImpl;
import com.appliedolap.essbase.impl.EssCubeVariableImpl;
import com.appliedolap.essbase.impl.EssVariableImpl;

import java.io.File;
import java.io.OutputStream;
import java.util.List;

public interface EssCube extends EssObject {

    /**
     * Get the list of scripts associated with this cube.
     *
     * @return the scripts on this cube
     */
    List<EssScript> getCalcScripts();

    /**
     * Gets the parent application of this cube.
     *
     * @return the parent application
     */
    EssApplication getApplication();

    /**
     * Gets the list of sessions on this cube.
     *
     * @return the current cube sessions
     */
    List<EssSession> getSessions();

    /**
     * Gets the list of variables specific to this cube. The return type is an {@link EssVariableImpl}, however, the
     * actual implementation will be the subclass {@link EssCubeVariableImpl}. The {@link EssVariableImpl#getScope()} method
     * can be used to check the variable type and cast as needed.
     *
     * @return the cube variables
     */
    List<EssCubeVariable> getVariables();

    /**
     * Gets an outline object for this cube.
     *
     * @return an outline object
     */
    EssOutline getOutline();

    /**
     * Checks if this cube has scenarios enabled.
     *
     * @return true if scenarios are enabled, false otherwise
     */
    boolean isScenariosEnabled();

    /**
     * Creates a URL-type drill-through on the cube,
     *
     * @param urlName      the name (no spaces?!)
     * @param urlLink      the drill-through link
     * @param drillRegions the drillable regions
     * @return a new drill-through object
     */
    EssDrillthrough createDrillthroughURL(String urlName, String urlLink, List<String> drillRegions);

    /**
     * Get list of drill-through reports on the cube.
     *
     * @return the list of cube drill-through reports
     */
    List<EssDrillthrough> getDrillthroughs();

    /**
     * Gets a drill-through report with a specific name. Note that the current implementation of this method hits the
     * drill-through report list endpoint and filters based on the name, so you will likely end up with two trips to the
     * server: one to get the list, and one to get the details of the report, which will happen when you access most
     * detailed properties in the drill-through report.
     *
     * @param drillthroughName the name of the drill-through report
     * @return a drill-through report object
     */
    EssDrillthrough getDrillthrough(String drillthroughName);

    /**
     * Gets the list of scenarios on the cube, if any.
     *
     * @return the cube's scenarios
     */
    List<EssScenario> getScenarios();

    /**
     * Gets a reference to a particular member in the cube outline. This method is likely to change in the future as the
     * outline management and other classes come in to focus, to say nothing of how member IDs and unique members and
     * such will need to be handled.
     *
     * @param memberName the name of the member
     * @return the member
     */
    EssMember getMember(String memberName);

    /**
     * Gets the list of dimensions on the cube, if any.
     *
     * @return the cube's dimensions
     */
    List<EssDimension> getDimensions();

    /**
     * List Locked Objects Returns all the locked objects from the specified application and database
     *
     * @param offset Number of items to omit from the start of the result set. Default value is 0
     * @param limit  Maximum number of objects to return. Default is 50
     * @return LockObjectLis
     */
    List<EssLock> getLockedObjects(Integer offset, Integer limit);

    /**
     * Unlock Object Unlocks the object in the specified application and database
     *
     * @param lockedObject Details about object to be unlocked
     */
    void unlockObject(EssLock lockedObject);

    /**
     * Lock Object Locks the object in the specified application and database and returns the details of the locked
     * object
     *
     * @param unlockedObject Object details to be locked (required)
     */
    void lockObject(EssLock unlockedObject);

    /**
     * Exports this cube to an Excel workbook.
     */
    void exportExcel();

    /**
     * Updates this cube using an Excel workbook. This API is likely to change soon to take an EssFile reference
     *
     * @param path     the path
     * @param filename the filename
     */
    void importExcel(String path, String filename);

    /**
     * Acts as a convenience function for uploading a file to a cube's storage folder. The Essbase server maps the file
     * location <code>/applications/AppName/CubeName</code>, which contains the cube files such as calc scripts, load
     * rules, and whatnot.
     *
     * @param file the file to upload to the cube
     */
    void importFile(File file);

    void executeMdx(String query, MdxOutputType outputType, EssCubeImpl.MdxOptions mdxOptions, OutputStream outputStream);

    EssGrid executeMdx(String query);

    enum MdxOutputType {

        JSON, HTML, XLSX, CSV;

        public boolean isPrintable() {
            return this != XLSX;
        }

    }

    enum MdxIdentifierType {
        NAME, ALIAS, UNIQUE_NAME
    }

}