package roomescape.reservation;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ReservationRepository {
    private final JdbcTemplate jdbcTemplate;

    public ReservationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public synchronized void insert(final Reservation reservation) {
        // 예약을 데이터 베이스에 저장하기
        String sql = "INSERT INTO reservation (name, date, time) VALUES (?, ?, ?)";
        jdbcTemplate.update(
                sql,
                reservation.name(),
                reservation.date(),
                reservation.time());
    }

    public synchronized int delete(final long id) {
        // id에 해당하는 reservation을 지우고, 해당 쿼리에 영향받는 row 수 반환
        String sql = "DELETE FROM reservation where id = ?";
        return jdbcTemplate.update(sql, Long.valueOf(id));
    }

    public synchronized List<Reservation> findAllReservations() {
        String sql = "SELECT id, name, date, time FROM reservation";

        // 저장된 모든 Reservations를 list형태로 반환
        return jdbcTemplate.query(
                sql, (resultSet, rowNum) -> {
                    Reservation reservation = new Reservation(
                            resultSet.getLong("id"),
                            resultSet.getString("name"),
                            LocalDate.parse(resultSet.getString("date")),
                            LocalTime.parse(resultSet.getString("time"))
                    );
                    return reservation;
                });
    }
}

