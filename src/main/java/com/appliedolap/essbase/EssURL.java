package com.appliedolap.essbase;

import java.net.URI;

public interface EssURL extends EssObject {

    @Override
    String getName();

    @Override
    Type getType();

    String getURL();

    URI getURI();

}