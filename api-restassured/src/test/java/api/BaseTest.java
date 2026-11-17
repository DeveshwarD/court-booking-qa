package api;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeSuite;
import java.util.Map;
import static io.restassured.RestAssured.given;

public class BaseTest {
    protected static RequestSpecification spec;
    protected static String token;

    @BeforeSuite
    public void setUp() {
        String baseUrl = System.getProperty("baseUrl", "https://restful-booker.herokuapp.com");
        spec = new RequestSpecBuilder()
                .setBaseUri(baseUrl)
                .setContentType(ContentType.JSON)
                .addHeader("Accept", "application/json")
                .build();

        // Credentials come from environment variables, so CI can inject them as secrets later
        token = given().spec(spec)
                .body(Map.of(
                        "username", System.getenv().getOrDefault("RB_USER", "admin"),
                        "password", System.getenv().getOrDefault("RB_PASS", "password123")))
                .when().post("/auth")
                .then().statusCode(200)
                .extract().path("token");
    }
}