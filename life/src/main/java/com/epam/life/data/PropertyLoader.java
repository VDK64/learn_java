package com.epam.life.data;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * This class loads properties from an app_en.properties file in resources package.
 * Static initialization block below creates <code>InputStream</code> with <code>PATH</code>
 * leads to .properties file in resources, initialized property variable and
 * invoke <code>properties.load()</code> to load data in .properties file.
 */
public class PropertyLoader {
    private static Properties properties;
    private static final String PATH = "app_en.properties";

    static {
        try (InputStream in = PropertyLoader.class.getClassLoader().getResourceAsStream(PATH)) {
            properties = new Properties();
            properties.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Properties getProperties() {
        return properties;
    }

}
