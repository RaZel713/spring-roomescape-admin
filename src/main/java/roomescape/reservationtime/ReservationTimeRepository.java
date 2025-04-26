package roomescape.reservationtime;

import java.time.LocalTime;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ReservationTimeRepository {

    private final JdbcTemplate jdbcTemplate;

    public ReservationTimeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public synchronized void insert(final ReservationTime reservationTime) {
        final String sql = "INSERT INTO reservation_time (start_at) VALUES (?)";
        jdbcTemplate.update(
                sql,
                reservationTime.startAt());
    }

    public synchronized List<ReservationTime> findAllReservationTime() {
        final String sql = "SELECT id, start_at FROM reservation_time";

        return jdbcTemplate.query(
                sql, (resultSet, rowNum) -> {
                    ReservationTime time = new ReservationTime(
                            resultSet.getLong("id"),
                            LocalTime.parse(resultSet.getString("start_at"))
                    );
                    return time;
                });
    }

    public synchronized int delete(final long id) {
        final String sql = "DELETE FROM reservation_time where id = ?";
        return jdbcTemplate.update(sql, id);
    }

    public ReservationTime findById(final long id) {
        final String sql = "SELECT id, start_at FROM reservation_time WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, (resultSet, rowNum) ->
                new ReservationTime(
                        resultSet.getLong("id"),
                        LocalTime.parse(resultSet.getString("start_at"))
                ), id
        );
    }
}
