/**
 * Specific {@link com.appliedolap.essbase.EssApiException} subtypes for error conditions callers
 * may want to catch and handle individually, rather than the generic base exception every other
 * API failure surfaces as.
 *
 * <p>{@link com.appliedolap.essbase.exceptions.NoSuchEssbaseObjectException} is thrown when a
 * name doesn't resolve to a real object on the server - the Essbase REST API itself just returns
 * a generic 400 for this case, so this library detects it and re-throws something callers can
 * inspect ({@link com.appliedolap.essbase.exceptions.NoSuchEssbaseObjectException#getName()},
 * {@link com.appliedolap.essbase.exceptions.NoSuchEssbaseObjectException#getType()}).
 * {@link com.appliedolap.essbase.exceptions.DrillthroughColumnMismatchException} is thrown when a
 * drill-through report's column mapping doesn't supply a value for a dimension it requires one
 * for.
 */
package com.appliedolap.essbase.exceptions;
