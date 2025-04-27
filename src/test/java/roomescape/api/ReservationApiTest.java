package roomescape.api;

import static org.hamcrest.Matchers.is;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ReservationApiTest {

    private static final Map<String, String> RESERVATION_BODY = Map.of(
            "name", "브라운",
            "date", "2023-08-05",
            "timeId", "1"
    );

    @BeforeEach
    void setUp() {
        Map<String, String> params = new HashMap<>();
        params.put("startAt", "10:00");

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(params)
                .when().post("/times")
                .then().log().all()
                .statusCode(200);
    }

    @DisplayName("이단계: 예약 조회 - 예약이 존재하지 않는다면 200 OK와 빈 컬렉션 응답한다.")
    @Test
    void inquiryReservationTest2() {
        RestAssured.given().log().all()
                .when().get("/reservations")
                .then().log().all()
                .statusCode(200)
                .body("size()", is(0));
    }

    @DisplayName("이단계: 예약 조회 - 존재하는 모든 예약과 200 OK를 응답한다.")
    @Test
    void inquiryReservationTest1() {
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(RESERVATION_BODY)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(200);

        RestAssured.given().log().all()
                .when().get("/reservations")
                .then().log().all()
                .statusCode(200)
                .body("size()", is(1));
    }

    @DisplayName("삼단계: 예약 추가 / 취소 - 예약을 추가하고, 200 OK를 응답한다.")
    @Test
    void addReservationTest1() {
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(RESERVATION_BODY)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(200);
    }

    @DisplayName("삼단계: 예약 추가 / 취소 - 삭제 요청시, 주어진 아이디에 해당하는 예약이 있다면 삭제하고, 200 OK 응답한다.")
    @Test
    void removeReservationTest1() {
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(RESERVATION_BODY)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(200);

        RestAssured.given().log().all()
                .when().delete("/reservations/1")
                .then().log().all()
                .statusCode(200);
    }

    @DisplayName("삼단계: 예약 추가 / 취소 - 삭제 요청시, 주어진 아이디에 해당하는 예약이 없다면 404로 응답한다.")
    @Test
    void removeReservationTest2() {
        RestAssured.given().log().all()
                .when().delete("/reservations/3")
                .then().log().all()
                .statusCode(404);
    }
}
