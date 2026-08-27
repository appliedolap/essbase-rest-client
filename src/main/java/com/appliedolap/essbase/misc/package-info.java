/**
 * Response models for REST endpoints whose JSON shape doesn't correspond to a generated model
 * class. Currently just {@link com.appliedolap.essbase.misc.MdxJson}, the shape returned by the
 * MDX-execute endpoint's JSON output, which
 * {@link com.appliedolap.essbase.impl.EssCubeImpl#executeMdx(String)} deserializes into to build
 * an {@link com.appliedolap.essbase.impl.EssGridImpl} result.
 */
package com.appliedolap.essbase.misc;
