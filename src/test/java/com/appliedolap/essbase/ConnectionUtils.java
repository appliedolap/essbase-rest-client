package com.appliedolap.essbase;

import com.appliedolap.essbase.impl.EssServerImpl;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static com.appliedolap.essbase.ApiClientFactory.ESSBASE_NETWORK_LOGGING;

public class ConnectionUtils {

    private static final String DEFAULT_PROPERTIES_FILENAME = "essbase-test.properties";

    private ConnectionUtils() {}

    public static EssServer server() {
        System.setProperty(ESSBASE_NETWORK_LOGGING, "true");

        Properties properties = new Properties();
        try (InputStream fis = new FileInputStream(System.getProperty("user.home") + "/" + DEFAULT_PROPERTIES_FILENAME)) {
            properties.load(fis);
            String endpoint = properties.getProperty("essbase.endpoint");
            String username = properties.getProperty("essbase.username");
            String password = properties.getProperty("essbase.password");
            return new EssServerImpl(endpoint, username, password);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}