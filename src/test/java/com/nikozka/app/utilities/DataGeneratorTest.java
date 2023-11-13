package com.nikozka.app.utilities;

import org.junit.jupiter.api.Test;

import static com.nikozka.app.utilities.DataGenerator.generateProductCategory;
import static com.nikozka.app.utilities.DataGenerator.generateShopAddresses;
import static org.junit.jupiter.api.Assertions.*;

class DataGeneratorTest {

    @Test
    void generateShopAddressesTest() {
        assertEquals(12, generateShopAddresses().size());
    }

    @Test
    void generateProductCategoryTest() {
        assertEquals(20, generateProductCategory().size());
    }
}