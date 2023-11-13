package com.nikozka.app.configs;

import com.nikozka.app.models.ProjectProperties;

import java.util.Objects;

public class ProjectPropertiesFactory {

    private ProjectPropertiesFactory() {}

    public static ProjectProperties createFromProperties(PropertiesLoader properties) {
        Objects.requireNonNull(properties, "Properties must not be null");

        String username = properties.getProperty("username");
        String password = properties.getProperty("password");
        String clusterUrl = properties.getProperty("clusterUrl");

        return new ProjectProperties(username, password, clusterUrl);
    }
}
