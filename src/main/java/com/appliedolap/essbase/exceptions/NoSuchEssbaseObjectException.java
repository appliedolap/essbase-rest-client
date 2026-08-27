package com.appliedolap.essbase.exceptions;

import com.appliedolap.essbase.EssApiException;
import com.appliedolap.essbase.EssObject;

public class NoSuchEssbaseObjectException extends EssApiException {

    private final String name;

    private final EssObject.Type type;

    public NoSuchEssbaseObjectException(String name, EssObject.Type type) {
        super("There is no " + type + " with name " + name);
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public EssObject.Type getType() {
        return type;
    }

}