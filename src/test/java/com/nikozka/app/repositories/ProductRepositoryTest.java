package com.nikozka.app.repositories;

import com.mongodb.client.MongoCollection;
import com.nikozka.app.models.ProductDTO;
import org.bson.Document;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
class ProductRepositoryTest {
    MongoCollection<Document> productsCollection = mock(MongoCollection.class);
    List<String> addresses = List.of(
            "Street_1", "Street_2", "Street_3", "Street_4",
            "Street_5", "Street_6", "Street_7", "Street_8",
            "Street_9", "Street_10", "Street_11", "Street_12"
    );
    List<String> productCategories = List.of("Product_category_1", "Product_category_2");
    Random random = mock(Random.class);
    @Captor
    ArgumentCaptor<List<Document>> captor;
    ProductRepository testObject = new ProductRepository(productsCollection, addresses, productCategories, random);

    @Test
    void insertProductsTest() {
        ProductDTO productDTO1 = new ProductDTO("Name", 1);
        ProductDTO productDTO2 = new ProductDTO("Name", 2);
        List<ProductDTO> products = List.of(productDTO1, productDTO2);
        int quantity = 2;

        when(random.nextInt()).thenReturn(10);
        testObject.insertProducts(products, quantity);

        verify(productsCollection, times(1)).insertMany(captor.capture(),any());

        List<Document> value = captor.getValue();
        assertEquals(2, value.size());

        assertEquals(12, value.get(0).getList("shops", Document.class).size());
    }
}
