package com.geekbrains.backend.test.imgur;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.ResponseSpecification;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.*;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ImgurApiFunctionalTest extends ImgurApiAbstractTest {

    private static String CURRENT_IMAGE_ID;
    ResponseSpecification responseSpecification = null;

    @BeforeEach
    void beforeTest() {
        responseSpecification = new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectStatusLine("HTTP/1.1 200 OK")
                .expectContentType(ContentType.JSON)
                .expectResponseTime(Matchers.lessThan(5000L))
                .expectHeader("Access-Control-Allow-Credentials", "true")
                .build();
    }

        @Test
    @Order(1)
    void getAccountBase() {
        String userName = "tatabor";
        given()
                .spec(requestSpecification)
                .log()
                .all()
                .expect()
                .body("data.id", is(157947641))
                .log()
                .all()
                .when()
                .get("account/" + userName);

    }

    @Test
    @Order(2)
    void postImageTest() {
        CURRENT_IMAGE_ID = given()
                .spec(requestSpecification)
                .multiPart("image", getFileResource("img.jpg"))
                .formParam("name", "Picture")
                .formParam("title", "The best picture!")
                .log()
                .all()
                .expect()
                .body("data.size", is(46314))
                .body("data.type", is("image/jpeg"))
                .body("data.name", is("Picture"))
                .body("data.title", is("The best picture!"))
                .log()
                .all()
                .when()
                .post("upload")
                .body()
                .jsonPath()
                .getString("data.id");
    }

    @Test
    @Order(3)
    void getImageTest() {
        given()
                .spec(requestSpecification)
                .log()
                .all()
                .expect()
                .body("data.type", is("image/jpeg"))
                .body("data.name", is("Picture"))
                .body("data.title", is("The best picture!"))
                .log()
                .all()
                .when()
                .get("image/" + CURRENT_IMAGE_ID);
    }

    @Test
    @Order(4)
    void updateImageInformationTest() {
        given()
                .spec(requestSpecification)
                .formParam("title", "Mem")
                .formParam("description", "Best mem mem mem mem!")
                .log()
                .all()
                .expect()
                .body("data", is(true))
                .body("success", is(true))
                .body("status", is(200))
                .log()
                .all()
                .when()
                .post("image/" + CURRENT_IMAGE_ID);
    }

    @Test
    @Order(5)
    void getImageTestAfterUpdate() {
        given()
                .spec(requestSpecification)
                .log()
                .all()
                .expect()
                .body("data.description", is("Best mem mem mem mem!"))
                .body("data.title", is("Mem"))
                .log()
                .all()
                .when()
                .get("image/" + CURRENT_IMAGE_ID);
    }

    @Test
    @Order(6)
    void deleteImageById() {
        given()
                .spec(requestSpecification)
                .log()
                .all()
                .expect()
                .body("status", is(200))
                .log()
                .all()
                .when()
                .delete("image/" + CURRENT_IMAGE_ID);
    }

    private static String COMMENT_ID;

    @Test
    @Order(7)
    void testCreateComment() {
        COMMENT_ID = given()
                .spec(requestSpecification)
                .formParam("image_id", "8xGCvWR")
                .formParam("comment", "Hello world")
                .log()
                .all()
                .expect()
                .body("success", is(true))
                .body("status", is(200))
                .log()
                .all()
                .when()
                .post("comment")
                .body()
                .jsonPath()
                .getString("data.id");
    }

    @Test
    @Order(8)
    void getCommentTest() {
        given()
                .spec(requestSpecification)
                .log()
                .all()
                .expect()
                .log()
                .all()
                .when()
                .get("comment/" + COMMENT_ID);
    }
    @Test
    @Order(9)
    void deleteCommentById() {
        given()
                .spec(requestSpecification)
                .log()
                .all()
                .expect()
                .body("status", is(200))
                .log()
                .all()
                .when()
                .delete("comment/" + COMMENT_ID);
    }
}
