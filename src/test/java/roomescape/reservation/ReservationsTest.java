package roomescape.reservation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ReservationsTest {

    private static final long DUMMY_ID = 0L;
    private Reservations reservations;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        reservations = new Reservations(jdbcTemplate);
        jdbcTemplate.execute("DROP TABLE IF EXISTS reservation");
        jdbcTemplate.execute("CREATE TABLE reservation("
                + "id BIGINT NOT NULL AUTO_INCREMENT, "
                + "name VARCHAR(255) NOT NULL, "
                + "date VARCHAR(255) NOT NULL, "
                + "time VARCHAR(255) NOT NULL, "
                + "PRIMARY KEY (id))");
    }

    @DisplayName("예약을 입력받아 저장한다.")
    @Test
    void save1() {
        // given
        final Reservation reservation = new Reservation(DUMMY_ID, "검프",
                LocalDate.of(2025, 4, 4), LocalTime.of(14, 28));

        // when
        reservations.insert(reservation);

        // then
        assertThat(reservations.findAllReservations()).hasSize(1);
    }

    @DisplayName("예약이 저장되면 아이디를 붙인 예약을 반환한다.")
    @Test
    void save2() {
        // given
        final Reservation reservation = new Reservation(DUMMY_ID, "검프",
                LocalDate.of(2025, 4, 4), LocalTime.of(14, 28));

        // then
        assertThatCode(() -> reservations.insert(reservation))
                .doesNotThrowAnyException();
    }

    @DisplayName("아이디를 입력받아 예약을 삭제한다.")
    @Test
    void removeReservation1() {
        // given
        final Reservation reservation = new Reservation(DUMMY_ID, "검프",
                LocalDate.of(2025, 4, 4), LocalTime.of(14, 28));
        reservations.insert(reservation);

        // then
        assertThat(reservations.delete(1L)).isEqualTo(1);
    }

    @DisplayName("존재하지 않는 아이디가 들어오면 예외가 발생한다.")
    @Test
    void removeReservation2() {
        // when & then
        assertThat(reservations.delete(1L)).isEqualTo(0);
    }

    @Test
    void 오단계() {
        jdbcTemplate.update("INSERT INTO reservation (name, date, time) VALUES (?, ?, ?)",
                "브라운", "2023-08-05", "15:40");

        List<Reservation> reservations = RestAssured.given().log().all()
                .when().get("/reservations")
                .then().log().all()
                .statusCode(200).extract()
                .jsonPath().getList(".", Reservation.class);

        Integer count = jdbcTemplate.queryForObject("SELECT count(1) from reservation", Integer.class);

        assertThat(reservations.size()).isEqualTo(count);
    }

    @Test
    void 육단계() {
        Map<String, String> params = new HashMap<>();
        params.put("name", "브라운");
        params.put("date", "2023-08-05");
        params.put("time", "10:00");

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(params)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(200);

        Integer count = jdbcTemplate.queryForObject("SELECT count(1) from reservation", Integer.class);
        assertThat(count).isEqualTo(1);

        RestAssured.given().log().all()
                .when().delete("/reservations/1")
                .then().log().all()
                .statusCode(200);

        Integer countAfterDelete = jdbcTemplate.queryForObject("SELECT count(1) from reservation", Integer.class);
        assertThat(countAfterDelete).isEqualTo(0);
    }

}
