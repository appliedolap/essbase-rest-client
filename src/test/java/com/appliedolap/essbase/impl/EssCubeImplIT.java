package com.appliedolap.essbase.impl;

import com.appliedolap.essbase.ConnectionUtils;
import com.appliedolap.essbase.EssCube;
import com.appliedolap.essbase.EssMember;
import com.appliedolap.essbase.EssObject;
import com.appliedolap.essbase.exceptions.NoSuchEssbaseObjectException;
import com.appliedolap.essbase.testing.ReadOnlyIntegrationTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertThrows;

@Category(ReadOnlyIntegrationTest.class)
public class EssCubeImplIT {

    @Test
    public void getMember() {
        EssCube cube = ConnectionUtils.server().getApplication("Sample").getCube("Basic");
        EssMember actual = cube.getMember("Actual");
        assertThat(actual.isLeaf(), is(true));
    }

    @Test
    public void whenGetInvalidMember() {
        final String invalidMember = "InvalidMember";
        EssCube cube = ConnectionUtils.server().getApplication("Sample").getCube("Basic");
        NoSuchEssbaseObjectException exception = assertThrows(NoSuchEssbaseObjectException.class, () -> cube.getMember(invalidMember));
        assertThat(exception.getName(), is(invalidMember));
        assertThat(exception.getType(), is(EssObject.Type.MEMBER));
    }

}