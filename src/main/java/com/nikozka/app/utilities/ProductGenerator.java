package com.nikozka.app.utilities;

import com.nikozka.app.models.ProductDTO;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class ProductGenerator {
    Validator validator;

    public ProductGenerator(Validator validator) {
        this.validator = validator;
    }

    public List<ProductDTO> generateProducts(int quantity) {
        List<ProductDTO> listOfProducts = new ArrayList<>();
        int counter = 1;
        while (counter <= quantity) {
            ProductDTO product = generateRandomProduct();
            if (isValidProduct(product)) {
                listOfProducts.add(product);
                counter++;
            }
        }
        return listOfProducts;
    }

    private ProductDTO generateRandomProduct() {
        return new ProductDTO(generateRandomString(), generateRandomCategoryId());
    }

    private String generateRandomString() {
        int length = ThreadLocalRandom.current().nextInt(3, 30);
        return StringUtils.capitalize(RandomStringUtils.randomAlphabetic(length).toLowerCase());
    }

    private int generateRandomCategoryId() {
        return ThreadLocalRandom.current().nextInt(1, 25);
    }

    private boolean isValidProduct(ProductDTO product) {
        Set<ConstraintViolation<ProductDTO>> constraintViolations = validator.validate(product);
        return constraintViolations.isEmpty();
    }
}
