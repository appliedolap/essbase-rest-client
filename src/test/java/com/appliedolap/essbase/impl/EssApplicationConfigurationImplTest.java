package com.appliedolap.essbase.impl;

import com.appliedolap.essbase.EssApplicationConfiguration;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * Offline. What is under test is the one decision this type makes for itself - whether the
 * application sets the key - which is deliberately not the server's answer to that question.
 */
public class EssApplicationConfigurationImplTest {

    @Test
    public void takesHavingAValueAsBeingConfigured() {
        // The server's own `configured` field is false on every key it returns, including the two it
        // returns for ?configured=true. Having a value is the only reliable signal, and it is the one
        // the merge produces.
        assertTrue(entry("DATACACHESIZE", "3M").isConfigured());
        assertFalse(entry("AUDITTRAIL", null).isConfigured());
    }

    @Test
    public void carriesTheCatalogueEntryAlongsideTheValue() {
        EssApplicationConfiguration entry = new EssApplicationConfigurationImpl(null, "DATACACHESIZE", "3M",
                "Define the value for the data cache size for Essbase databases.",
                "DATACACHESIZE n", "DATACACHESIZE 90M");

        assertEquals("DATACACHESIZE n", entry.getSyntax());
        assertEquals("DATACACHESIZE 90M", entry.getExample());
        assertTrue(entry.getDescription().startsWith("Define the value"));
    }

    @Test
    public void describesItselfWithOrWithoutAValue() {
        assertEquals("DATACACHESIZE = 3M", entry("DATACACHESIZE", "3M").toString());
        assertEquals("AUDITTRAIL", entry("AUDITTRAIL", null).toString());
    }

    @Test
    public void hasNoCatalogueDetailForAKeyThatCameOnlyFromTheConfigurations() {
        EssApplicationConfiguration entry = entry("SOMETHINGUNLISTED", "1");

        assertTrue("still usable", entry.isConfigured());
        assertNull(entry.getDescription());
        assertNull(entry.getSyntax());
    }

    private static EssApplicationConfiguration entry(String key, String value) {
        return new EssApplicationConfigurationImpl(null, key, value);
    }

}
