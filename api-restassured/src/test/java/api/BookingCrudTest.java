package api;

import org.testng.annotations.Test;
import java.util.Map;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.*;

public class BookingCrudTest extends BaseTest {
    private int bookingId;

    private Map<String, Object> newBooking() {
        return Map.of(
                "firstname", "Deveshwar",
                "lastname", "QA",
                "totalprice", 500,
                "depositpaid", true,
                "bookingdates", Map.of("checkin", "2026-12-01", "checkout", "2026-12-02"),
                "additionalneeds", "Shuttle court");
    }

    @Test
    public void createBooking() {
        bookingId = given().spec(spec).body(newBooking())
                .when().post("/booking")
                .then().log().ifValidationFails()
                .statusCode(200)                       // quirk: this API returns 200, not 201
                .body("bookingid", notNullValue())
                .body("booking.firstname", equalTo("Deveshwar"))
                .extract().path("bookingid");
    }

    @Test(dependsOnMethods = "createBooking")
    public void getBookingMatchesSchema() {
        given().spec(spec).pathParam("id", bookingId)
                .when().get("/booking/{id}")
                .then().log().ifValidationFails()
                .statusCode(200)
                .body("lastname", equalTo("QA"))
                .body("bookingdates.checkin", equalTo("2026-12-01"))
                .body(matchesJsonSchemaInClasspath("schemas/booking.json"));
    }

    @Test(dependsOnMethods = "getBookingMatchesSchema")
    public void partialUpdateWithToken() {
        given().spec(spec).cookie("token", token).pathParam("id", bookingId)
                .body(Map.of("firstname", "Dev"))
                .when().patch("/booking/{id}")
                .then().statusCode(200)
                .body("firstname", equalTo("Dev"))
                .body("lastname", equalTo("QA"));     // PATCH must not touch other fields
    }

    @Test(dependsOnMethods = "partialUpdateWithToken")
    public void deleteThenVerifyGone() {
        given().spec(spec).cookie("token", token).pathParam("id", bookingId)
                .when().delete("/booking/{id}")
                .then().statusCode(201);               // quirk: delete returns 201 here

        given().spec(spec).pathParam("id", bookingId)
                .when().get("/booking/{id}")
                .then().statusCode(404);               // proves the delete really worked
    }

    @Test
    public void updateWithoutTokenIsForbidden() {
        given().spec(spec).pathParam("id", 1)
                .body(newBooking())
                .when().put("/booking/{id}")
                .then().statusCode(403);
    }
}
