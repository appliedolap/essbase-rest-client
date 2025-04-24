package com.appliedolap.essbase.exceptions;

import com.appliedolap.essbase.EssApiException;
import com.appliedolap.essbase.AbstractEssObject;

public class NoSuchEssbaseObjectException extends EssApiException {

    public NoSuchEssbaseObjectException(String objectName, AbstractEssObject.Type objectType) {
        super("There is no " + objectType + " with name " + objectName);
    }

}