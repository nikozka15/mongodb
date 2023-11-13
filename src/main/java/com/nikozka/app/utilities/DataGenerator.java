package com.nikozka.app.utilities;

import com.github.javafaker.Faker;

import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class DataGenerator {
    static Faker faker = Faker.instance(new Locale("uk-UA"));

    private DataGenerator() {}

    public static List<String> generateShopAddresses() {
        return IntStream.range(0, 12)
                .mapToObj(i -> faker.address().fullAddress())
                .collect(Collectors.toList());
    }

    public static List<String> generateProductCategory() {
        Set<String> uniqueCategories = new HashSet<>();
        return IntStream.range(0, 20)
                .mapToObj(i -> generateUniqueCategory(uniqueCategories))
                .collect(Collectors.toList());
    }

    private static String generateUniqueCategory(Set<String> uniqueCategories) {
        String category;
        do {
            category = faker.commerce().department();
        } while (!uniqueCategories.add(category));
        return category;
    }
}
