package com.appliedolap.essbase.exceptions;

import com.appliedolap.essbase.EssApiException;
import com.appliedolap.essbase.EssObject;

public class NoSuchEssbaseObjectException extends EssApiException {

    public NoSuchEssbaseObjectException(String objectName, EssObject.Type objectType) {
        super("There is no " + objectType + " with name " + objectName);
    }

}