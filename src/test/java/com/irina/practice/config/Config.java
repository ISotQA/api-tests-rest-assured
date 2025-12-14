package com.irina.practice.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {
    private static final Properties props = new Properties();

    static {
        try (InputStream is = Config.class
             .getClassLoader()
             .getResourceAsStream("config.properties")) {

        if (is == null) {
            throw new IllegalStateException("config.properties not found");
        }

        props.load(is);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static String get(String key) {
        return System.getProperty(key, props.getProperty(key));
    }

    public static String apiBaseUrl() {
        return get("api.baseUrl");
    }

    public static String contentType() {
        return get("api.contentType");
    }

    public static String accept() {
        return get("api.accept");
    }
}
