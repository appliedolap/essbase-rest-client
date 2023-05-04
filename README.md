# Essbase REST Java Client

The Essbase REST Java Client is an open source Java library created by [Applied OLAP](https://www.appliedolap.com) for working with an Essbase server using its REST API. This is intended to be used with Essbase 21+ but may also work, to an extent, with Essbase 19. This library cannot be used with older Essbase/EPM versions such as EPM 11.1.2.4.

This library is a very functional but does not implement everything available in the Essbase REST API. Contributions and pull requests are welcome. We will be filling in methods as they are needed.

## Versus Essbase Java API

The Essbase Java API has been the gold standard for connectivity and interaction with Essbase over the years. There are a few reasons you may want to use this library. This library provides access to some functionality that is _only_ in the REST API, is less sensitive to version changes (sometimes the Essbase Java JARs would receive binary incompatible changes), and it may be easier to access your Essbase server using its normal HTTPS port instead of the APS port.

## Using in a Java Project

You can pull this library in using Maven by adding this to your `<dependencies>` section:

        <dependency>
            <groupId>com.appliedolap.essbase</groupId>
            <artifactId>essbase-rest-client</artifactId>
            <version>1.0.2</version>
        </dependency>

You may want to check the versions available on [Maven Central](https://central.sonatype.com/artifact/com.appliedolap.essbase/essbase-rest-client) to ensure you are using the latest version.

## Example

The library can be consumed as simply as doing the following:

    // replace example with your machine running Essbase
    EssServer server = new EssServer("http://example:9000/essbase", "admin", "welcome1");

    List<EssApplication> applications = server.getApplications();
    for (EssApplication application : applications) {
        System.out.println("App: " + application);
    }

This would print out the list of applications on the server. If you want to get an Essbase 21 server up and running using Docker, you might want to look at the [Essbase Docker](https://github.com/appliedolap/docker-essbase) project (also by Applied OLAP).

Although not extensively tested, as this is a normal Java library you should be able to use this with Kotlin, Jython, Groovy, and other languages that work with the JVM.

## Technical Details

The Essbase REST API Swagger/OpenAPI definition document is used as the basis of the auto-generated classes in this library, although a series of changes are applied to it in order to 'fix' things that the OpenAPI generator would otherwise struggle with. These adjustments can be found in the `process.sh` script. Generally these changes are to fix return types that would otherwise not deserialize properly. Once the document has been processed/fixed, the Open API code generator is run. The code is generated into the `/target` folder and then a part of it is copied in to the source tree for this library.

## License

Licensed under the Apache License version 2.