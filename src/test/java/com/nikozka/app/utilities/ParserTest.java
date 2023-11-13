package com.nikozka.app.utilities;

import org.bson.Document;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ParserTest {

    @Test
    void parseToResultTest() {
        Document mongoDocument = Document.parse("{ \"_id\" : { \"address,\" : \"пр. Рудного, 1, Павлоград,  58353\", \"quantity\" : 96 }, \"totalQuantity\" : 14976 }");
        String category = "Музика";

        String result = Parser.parseToResult(mongoDocument, category);

        String expected = "Адреса магазину із найбільшою кількістю товарів типу:\" Музика\" - пр. Рудного, 1, Павлоград,  58353, totalQuantity=14976";
        assertEquals(expected, result);
    }

    @Test
    void parseToResultTestException() {
        Document mongoDocument = null;
        String category = "Музика";

        IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class,
                () -> Parser.parseToResult(mongoDocument, category));
        assertEquals("mongoDocument cannot be null, There is no such a category in DB",
                illegalArgumentException.getMessage());
    }
}