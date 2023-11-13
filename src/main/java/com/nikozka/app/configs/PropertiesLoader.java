package com.nikozka.app.configs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesLoader {
    Properties properties;
    private static final Logger logger = LoggerFactory.getLogger(PropertiesLoader.class);

    public PropertiesLoader() {
        properties = new Properties();
    }

    public void loadProperties(String fileName) {
        try (InputStream file = getClass().getClassLoader().getResourceAsStream(fileName)) {
            if (file != null) {
                properties.load(file);
                logger.debug("File loaded");
            } else {
                throw new FileNotFoundException("Error while loading file ");
            }
        } catch (IOException e) {
            logger.error("File not loaded, no such file or directory for file {}", e.getLocalizedMessage(), e);
            throw new IllegalArgumentException("Error while loading file ");
        }
    }

    public String getProperty(String name) {
        return properties.getProperty(name);
    }
}
