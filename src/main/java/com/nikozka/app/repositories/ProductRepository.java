package com.nikozka.app.repositories;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.InsertManyOptions;
import com.mongodb.client.model.Sorts;
import com.nikozka.app.models.ProductDTO;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static com.mongodb.client.model.Accumulators.sum;
import static com.mongodb.client.model.Aggregates.*;

public class ProductRepository {
    private static final Logger log = LoggerFactory.getLogger(ProductRepository.class);
    MongoCollection<Document> productsCollection;
    private final List<String> addresses;
    private final List<String> productCategories;
    private final Random random;

    public ProductRepository(MongoCollection<Document> productsCollection, List<String> addresses, List<String> productCategories, Random random
    ) {
        this.productsCollection = productsCollection;
        this.addresses = addresses;
        this.productCategories = productCategories;
        this.random = random;
    }

    public void insertProducts(List<ProductDTO> products, int quantity) {
        List<Document> documents = new ArrayList<>();
        for (int i = 1; i <= quantity; i++) {
            documents.add(generateDocument(i, products.get(i-1)));

            if (i % 10_000 == 0) {
                productsCollection.insertMany(documents, new InsertManyOptions().ordered(false));
                log.info("Batch with documents is executed");
                documents.clear();
            }
        }
        if (!documents.isEmpty()) {
            productsCollection.insertMany(documents, new InsertManyOptions().ordered(false));
            log.info("Leftover batch is executed");
        }
    }

    public Document findShopWithMaxQuantityInCategory(String category) {
        return productsCollection.aggregate(Arrays.asList(
                match(Filters.eq("category", category)),
                unwind("$shops"),
                group("$shops", sum("totalQuantity", "$shops.quantity")),
                sort(Sorts.descending("totalQuantity")),
                limit(1)
        )).first();
    }

    private Document generateDocument(int productId, ProductDTO product) {
        List<Document> shops = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            shops.add(generateShopDocument(i));
        }
        return new Document()
                .append("productId", productId)
                .append("name",  product.getName())
                .append("category", productCategories.get(product.getCategoryId() - 1))
                .append("shops", shops);
    }

    private Document generateShopDocument(int shopNumber) {
        return new Document()
                .append("address,", addresses.get(shopNumber))
                .append("quantity", random.nextInt(100));
    }
}
