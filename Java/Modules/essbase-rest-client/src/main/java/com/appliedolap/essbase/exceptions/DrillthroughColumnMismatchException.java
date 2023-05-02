package com.appliedolap.essbase.exceptions;

import com.appliedolap.essbase.EssApiException;
import com.appliedolap.essbase.EssDrillthrough;

public class DrillthroughColumnMismatchException extends EssApiException {

    private static final String MESSAGE = "Cannot run drill-through report %s, as column mapping %s requires a value for dimension %s";

    public DrillthroughColumnMismatchException(EssDrillthrough drillthrough, String missingColummMapping, String missingDimension) {
        super(String.format(MESSAGE, drillthrough.getName(), missingColummMapping, missingDimension));
    }

}