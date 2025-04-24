package roomescape.time;

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
        // 시간을 데이터 베이스에 저장하기
        String sql = "INSERT INTO reservation_time (start_at) VALUES (?)";
        jdbcTemplate.update(
                sql,
                reservationTime.startAt());
    }

    public synchronized List<ReservationTime> findAllReservationTime() {
        String sql = "SELECT id, start_at FROM reservation_time";

        // 저장된 모든 시갇들을 list 형태로 반환
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
        // id에 해당하는 시간을 지우고, 해당 쿼리에 영향받는 row 수 반환
        String sql = "DELETE FROM reservation_time where id = ?";
        return jdbcTemplate.update(sql, Long.valueOf(id));
    }
}
