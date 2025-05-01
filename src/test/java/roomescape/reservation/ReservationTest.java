package roomescape.reservation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import roomescape.Repository.ReservationRepository;
import roomescape.Repository.ReservationTimeRepository;
import roomescape.domain.reservation.Reservation;
import roomescape.domain.reservationtime.ReservationTime;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ReservationTest {

    private static final ReservationTime TIME = new ReservationTime(1L, LocalTime.of(12, 0));
    private static final Long DUMMY_ID = null;

    private ReservationRepository reservationRepository;
    private ReservationTimeRepository reservationTimeRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        reservationRepository = new ReservationRepository(jdbcTemplate);
        reservationTimeRepository = new ReservationTimeRepository(jdbcTemplate);

        jdbcTemplate.execute("DROP TABLE IF EXISTS reservation");
        jdbcTemplate.execute("DROP TABLE IF EXISTS reservation_time");

        jdbcTemplate.execute("CREATE TABLE reservation_time ("
                + "id BIGINT NOT NULL AUTO_INCREMENT,"
                + "start_at VARCHAR(255) NOT NULL,"
                + "PRIMARY KEY (id))");
        jdbcTemplate.execute("CREATE TABLE reservation ("
                + "id BIGINT NOT NULL AUTO_INCREMENT,"
                + "name VARCHAR(255) NOT NULL,"
                + "date VARCHAR(255) NOT NULL,"
                + "time_id BIGINT, "
                + "PRIMARY KEY (id),"
                + "FOREIGN KEY (time_id) REFERENCES reservation_time (id))");

        reservationTimeRepository.insert(TIME);
    }

    @DisplayName("예약을 입력받아 저장한다.")
    @Test
    void insertReservationTest1() {
        // given
        ReservationTime reservationTime = reservationTimeRepository.findBy(1L);
        final Reservation reservation = new Reservation(DUMMY_ID, "검프",
                LocalDate.of(2025, 4, 4), reservationTime);

        // when
        reservationRepository.insert(reservation);

        // then
        assertThat(reservationRepository.findAll()).hasSize(1);
    }

    @DisplayName("예약이 저장되면 아이디를 붙인 예약을 반환한다.")
    @Test
    void insertReservationTest2() {
        // given
        ReservationTime reservationTime = reservationTimeRepository.findBy(1L);
        final Reservation reservation = new Reservation(DUMMY_ID, "검프",
                LocalDate.of(2025, 4, 4), reservationTime);

        // then
        assertThatCode(() -> reservationRepository.insert(reservation))
                .doesNotThrowAnyException();
    }

    @DisplayName("아이디를 입력받아 예약을 삭제한다.")
    @Test
    void removeReservationTest1() {
        // given
        ReservationTime reservationTime = reservationTimeRepository.findBy(1L);
        final Reservation reservation = new Reservation(DUMMY_ID, "검프",
                LocalDate.of(2025, 4, 4), reservationTime);

        reservationRepository.insert(reservation);

        // then
        assertThat(reservationRepository.deleteBy(1L)).isTrue();
    }

    @DisplayName("존재하지 않는 아이디가 들어오면 false를 반환한다.")
    @Test
    void removeReservationTest2() {
        // when & then
        assertThat(reservationRepository.deleteBy(1L)).isFalse();
    }
}
