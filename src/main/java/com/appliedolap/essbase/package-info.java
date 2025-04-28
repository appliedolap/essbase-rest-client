/**
 * The classes in this package model client API that works with the Essbase REST API. In general, the design of the
 * library is completely top-down (similar to the traditional Essbase Java API). The starting place for any client program
 * working with this library is the {@link com.appliedolap.essbase.impl.EssServerImpl}. Once instantiated, usage procedes in a
 * top-down manner, such as getting the list of applications on the server, getting the list of cubes from that application, getting
 * the list of dimensions in that cube, and so on.
 *
 * <p>All objects in the API are created from a parent object, and all objects maintain a reference to their parent. For
 * example, the {@link com.appliedolap.essbase.impl.EssApplicationImpl} object has a method to return a list of cubes in that application,
 * via {@link com.appliedolap.essbase.impl.EssApplicationImpl#getCubes()}, and each cube in the returned list "knows" about its
 * parent/owning application, accessible via {@link com.appliedolap.essbase.impl.EssCubeImpl#getApplication()}.
 */
package com.appliedolap.essbase;