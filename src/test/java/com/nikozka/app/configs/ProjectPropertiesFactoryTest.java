package com.nikozka.app.configs;

import com.nikozka.app.models.ProjectProperties;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProjectPropertiesFactoryTest {

    @Test
    void createFromPropertiesTestValidPropertiesReturnsProjectProperties() {
        PropertiesLoader propertiesLoader = new PropertiesLoader();
        propertiesLoader.loadProperties("test.properties");
        ProjectProperties projectProperties = ProjectPropertiesFactory.createFromProperties(propertiesLoader);
        assertAll(
                () -> assertNotNull(projectProperties, "ProjectProperties should not be null"),
                () -> assertEquals("username", projectProperties.getUsername(), "Username does not match")
        );
    }

    @Test
    void createFromProperties_NullProperties_ThrowsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ProjectPropertiesFactory.createFromProperties(null));
    }
}
