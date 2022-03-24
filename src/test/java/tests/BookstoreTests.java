package tests;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;


public class BookstoreTests extends TestBase {

    TestData testData = new TestData();

    @Test
    @Tag("bookstoretests")
    void bookListTest() {
        given()
                .log().uri()
                .when()
                .get(testData.bookListUrl)
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("schemas/bookList_response_schema.json"))
                .body("books", hasSize(greaterThan(0)));
    }

    @Test
    @Tag("bookstoretests")
    void generateTokenTest() {
        given()
                .contentType(JSON)
                .body(testData.userLogin)
                .log().uri()
                .log().body()
                .when()
                .post(testData.generateTokenUrl)
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("schemas/generateToken_response_schema.json"))
                .body("status", is("Success"))
                .body("result", is("User authorized successfully."))
                .body("token.size()", greaterThan(0));
    }


}
