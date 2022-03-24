package tests;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;

import static helpers.CustomAllureListener.withCustomTemplates;

public class TestBase {

    @BeforeAll
    @Tag("bookstoretests")
    static void base() {
        RestAssured.baseURI = "https://demoqa.com";
        RestAssured.filters(withCustomTemplates());
    }
}
