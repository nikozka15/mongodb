package com.nikozka.app.configs;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class PropertiesLoaderTest {

    @Test
    void loadPropertiesTestCorrectLoad(){
        PropertiesLoader testObj = new PropertiesLoader();
        testObj.loadProperties("test.properties");
        Assertions.assertEquals("username", testObj.getProperty("username"));
    }

    @Test
    void loadPropertiesTestIncorrectLoad(){
        PropertiesLoader testObj = new PropertiesLoader();

        IllegalArgumentException illegalArgumentException = Assertions.assertThrows(IllegalArgumentException.class,
                () -> testObj.loadProperties("incorrect.properties"));
        Assertions.assertEquals("Error while loading file ", illegalArgumentException.getLocalizedMessage());
    }
}
