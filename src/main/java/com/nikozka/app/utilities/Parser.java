package com.nikozka.app.utilities;

import org.bson.Document;

public class Parser {
    private Parser() {}
    public static String parseToResult(Document mongoDocument, String category) {
        if(mongoDocument == null) {
            throw new IllegalArgumentException("mongoDocument cannot be null, There is no such a category in DB");
        }
        Document addressDocument = mongoDocument.get("_id", Document.class);
        String address = addressDocument.getString("address,");
        int totalQuantity = mongoDocument.getInteger("totalQuantity");

        return String.format("Адреса магазину із найбільшою кількістю товарів типу:\" %s\" - %s, totalQuantity=%d",
                category, address, totalQuantity);
    }
}
