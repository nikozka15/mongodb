package com.nikozka.app;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.nikozka.app.configs.Config;
import com.nikozka.app.configs.PropertiesLoader;
import com.nikozka.app.models.ProductDTO;
import com.nikozka.app.repositories.ProductRepository;
import com.nikozka.app.utilities.Parser;
import com.nikozka.app.utilities.ProductGenerator;
import jakarta.validation.Validator;
import org.apache.commons.lang3.time.StopWatch;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Random;

import static com.nikozka.app.utilities.DataGenerator.generateProductCategory;
import static com.nikozka.app.utilities.DataGenerator.generateShopAddresses;

public class LocalApplicationRunner {
    private static final Logger log = LoggerFactory.getLogger(LocalApplicationRunner.class);
    private static final Random random = new Random();
    private static final String FILE_NAME = "local.properties";
    private static final int TARGET_QUANTITY = 250_000;

    private LocalApplicationRunner() {}

    public static void runApp() {
        PropertiesLoader propertiesLoader = new PropertiesLoader();
        propertiesLoader.loadProperties(FILE_NAME);

        String category = System.getProperty("Тип_товару", "Музика");

        StopWatch watch = new StopWatch();
        Validator validator = Config.createValidator();
        List<ProductDTO> productDTOS = new ProductGenerator(validator).generateProducts(TARGET_QUANTITY);

        try (MongoClient mongoClient =  MongoClients.create(propertiesLoader.getProperty("connectionString"))) {

            MongoDatabase sampleTrainingDB = mongoClient.getDatabase("shop_stock");
            MongoCollection<Document> productsCollection = sampleTrainingDB.getCollection("products");

            List<String> addresses = generateShopAddresses();
            List<String> productCategories = generateProductCategory();
            ProductRepository productRepository = new ProductRepository(productsCollection, addresses, productCategories, random);

            watch.start();
            productRepository.insertProducts(productDTOS, TARGET_QUANTITY);
            watch.stop();
            log.info("Insertion time: {}", watch.getTime());
            watch.reset();

            watch.start();
            Document shopWithMaxQuantityInCategory = productRepository.findShopWithMaxQuantityInCategory(category);
            watch.stop();
            String result = Parser.parseToResult(shopWithMaxQuantityInCategory, category);
            log.info("Result: {}", result);
            log.info("Search time: {}", watch.getTime());
            watch.reset();

        } catch (Exception e) {
            log.error("Exception occurred: ", e);
        }
    }
}
