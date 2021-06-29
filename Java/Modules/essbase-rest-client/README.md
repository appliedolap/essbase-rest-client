# Essbase REST Java Client

This library is a combination of auto-generated classes using the Open API client generator, and a simple developer-friendly API that sits on top and makes the auto-generated code easy to use. 

The auto-generated code is based on a modified version of the Essbase REST Swagger/Open API document that is available. A series of fixes are applied to it using the `jq` command-line to to update nodes in the document tree. Generally these changes are to fix return types that would otherwise not deserialize propertly. Once the document has been processed/fixed, the Open API code generator is run. The code is generated into the `/target` folder and then a part of it is copied in to the source tree for this library. 

In practice is seems that these generated library 'want' to be in their own distinct Maven module but I am going against the grain a bit and combining the auto-generated code and the client library
in a single module for the sake of convenience and some technical factors.

The library can be consumed as simply as doing the following:

    EssServer server = new EssServer("http://docker1:9000/essbase/rest/v1", "admin", "welcome1");
    List<EssApplication> applications = server.getApplications();
    for (EssApplication application : applications) {
        System.out.println("App: " + application);
    }

# Generating Javadoc

You can generate Javadoc by executing the following goal from the project folder:

    mvn clean javadoc:javadoc

After generation you should be able to browse to `target/site/apidocs/index.html`.