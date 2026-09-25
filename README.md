# Essbase REST Java Client

The Essbase REST Java Client is an open source Java library created by [Applied OLAP](https://www.appliedolap.com) for working with an Essbase server using its REST API. This is intended to be used with Essbase 21+ but may also work, to an extent, with Essbase 19. This library cannot be used with older Essbase/EPM versions such as EPM 11.1.2.4.

This library is a very functional but does not implement everything available in the Essbase REST API. Contributions and pull requests are welcome. We will be filling in methods as they are needed.

## Documentation

**[developer.dodecasoftware.com/essbase-rest-client](https://developer.dodecasoftware.com/essbase-rest-client/)**

| | |
| --- | --- |
| [Javadoc](https://developer.dodecasoftware.com/essbase-rest-client/api/) | The public API |
| [What's new by version](https://developer.dodecasoftware.com/essbase-rest-client/) | Every endpoint Oracle added to the Essbase REST API, by release |
| [Coverage](https://developer.dodecasoftware.com/essbase-rest-client/coverage.html) | Every endpoint in the newest spec, and whether this library reaches it |

## Versus Essbase Java API

The Essbase Java API has been the gold standard for connectivity and interaction with Essbase over the years. There are a few reasons you may want to use this library. This library provides access to some functionality that is _only_ in the REST API, is less sensitive to version changes (sometimes the Essbase Java JARs would receive binary incompatible changes), and it may be easier to access your Essbase server using its normal HTTPS port instead of the APS port.

## Using in a Java Project

You can pull this library in using Maven by adding this to your `<dependencies>` section:

        <dependency>
            <groupId>com.appliedolap.essbase</groupId>
            <artifactId>essbase-rest-client</artifactId>
            <version>2.0.2</version>
        </dependency>

You may want to check the versions available on [Maven Central](https://central.sonatype.com/artifact/com.appliedolap.essbase/essbase-rest-client) to ensure you are using the latest version.

Version 2.x requires Java 17 or later. Coming from 1.x, the high-level `Ess*` API is unchanged, but the generated `com.appliedolap.essbase.client` layer now uses the JDK's `HttpClient` and Jackson in place of OkHttp and Gson, so code that called its `okhttp3.Call`/`*Async` methods or relied on Gson annotations will need updating.

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

## API Version History and Coverage

The `spec/` folder archives the Essbase REST API specification as shipped with each release - 21.1,
21.4, 21.5, 21.7 and 26.1 - along with generated reports built from them. Each report is published
on the documentation site above and also committed here, so it can be read either way:

| Report | On the site | In this repository |
| --- | --- | --- |
| Every endpoint added, by version | [What's new](https://developer.dodecasoftware.com/essbase-rest-client/) | [spec/diffs/README.md](spec/diffs/README.md) |
| What this library reaches, and what it does not | [Coverage](https://developer.dodecasoftware.com/essbase-rest-client/coverage.html) | [spec/diffs/coverage.md](spec/diffs/coverage.md) |
| One version step in full | e.g. [21.7 to 26.1](https://developer.dodecasoftware.com/essbase-rest-client/21.7-to-26.1.html) | [spec/diffs/21.7-to-26.1.md](spec/diffs/21.7-to-26.1.md) |

The version history is a quick way to find out whether an endpoint you want exists on the Essbase
version you are targeting. The coverage report is the place to look if you want to contribute: the
endpoints marked "generated, not exposed" are the cheapest to add, because the generated method
already exists and only an `Ess*` wrapper is missing.

See [spec/README.md](spec/README.md) for how the archive works and how to add a version.

## License

Licensed under the Apache License version 2.