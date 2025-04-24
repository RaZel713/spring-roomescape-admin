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
import roomescape.time.ReservationTime;
import roomescape.time.ReservationTimeRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ReservationRepositoryTest {

    private static final long DUMMY_ID = 0L;
    private ReservationRepository reservationRepository;
    private ReservationTimeRepository reservationTimeRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate1;
    @Autowired
    private JdbcTemplate jdbcTemplate2;

    @BeforeEach
    void setUp() {
        reservationRepository = new ReservationRepository(jdbcTemplate1);
        reservationTimeRepository = new ReservationTimeRepository(jdbcTemplate2);

        jdbcTemplate1.execute("DROP TABLE IF EXISTS reservation");
        jdbcTemplate2.execute("DROP TABLE IF EXISTS reservation_time");

        jdbcTemplate2.execute("CREATE TABLE reservation_time ("
                + "id BIGINT NOT NULL AUTO_INCREMENT,"
                + "start_at VARCHAR(255) NOT NULL,"
                + "PRIMARY KEY (id))");
        jdbcTemplate1.execute("CREATE TABLE reservation ("
                + "id BIGINT NOT NULL AUTO_INCREMENT,"
                + "name VARCHAR(255) NOT NULL,"
                + "date VARCHAR(255) NOT NULL,"
                + "time_id BIGINT, "
                + "PRIMARY KEY (id),"
                + "FOREIGN KEY (time_id) REFERENCES reservation_time (id))");

        final ReservationTime time = new ReservationTime(1L, LocalTime.of(12, 0));
        reservationTimeRepository.insert(time);
    }

    @DisplayName("예약을 입력받아 저장한다.")
    @Test
    void save1() {
        // given
        ReservationTime reservationTime = reservationTimeRepository.findById(1L);
        final Reservation reservation = new Reservation(DUMMY_ID, "검프",
                LocalDate.of(2025, 4, 4), reservationTime);

        // when
        reservationRepository.insert(reservation);

        // then
        assertThat(reservationRepository.findAllReservations()).hasSize(1);
    }

    @DisplayName("예약이 저장되면 아이디를 붙인 예약을 반환한다.")
    @Test
    void save2() {
        // given
        ReservationTime reservationTime = reservationTimeRepository.findById(1L);
        final Reservation reservation = new Reservation(DUMMY_ID, "검프",
                LocalDate.of(2025, 4, 4), reservationTime);

        // then
        assertThatCode(() -> reservationRepository.insert(reservation))
                .doesNotThrowAnyException();
    }

    @DisplayName("아이디를 입력받아 예약을 삭제한다.")
    @Test
    void removeReservation1() {
        // given
        ReservationTime reservationTime = reservationTimeRepository.findById(1L);
        final Reservation reservation = new Reservation(DUMMY_ID, "검프",
                LocalDate.of(2025, 4, 4), reservationTime);

        reservationRepository.insert(reservation);

        // then
        assertThat(reservationRepository.delete(1L)).isEqualTo(1);
    }

    @DisplayName("존재하지 않는 아이디가 들어오면 예외가 발생한다.")
    @Test
    void removeReservation2() {
        // when & then
        assertThat(reservationRepository.delete(1L)).isEqualTo(0);
    }

    @Test
    void 오단계() {
        jdbcTemplate1.update("INSERT INTO reservation (name, date, time_id) VALUES (?, ?, ?)",
                "브라운", LocalDate.parse("2023-08-05"), 1L);

        List<Reservation> reservations = RestAssured.given().log().all()
                .when().get("/reservations")
                .then().log().all()
                .statusCode(200).extract()
                .jsonPath().getList(".", Reservation.class);

        Integer count = jdbcTemplate1.queryForObject("SELECT count(1) from reservation", Integer.class);

        assertThat(reservations.size()).isEqualTo(count);
    }

    @Test
    void 육단계() {
        Map<String, String> params = new HashMap<>();
        params.put("name", "브라운");
        params.put("date", "2023-08-05");
        params.put("timeId", "1");

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(params)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(200);

        Integer count = jdbcTemplate1.queryForObject("SELECT count(1) from reservation", Integer.class);
        assertThat(count).isEqualTo(1);

        RestAssured.given().log().all()
                .when().delete("/reservations/1")
                .then().log().all()
                .statusCode(200);

        Integer countAfterDelete = jdbcTemplate1.queryForObject("SELECT count(1) from reservation", Integer.class);
        assertThat(countAfterDelete).isEqualTo(0);
    }

}
