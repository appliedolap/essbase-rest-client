package com.appliedolap.essbase.testing;

/**
 * JUnit {@code @Category} marker for a live-Essbase integration test that mutates server state -
 * creates, deletes, or otherwise changes real objects. Requires an explicit
 * {@code -DallowDestructiveEssbaseTests=true} acknowledgement to run (see the
 * {@code integration-destructive} Maven profile), since it can leave a test server in a different
 * state or destroy real objects.
 */
public interface DestructiveIntegrationTest {
}
