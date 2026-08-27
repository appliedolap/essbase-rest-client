package com.appliedolap.essbase.testing;

/**
 * JUnit {@code @Category} marker for a live-Essbase integration test that only reads server
 * state - safe to run against any reachable server with no side effects.
 */
public interface ReadOnlyIntegrationTest {
}
